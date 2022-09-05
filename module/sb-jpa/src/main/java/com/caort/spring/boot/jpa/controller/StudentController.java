package com.caort.spring.boot.jpa.controller;

import com.caort.spring.boot.jpa.pojo.entity.Student;
import com.caort.spring.boot.jpa.repository.DynamicQueryRepository;
import com.caort.spring.boot.jpa.repository.StudentRepository;
import com.caort.spring.boot.jpa.service.StudentService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
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
    @Autowired
    private StudentService studentService;

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

    @GetMapping("/test")
    public void test(){
        studentService.exec1();
    }

    @Bean
    public String one(){
        return "one";
    }
}
