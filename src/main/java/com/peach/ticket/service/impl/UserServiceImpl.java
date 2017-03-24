package com.peach.ticket.service.impl;

import com.peach.ticket.domain.Role;
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

    public Page<User> findAll(int page, int size, String nickname) {
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return page;
            }

            @Override
            public int getPageSize() {
                return size;
            }

            @Override
            public int getOffset() {
                return (page - 1) * size;
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

        if(!nickname.isEmpty()){
            return this.userRepository.findByNicknameContaining(nickname, pageable);
        }else{
            return this.userRepository.findAll(pageable);
        }
    }

    public User findByUserName(String username) {
        return this.userRepository.findByUsername(username);
    }

    public int countByRole(Role role){
        return this.userRepository.countByRole(role);
    }
}
