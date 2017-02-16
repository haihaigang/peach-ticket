package com.peach.ticket.service.impl;

import com.peach.ticket.domain.Role;
import com.peach.ticket.repository.RoleRepository;
import com.peach.ticket.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by hgwang on 2017/1/29.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public void save(Role entity){
        roleRepository.save(entity);
    }

    public void remove(long id){
        roleRepository.delete(id);
    }

    public Role findOne(long id){
        return roleRepository.findById(id);
    }

    public Page<Role> findAll(){
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public int getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
        return roleRepository.findAll(pageable);
    }


}
