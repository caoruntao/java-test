package com.caort.security.pojo;

import javax.persistence.*;
import java.util.List;

/**
 * @author Caort
 * @date 2022/2/20 9:35
 */
@Entity
@Table(name = "tb_role")
public class SystemRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(name = "role_authority",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private List<SystemAuthority> authorityList;

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

    public List<SystemAuthority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<SystemAuthority> authorityList) {
        this.authorityList = authorityList;
    }
}
