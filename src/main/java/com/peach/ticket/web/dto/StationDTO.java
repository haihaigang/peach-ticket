package com.peach.ticket.web.dto;

import com.peach.ticket.domain.Station;

/**
 * Created by hgwang on 2017/2/13.
 */
public class StationDTO {
    private long id;
    private String code;
    private String name;
    private int status;

    public StationDTO(){}

    public StationDTO(Station station){
        this.setId(station.getId());
        this.setCode(station.getCode());
        this.setName(station.getName());
        this.setStatus(station.getStatus());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
