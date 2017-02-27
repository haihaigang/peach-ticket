package com.peach.ticket.service.impl;

import com.peach.ticket.config.OrderStatus;
import com.peach.ticket.domain.Order;
import com.peach.ticket.repository.OrderRepository;
import com.peach.ticket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by hgwang on 2017/1/29.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public boolean confirm(long id) {
        Order order = this.orderRepository.findById(id);
        if (order == null) {
            return false;
        }

        if (order.getPayStatus() != 1) {
            return false;
        }

        if (order.getStatus() != OrderStatus.NOT_PAYED) {
            return false;
        }

        order.setStatus(OrderStatus.PAYED);
        this.orderRepository.save(order);

        return true;
    }

    public void remove(long id) {
        this.orderRepository.delete(id);
    }

    public Order findOne(long id) {
        return this.orderRepository.findById(id);
    }

    public Page<Order> findAll() {
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

        return this.orderRepository.findAllByOrderByCreateAtDesc(pageable);
    }

    public void create(Order entity) {
        this.orderRepository.save(entity);
    }

    public boolean pay(long id) {
        Order order = this.orderRepository.findById(id);
        if (order == null) {
            return false;
        }

        if (order.getPayStatus() != 0) {
            return false;
        }

        order.setPayStatus(1);
        this.orderRepository.save(order);

        return true;
    }

    public boolean cancel(long id) {
        Order order = this.orderRepository.findById(id);
        if (order == null) {
            return false;
        }

        if (order.getStatus() != OrderStatus.NOT_PAYED) {
            return false;
        }

        order.setPayStatus(OrderStatus.CANCELED);
        this.orderRepository.save(order);

        return true;
    }

    public boolean use(long id) {
        Order order = this.orderRepository.findById(id);

        if (order == null) {
            return false;
        }

        if (order.getStatus() != OrderStatus.PAYED) {
            return false;
        }

        order.setStatus(OrderStatus.USED);
        this.orderRepository.save(order);

        return true;
    }

    public boolean invalid(long id) {
        Order order = this.orderRepository.findById(id);

        if (order == null) {
            return false;
        }

        if (order.getStatus() != OrderStatus.PAYED) {
            return false;
        }

        order.setPayStatus(OrderStatus.INVALIDED);
        this.orderRepository.save(order);

        return true;
    }

    public Page<Order> findByUser(long id) {
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
        return this.orderRepository.findByUserIdOrderByCreateAtDesc(id, pageable);
    }
}
