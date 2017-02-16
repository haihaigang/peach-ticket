package com.peach.ticket.service.impl;

import com.peach.ticket.domain.User;
import com.peach.ticket.repository.UserRepository;
import com.peach.ticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by hgwang on 2017/1/29.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(User entity) {
        this.userRepository.save(entity);
    }

    public void remove(long id) {
        this.userRepository.delete(id);
    }

    public User findOne(long id) {
        return this.userRepository.findById(id);
    }

    public Page<User> findAll() {
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

        return this.userRepository.findAll(pageable);
    }

    public User findByUserName(String username) {
        return this.userRepository.findByUsername(username);
    }
}
