package com.peach.ticket.repository;

import com.peach.ticket.domain.Role;
import com.peach.ticket.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 * Created by jwliu on 2017/2/13.
 */
public interface UserRepository  extends Repository<User, Long> {

    void save(User entity);

    void delete(Long id);

    User findById(Long id);

    Page<User> findAll(Pageable pageable);

    Page<User> findByNicknameContaining(String nickname, Pageable pageable);

    User findByUsername(String username);

    int countByRole(Role role);


}
