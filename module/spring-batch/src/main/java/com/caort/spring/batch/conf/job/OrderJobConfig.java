package com.caort.spring.batch.conf.job;

import com.caort.spring.batch.constant.OrderStatus;
import com.caort.spring.batch.listener.OrderReadListener;
import com.caort.spring.batch.listener.OrderWriteListener;
import com.caort.spring.batch.pojo.entity.Order;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Caort
 * @date 2022/7/9 11:07
 */
@Configuration
@EnableBatchProcessing
public class OrderJobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    // 指定续费提醒阈值
    private Duration threshold = Duration.ofDays(31);

    @Bean
    public ItemReader<Order> orderItemReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Order>()
                .name("orderItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT o FROM Order o WHERE o.status = 'completed'")
                .pageSize(100)
                .build();
    }

    @Bean
    public ItemProcessor<Order, Order> orderItemProcessor() {
        return order -> {
            LocalDateTime expireTime = order.getExpireTime();
            LocalDateTime remindTime = LocalDateTime.now().plus(threshold);
            if (remindTime.isAfter(expireTime)) {
                order.setStatus(OrderStatus.WAIT_RENEW);
                // TODO 发送消息到MQ，消费者发送续费提醒
            }
            order.setUpdateTime(LocalDateTime.now());
            return order;
        };
    }

    @Bean
    public ItemWriter<Order> orderItemWriter(EntityManagerFactory entityManagerFactory) {
        return new JpaItemWriterBuilder<Order>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step orderFirstStep(PlatformTransactionManager transactionManager,
                               ItemReader<Order> orderItemReader,
                               ItemProcessor<Order, Order> orderItemProcessor,
                               ItemWriter<Order> orderItemWriter) {
        return stepBuilderFactory.get("order_first_step")
                .transactionManager(transactionManager)
                .<Order, Order>chunk(100)
                .reader(orderItemReader)
                .processor(orderItemProcessor)
                .writer(orderItemWriter)
                .listener(new OrderReadListener())
                .listener(new OrderWriteListener())
//                .startLimit(1)
                .build();
    }

    @Bean
    public Job orderJob(JobRepository jobRepository, Step orderFirstStep) {
        return jobBuilderFactory.get("order_job")
                .repository(jobRepository)
                .start(orderFirstStep)
                .build();
    }
}
