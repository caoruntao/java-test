package com.caort.datasource.repository;

import com.caort.datasource.pojo.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Caort.
 * @date 2021/5/10 下午3:43
 */
@Mapper
public interface StudentMapper {
    Student findUserById(Long id);

    int addStudent(Student student);
}
