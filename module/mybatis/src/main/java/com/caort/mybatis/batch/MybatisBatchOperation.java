package com.caort.mybatis.batch;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MybatisBatchOperation {
    private static final Logger log = LoggerFactory.getLogger(MybatisBatchOperation.class);
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 批量插入
     *
     * @param mapperMethodFullName 批量插入时要调用的方法全限定名，eg. com.xx.mapper.UserMapper.insert
     * @param entityCollection     批量插入的数据
     * @param batchSize            批大小，每多少条数据一提交
     */
    public void batchInsert(String mapperMethodFullName, Collection<?> entityCollection, int batchSize) {
        Assert.notEmpty(entityCollection, "批量插入的数据不能为空");
        Assert.isTrue(batchSize > 0, "批量插入的批大小不能小于0");
        int count = entityCollection.size();
        // 获取一个模式为BATCH，自动提交为false的session
        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
            AtomicInteger i = new AtomicInteger(1);
            Iterator<?> iterator = entityCollection.iterator();
            while (iterator.hasNext()) {
                Object entity = iterator.next();
                sqlSession.insert(mapperMethodFullName, entity);
                if (i.get() % batchSize == 0 || i.get() == count) {
                    // 手动提交，提交后无法回滚
                    sqlSession.commit();
                    // 清理缓存，防止溢出
                    sqlSession.clearCache();
                }
                i.getAndIncrement();
            }
        }
        log.info("批量插入数据成功, 调用方法全限定名[{}], 批大小[{}], 总条数[{}]", mapperMethodFullName, batchSize, count);
    }
}
