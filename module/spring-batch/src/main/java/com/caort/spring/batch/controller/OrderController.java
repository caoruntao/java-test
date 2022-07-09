package com.caort.spring.batch.controller;

import com.caort.spring.batch.pojo.entity.Order;
import com.caort.spring.batch.repository.OrderRepository;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Caort
 * @date 2022/7/9 10:27
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("orderJob")
    private Job job;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public Long addOrder(@RequestBody Order order) {
        LocalDateTime expireDate = LocalDateTime.now().plusMonths(1);
        order.setExpireTime(expireDate);
        orderRepository.save(order);
        return order.getId();
    }

    @GetMapping("/list")
    public List<Order> getAllOrder() {
        List<Order> orderList = orderRepository.findAll();
        return orderList;
    }

    @GetMapping
    public List<Order> findOrder() {
        List<Order> orderList = orderRepository.findByStatus();
        return orderList;
    }

    @GetMapping("/job.html")
    public void performJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        HashMap<String, JobParameter> paramtersMap = new HashMap<>();
        paramtersMap.put("performData", new JobParameter(new Date()));
        JobParameters jobParameters = new JobParameters(paramtersMap);
        jobLauncher.run(job, jobParameters);
    }
}
