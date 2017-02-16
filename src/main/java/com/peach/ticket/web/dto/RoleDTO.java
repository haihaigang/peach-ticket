package com.peach.ticket.web.dto;

import com.peach.ticket.domain.Role;

/**
 * Created by hgwang on 2017/1/29.
 */
public class RoleDTO {
    private long id;
    private String name;
    private String desc;

    public RoleDTO() {
    }

    public RoleDTO(Role role) {
        this.setId(role.getId());
        this.setName(role.getName());
        this.setDesc(role.getDesc());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
