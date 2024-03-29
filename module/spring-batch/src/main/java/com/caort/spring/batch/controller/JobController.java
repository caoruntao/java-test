package com.caort.spring.batch.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

/**
 * @author Caort.
 * @date 2021/8/25 下午3:25
 */
@RestController
@RequestMapping("/student")
public class JobController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("studentJob")
    private Job job;

    @GetMapping("/job.html")
    public void performJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        HashMap<String, JobParameter> paramtersMap = new HashMap<>();
        paramtersMap.put("performData", new JobParameter(new Date()));
        JobParameters jobParameters = new JobParameters(paramtersMap);
        jobLauncher.run(job, jobParameters);
    }
}
