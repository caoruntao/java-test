package com.caort.spring.batch.conf.job;

import com.caort.spring.batch.convert.input.JsonLingAggregator;
import com.caort.spring.batch.listener.StudentProcessorListener;
import com.caort.spring.batch.listener.StudentReaderListener;
import com.caort.spring.batch.listener.StudentWriterListener;
import com.caort.spring.batch.pojo.entity.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Caort.
 * @date 2021/8/25 下午2:33
 */
@Configuration
@EnableBatchProcessing
public class StudentJobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<Student> studentItemReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Student>()
                .name("studentItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT s FROM Student s")
                .pageSize(1000)
                .build();
    }

    @Bean
    public ItemProcessor<Student, Student> studentItemProcessor() {
        return student -> {
            student.setUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
            return student;
        };
    }

    @Bean
    public ItemWriter<Student> studentItemWriter() {
        return new FlatFileItemWriterBuilder<Student>()
                .name("studentItemWriter")
                .resource(new FileSystemResource("/Users/trustasia/Documents/tmp/batch/student_batch.txt"))
                .lineAggregator(new JsonLingAggregator())
                .build();


    }

    @Bean
    public Step studentFirstStep(PlatformTransactionManager transactionManager,
                          ItemReader<Student> studentItemReader,
                          ItemProcessor<Student, Student> studentItemProcessor,
                          ItemWriter<Student> studentItemWriter) {
        return stepBuilderFactory.get("student_first_step")
                .transactionManager(transactionManager)
                .<Student, Student>chunk(100)
                .reader(studentItemReader)
                .processor(studentItemProcessor)
                .writer(studentItemWriter)
                .listener(new StudentReaderListener())
                .listener(new StudentProcessorListener())
                .listener(new StudentWriterListener())
                .startLimit(1)
                .build();
    }

    @Bean
    public Job studentJob(JobRepository jobRepository, Step studentFirstStep) {
        return jobBuilderFactory.get("student_job")
                .repository(jobRepository)
                .start(studentFirstStep)
                .build();
    }

}
