package com.caort.spring.batch.listener;

import com.caort.spring.batch.convert.input.JsonLingAggregator;
import com.caort.spring.batch.pojo.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

/**
 * @author Caort.
 * @date 2021/8/25 下午3:37
 */
public class ReaderListener implements ItemReadListener<Student> {
    private static final Logger log = LoggerFactory.getLogger(ReaderListener.class);
    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(Student item) {

    }

    @Override
    public void onReadError(Exception ex) {
        log.error("An exception occurred while reading data", ex);
    }
}
