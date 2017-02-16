package com.peach.ticket.web.dto;

import com.peach.ticket.domain.Order;

/**
 * Created by hgwang on 2017/2/13.
 */
public class OrderDTO {
    private long id;
    private long userId;
    private long startStationId;
    private long endStationId;
    private double totalAmount;
    private double totalDiscount;
    private double payAmount;
    private int payStatus;
    private int status;
    private long createAt;
    private long updateAt;

    public OrderDTO(){}

    public OrderDTO(Order order){
        this.setId(order.getId());
        this.setUserId(order.getUserId());
        this.setStartStationId(order.getStartStationId());
        this.setEndStationId(order.getEndStationId());
        this.setTotalAmount(order.getTotalAmount());
        this.setTotalDiscount(order.getTotalDiscount());
        this.setPayAmount(order.getPayAmount());
        this.setPayStatus(order.getPayStatus());
        this.setStatus(order.getStatus());
        this.setCreateAt(order.getCreateAt());
        this.setUpdateAt(order.getUpdateAt());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(long startStationId) {
        this.startStationId = startStationId;
    }

    public long getEndStationId() {
        return endStationId;
    }

    public void setEndStationId(long endStationId) {
        this.endStationId = endStationId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }
}
