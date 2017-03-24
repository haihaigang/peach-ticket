package com.peach.ticket.service;

import com.peach.ticket.domain.Role;
import com.peach.ticket.domain.User;
import org.springframework.data.domain.Page;

/**
 * Created by hgwang on 2017/1/21.
 */
public interface UserService {

    //1. /api/user/save 保存更新
    void save(User entity);

    //2. /api/user/remove 删除
    void remove(long id);

    //3. /api/user/1 查询
    User findOne(long id);

    //4. /api/user/findAll 获取所有
    Page<User> findAll(int page, int size, String nickname);

    User findByUserName(String username);

    int countByRole(Role role);

}
