package com.peach.ticket.web.dto;

import com.peach.ticket.domain.User;

/**
 * Created by hgwang on 2017/2/13.
 */
public class UserDTO {
    private long id;

    private String username;

    private String nickname;

    private String phone;

    private int status;

    private long roleId;

    private RoleDTO role;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUserName());
        this.setNickname(user.getNickname());
        this.setPhone(user.getPhone());
        this.setStatus(user.getStatus());
        this.setRole(new RoleDTO(user.getRole()));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }
}
