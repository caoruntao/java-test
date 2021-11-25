package com.caort.spring.boot.jpa.repository;

import com.caort.spring.boot.jpa.pojo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * @author Caort
 * @date 2021/8/17 21:15
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "SELECT * FROM tb_student WHERE id = ?",
            nativeQuery = true)
    //@Query(value = "SELECT Student FROM Student WHERE id = ?")
    Optional<Student> findStudentById(Long id);
}
