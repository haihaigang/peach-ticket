package com.peach.ticket.domain;

import javax.persistence.*;

/**
 * Created by hgwang on 2017/1/25.
 */
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "`name`")
    private String name;
    @Column(name = "`desc`")
    private String desc;

    public Role() {
    }

    public Role(int id) {
        this.id = id;
    }

    public Role(long id) {
        this.id = id;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
