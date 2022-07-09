package com.caort.spring.batch.conf.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @author Caort
 * @date 2022/7/9 11:29
 */
@Configuration
@EnableScheduling
public class OrderScheduler {
    private static final Logger log = LoggerFactory.getLogger(OrderScheduler.class);
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("orderJob")
    private Job job;

//    @Scheduled(cron = "* 0/5 * * * ?")
    public void orderWaitRenewCheck() {
        JobParameters jobParameters = new JobParameters();
        jobParameters.getParameters().put("performData", new JobParameter(new Date()));
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.error("调度订单待续费检查失败", e);
        }
    }
}
