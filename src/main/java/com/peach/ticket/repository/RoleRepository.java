package com.peach.ticket.repository;

import com.peach.ticket.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 * Created by hgwang on 2017/1/29.
 */
public interface RoleRepository extends Repository<Role, Long> {

    void save(Role entity);

    void delete(Long id);

    Role findById(Long id);

    Page<Role> findAll(Pageable pageable);
}
