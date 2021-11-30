package com.caort.datasource.controller;

import com.caort.datasource.pojo.entity.Student;
import com.caort.datasource.repository.StudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Caort.
 * @date 2021/5/10 下午3:50
 */
@RestController
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentMapper studentMapper;

    @GetMapping("/student/{id}")
    public Student findStudent(@PathVariable Long id){
        return studentMapper.findUserById(id);
    }

    @PostMapping("/student")
    public Long addStudent(@RequestBody Student student){
        int count = studentMapper.addStudent(student);
        log.info("count is [{}]", count);
        return student.getId();
    }
}
