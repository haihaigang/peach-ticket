package com.peach.ticket.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by hgwang on 2017/1/25.
 */
@Entity
@Table(name = "line")
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    private String name;
    private double price;
    private double miles;
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="line_station",
            joinColumns={@JoinColumn(name="line_id")},
            inverseJoinColumns={@JoinColumn(name="station_id")}
    )
    private List<Station> stations;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        id = id;
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

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public void addStation(Station station){
        this.stations.add(station);
    }

    public void removeStation(Station station){
        this.stations.remove(station);
    }
}
