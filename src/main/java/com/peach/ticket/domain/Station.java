package com.peach.ticket.domain;

import javax.persistence.*;
import java.util.HashMap;

/**
 * Created by hgwang on 2017/1/25.
 */
@Entity
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    private String name;
    private int status;
    @Transient
    private HashMap<Long, Station> nextStations;
    @Transient
    private HashMap<String, Boolean> lines;

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

    public HashMap<Long, Station> getNextStations() {
        return nextStations;
    }

    public void setNextStations(HashMap<Long, Station> nextStations) {
        this.nextStations = nextStations;
    }

    public void addStation(Station station) {
        if (this.nextStations == null) {
            this.nextStations = new HashMap<>();
        }
        this.nextStations.put(station.getId(), station);

        String key = new String();
        if (this.id < station.getId()) {
            key = this.id + "-" + station.getId();
        } else {
            key = station.getId() + "-" + this.id;
        }
        if (this.lines == null) {
            this.lines = new HashMap<>();
        }
        this.lines.put(key, false);
    }

    public HashMap<String, Boolean> getLines() {
        return lines;
    }

    public void setLines(HashMap<String, Boolean> lines) {
        this.lines = lines;
    }

    public void addLines(String key) {
        if (this.lines == null) {
            this.lines = new HashMap<>();
        }
        this.lines.put(key, true);
    }

    public void resetLines() {
        for (int i = 0; i < this.lines.keySet().size(); i++) {
            String key = (String) this.lines.keySet().toArray()[i];
            this.lines.put(key, false);
        }
    }

    public boolean hasAllLines() {
        boolean flag = true;
        for (int i = 0; i < this.lines.keySet().size(); i++) {
            String key = (String) this.lines.keySet().toArray()[i];
            if (!this.lines.get(key)) {
                flag = false;
            }
        }

        return flag;
    }
}
