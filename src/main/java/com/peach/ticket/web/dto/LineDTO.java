package com.peach.ticket.web.dto;

import com.peach.ticket.domain.Line;

/**
 * Created by hgwang on 2017/2/13.
 */
public class LineDTO {
    private long Id;
    private String code;
    private String name;
    private double price;
    private double miles;

    public LineDTO(){}

    public LineDTO(Line line){
        this.setId(line.getId());
        this.setCode(line.getCode());
        this.setName(line.getName());
        this.setPrice(line.getPrice());
        this.setMiles(line.getMiles());
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }
}
