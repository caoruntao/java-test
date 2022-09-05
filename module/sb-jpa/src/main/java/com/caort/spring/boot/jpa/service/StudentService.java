package com.caort.spring.boot.jpa.service;

import com.caort.spring.boot.jpa.pojo.entity.Student;
import com.caort.spring.boot.jpa.repository.StudentRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author caort
 * @date 2022/8/30 17:15
 */
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ObjectProvider<StudentService> serviceObjectProvider;

    /**
     * 事务和方法绑定，事务的数据逃逸不影响事务提交
     */
    @Transactional(rollbackFor = Throwable.class)
    public void exec1(){
        Student student = studentRepository.findStudentById(1L).get();
        student.setName("111111");
        Thread thread = new Thread(() -> {
            Objects.requireNonNull(serviceObjectProvider.getIfAvailable()).exec2(student);
        });
        thread.start();
        studentRepository.save(student);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void exec2(Student student){
        student.setName("222222");
        studentRepository.save(student);
    }
}
