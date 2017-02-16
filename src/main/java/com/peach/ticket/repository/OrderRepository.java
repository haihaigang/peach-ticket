package com.peach.ticket.repository;

import com.peach.ticket.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 * Created by hgwang on 2017/2/13.
 */
public interface OrderRepository extends Repository<Order, Long> {

    void save(Order entity);

    void delete(Long id);

    Order findById(Long id);

    Page<Order> findAll(Pageable pageable);

    Page<Order> findByUserId(long userId, Pageable pageable);
}
