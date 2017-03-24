package com.peach.ticket.service;

import com.peach.ticket.domain.Order;
import org.springframework.data.domain.Page;

/**
 * Created by hgwang on 2017/1/25.
 */
public interface OrderService {
    /**
     * 确认订单，更改订单状态从未支付到已支付
     *
     * @param id 订单ID
     * @return true成功，false失败（不存在或状态不正确或支付状态不正确）
     */
    boolean confirm(long id);

    /**
     * 删除订单
     */
    void remove(long id);

    /**
     * 获取所有订单
     */
    Page<Order> findAll(int page, int size, long userId, int status);

    /**
     * 获取某个订单
     */
    Order findOne(long id);

    /**
     * 创建订单
     */
    void create(Order entity);

    /**
     * 支付订单，变更支付状态为已支付
     *
     * @param id 订单ID
     * @return true成功，false失败（不存在或状态不正确）
     */
    boolean pay(long id);

    /**
     * 取消订单，用户取消自己未支付的订单，更改订单状态从未支付到已取消
     *
     * @param id 订单ID
     * @return true成功，false失败（不存在或状态不正确）
     */
    boolean cancel(long id);

    /**
     * 用户使用订单，更改订单状态从已支付到已使用
     *
     * @param id 订单ID
     * @return true成功，false失败（不存在或状态不正确）
     */
    boolean use(long id);

    /**
     * 作废订单，更改订单状态从已支付到已作废
     *
     * @param id 订单ID
     * @return true成功，false失败（不存在或状态不正确）
     */
    boolean invalid(long id);

    Page<Order> findByUser(int page, int size, long id, int status);
}
