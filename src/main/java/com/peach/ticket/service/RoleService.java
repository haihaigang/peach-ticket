package com.peach.ticket.service;

import com.peach.ticket.domain.Role;
import org.springframework.data.domain.Page;

/**
 * Created by hgwang on 2017/1/25.
 */
public interface RoleService {

    //1. /api/role/save 保存更新
    public void save(Role entity);

    //2. /api/role/remove 删除
    public void remove(long id);

    //3. /api/role/1 查询
    public Role findOne(long id);

    //4. /api/role/findAll 获取所有
    public Page<Role> findAll();
}
