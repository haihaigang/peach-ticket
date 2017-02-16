package com.peach.ticket.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hgwang on 2017/1/25.
 */
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private long startStationId;
    private long endStationId;
    private String fangan;
    private double totalAmount;
    private double totalDiscount;
    private double payAmount;
    private int payStatus;//0未支付、1已支付
    private int status;//0未支付、1已支付、2已使用、3已取消、4已作废
    private long createAt;
    private long updateAt;

    public Order(){
        this.payStatus = 0;
        this.status = 0;
        this.createAt = new Date().getTime();
        this.updateAt = new Date().getTime();
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

    public String getFangan() {
        return fangan;
    }

    public void setFangan(String fangan) {
        this.fangan = fangan;
        this.totalAmount = this.calPrice();
    }

    /**
     * 定价策略：所经站台数x
     * 1. 2<=x<5 = 3元
     * 2. 5<=x></=x>9 = 4元
     * 3. 9<=x<13 = 6元
     * 4. 13<=x = 8元
     * */
    private double calPrice(){
        if(this.fangan == null || this.fangan.isEmpty()){
            return 0;
        }

        String[] fangs = this.fangan.split("-");
        int size = fangs.length;

        if(size < 3){
            return 0;
        }
        size--;//去除最后一个横线

        if(size < 5){
            return 3;
        }

        if(size < 9){
            return 4;
        }

        if(fangs.length < 12){
            return 6;
        }

        return 8;
    }
}
