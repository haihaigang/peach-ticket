package com.peach.ticket.web.dto;

/**
 * Created by hgwang on 2017/2/15.
 */
public class TicketDTO {
    private long startStationId;
    private long endStationId;
    private double price;
    private String fangan;
    private String fanganCN;
    private int count;


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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFangan() {
        return fangan;
    }

    public void setFangan(String fangan) {
        this.fangan = fangan;
        if(!fangan.isEmpty()){
            this.count = fangan.split("-").length;
        }
    }

    public String getFanganCN() {
        return fanganCN;
    }

    public void setFanganCN(String fanganCN) {
        this.fanganCN = fanganCN;
    }
}
