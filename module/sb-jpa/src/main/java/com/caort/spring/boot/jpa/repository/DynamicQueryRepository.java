package com.caort.spring.boot.jpa.repository;

import com.caort.spring.boot.jpa.pojo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Caort
 * @date 2021/8/18 19:51
 */
@Repository
public class DynamicQueryRepository {
    @Autowired
    private EntityManager entityManager;

    public List<Student> findStudentByDynamic(String name, Integer age){
        StringBuilder querySqlBuilder = new StringBuilder();
        StringBuilder conditionBuilder = new StringBuilder();
        List<Object> queryParams = new ArrayList<>();
        // 声明查询字段
        querySqlBuilder.append("SELECT Student " +
                "FROM Student " +
                "WHERE 1 = 1 ");

        // 拼接条件查询
        if (StringUtils.hasText(name)) {
            conditionBuilder.append("AND name like ? ");
            queryParams.add(name);
        }
        if(age != null &&  age > 0){
            conditionBuilder.append("AND age = ? ");
            queryParams.add(age);
        }
        querySqlBuilder.append(conditionBuilder);
        // 拼接条件参数
        final Query query = entityManager.createQuery(querySqlBuilder.toString(), Student.class);
        for (int i = 0; i < queryParams.size(); i++) {
            query.setParameter(i, queryParams.get(i));
        }
        return query.getResultList();
    }
}
