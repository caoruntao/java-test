package com.caort.spring.boot.jpa.controller;

import com.caort.spring.boot.jpa.pojo.entity.Student;
import com.caort.spring.boot.jpa.repository.DynamicQueryRepository;
import com.caort.spring.boot.jpa.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Caort
 * @date 2021/8/17 21:14
 */
@RestController
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private DynamicQueryRepository dynamicQueryRepository;

    @PostMapping("/student")
    public Long saveStudent(@RequestBody Student student) {
        studentRepository.save(student);
        return student.getId();
    }

    @GetMapping("/student/{id}")
    public Student findById(@PathVariable Long id) {
        Optional<Student> foundStudentOpt = studentRepository.findById(id);
        if (foundStudentOpt.isPresent()) {
            return foundStudentOpt.get();
        }
        return null;
    }

    @GetMapping("/student")
    public List<Student> findStudents(@RequestParam String name, @RequestParam Integer age) {
        return dynamicQueryRepository.findStudentByDynamic(name + "%s", age);
    }

    @Bean
    public String one(){
        return "one";
    }
}
