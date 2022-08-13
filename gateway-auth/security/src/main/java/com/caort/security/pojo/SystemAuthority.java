package com.caort.security.pojo;

import javax.persistence.*;
import java.util.List;

/**
 * @author Caort
 * @date 2022/2/20 9:38
 */
@Entity
@Table(name = "tb_authority")
public class SystemAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
