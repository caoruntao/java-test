package com.caort.spring.batch.listener;

import com.caort.spring.batch.pojo.entity.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Caort.
 * @date 2021/8/25 下午3:39
 */
public class WriterListener implements ItemWriteListener<Student> {
    private static final Logger log = LoggerFactory.getLogger(WriterListener.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void beforeWrite(List<? extends Student> items) {

    }

    @Override
    public void afterWrite(List<? extends Student> items) {

    }

    @Override
    public void onWriteError(Exception exception, List<? extends Student> items) {
        log.error("An exception occurred while writing data, data[{}]",
                items.stream()
                        .map(item -> String.valueOf(item.getId()))
                        .collect(Collectors.joining(", ")),
                exception);
    }
}
