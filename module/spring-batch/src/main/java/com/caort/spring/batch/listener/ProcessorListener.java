package com.caort.spring.batch.listener;

import com.caort.spring.batch.pojo.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;

/**
 * @author Caort.
 * @date 2021/8/25 下午3:44
 */
public class ProcessorListener implements ItemProcessListener<Student, Student> {
    private static final Logger log = LoggerFactory.getLogger(ProcessorListener.class);

    @Override
    public void beforeProcess(Student item) {

    }

    @Override
    public void afterProcess(Student item, Student result) {

    }

    @Override
    public void onProcessError(Student item, Exception e) {
        log.error("An exception occurred while processing the data, dataId[{}]", item.getId(), e);
    }
}
