package com.caort.spring.batch.convert.input;

import com.caort.spring.batch.pojo.entity.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.LineAggregator;

/**
 * @author Caort.
 * @date 2021/8/25 下午3:02
 */
public class JsonLingAggregator implements LineAggregator<Student> {
    private static final Logger log = LoggerFactory.getLogger(JsonLingAggregator.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String aggregate(Student student) {
        try {
            return objectMapper.writeValueAsString(student);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert object to string", e);
        }
        return null;
    }
}
