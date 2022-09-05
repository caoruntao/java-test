package com.caort.spring.boot.jpa.pojo.entity;

import com.caort.spring.boot.jpa.data.integrity.ProtectedData;
import com.caort.spring.boot.jpa.data.integrity.ProtectionStringBuilder;
import com.caort.spring.boot.jpa.exception.DatabaseProtectionException;

import javax.persistence.*;

/**
 * @author Caort
 * @date 2021/8/17 21:15
 */
@Entity
@Table(name = "tb_student")
public class Student extends ProtectedData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "row_protection")
    private String rowProtection;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    protected String getProtectString(int protectVersion) {
        ProtectionStringBuilder protectionStringBuilder = new ProtectionStringBuilder();
        protectionStringBuilder.append(name).append(age);
        return protectionStringBuilder.toString();
    }

    @PrePersist
    @PreUpdate
    @Override
    protected void protectData() throws DatabaseProtectionException {
        long start = System.currentTimeMillis();
        super.protectData();
        System.out.println("use time:" + (System.currentTimeMillis() - start));
    }

    @PostLoad
    @Override
    protected void verifyData() throws DatabaseProtectionException {
        long start = System.currentTimeMillis();
        super.verifyData();
        System.out.println("use time:" + (System.currentTimeMillis() - start));
    }

    @Override
    public void setRowProtection(String rowProtection) {
        this.rowProtection = rowProtection;
    }

    @Override
    public String getRowProtection() {
        return this.rowProtection;
    }
}
