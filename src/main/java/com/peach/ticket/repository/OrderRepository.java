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

    Page<Order> findAllByOrderByCreateAtDesc(Pageable pageable);

    Page<Order> findByUserIdOrderByCreateAtDesc(long userId, Pageable pageable);

    Page<Order> findByUserIdAndStatusOrderByCreateAtDesc(long userId, int status, Pageable pageable);

    Page<Order> findByStatusOrderByCreateAtDesc(int status, Pageable pageable);
}
