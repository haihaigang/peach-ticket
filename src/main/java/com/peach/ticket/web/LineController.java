package com.peach.ticket.web;

import com.peach.ticket.domain.Line;
import com.peach.ticket.domain.Order;
import com.peach.ticket.domain.Station;
import com.peach.ticket.service.LineService;
import com.peach.ticket.service.StationService;
import com.peach.ticket.web.dto.LineDTO;
import com.peach.ticket.web.dto.TicketDTO;
import com.peach.ticket.web.response.ResponseCode;
import com.peach.ticket.web.response.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hgwang on 2017/2/13.
 */
@RestController
@RequestMapping(value = "/api/line")
public class LineController {
    @Autowired
    private LineService lineService;
    @Autowired
    private StationService stationService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(
            @RequestParam(required = false) int id,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) double price,
            @RequestParam(required = false) double miles
    ) {
        Line oldLine = null;
        Line line = null;

        if (id != 0) {
            oldLine = this.lineService.findOne(id);
        }

        if (oldLine == null) {
            line = new Line();
            line.setId(id);
        } else {
            line = oldLine;
        }

        line.setCode(code);
        line.setName(name);
        line.setPrice(price);
        line.setMiles(miles);

        this.lineService.save(line);
        return new ResponseVo(ResponseCode.OK, "保存成功");
    }

    @RequestMapping(value = "/{id}/remove", method = RequestMethod.POST)
    public ResponseVo remove(@PathVariable Long id) {
        this.lineService.remove(id);

        return new ResponseVo(ResponseCode.OK, "删除成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseVo getOne(@PathVariable Long id) {
        Line line = this.lineService.findOne(id);
        if (line == null) {
            return new ResponseVo(ResponseCode.NOT_FOUND, "未找到当前的线路");
        }

        return new ResponseVo(line);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseVo findAll(
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int size,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name
    ) {
        Page<Line> lines = this.lineService.findAll(page, size, code, name);

        List<LineDTO> dtos = new ArrayList<LineDTO>();
        for (Line line : lines.getContent()
                ) {
            dtos.add(new LineDTO(line));
        }

        return new ResponseVo(lines);
    }

    @RequestMapping(value = "/{id}/addStation", method = RequestMethod.POST)
    public ResponseVo addStation(@PathVariable Long id, @RequestParam Long stationId) {
        Line line = this.lineService.findOne(id);
        Station station = this.stationService.findOne(stationId);

        if (line == null) {
            return new ResponseVo(ResponseCode.NOT_FOUND, "未找到线路信息");
        }

        if (station == null) {
            return new ResponseVo(ResponseCode.NOT_FOUND, "未找到站台信息");
        }

        if (line.getStations().contains(station)) {
            return new ResponseVo(ResponseCode.LOGIC_ERROR, "已经存在该站台信息");
        }

        line.addStation(station);

        this.lineService.save(line);

        return new ResponseVo(ResponseCode.OK, "增加站台成功");
    }

    @RequestMapping(value = "/{id}/removeStation", method = RequestMethod.POST)
    public ResponseVo removeStation(@PathVariable Long id, @RequestParam Long stationId) {
        Line line = this.lineService.findOne(id);
        Station station = this.stationService.findOne(stationId);

        if (line == null) {
            return new ResponseVo(ResponseCode.NOT_FOUND, "未找到线路信息");
        }

        if (station == null) {
            return new ResponseVo(ResponseCode.NOT_FOUND, "未找到站台信息");
        }

        line.removeStation(station);

        this.lineService.save(line);

        return new ResponseVo(ResponseCode.OK, "删除站台成功");
    }

    @RequestMapping(value = "/changeSort", method = RequestMethod.POST)
    public ResponseVo changeSort() {
        Page<Line> lines = this.lineService.findAll(1, 9999, "","");

        List<LineDTO> dtos = new ArrayList<LineDTO>();
        for (Line line : lines.getContent()
                ) {
            dtos.add(new LineDTO(line));
        }

        return new ResponseVo(lines);
    }

    @RequestMapping(value = "/{id}/getStations", method = RequestMethod.GET)
    public ResponseVo getStations(@PathVariable long id) {
        Line line = this.lineService.findOne(id);

        if (line == null) {
            return new ResponseVo(ResponseCode.NOT_FOUND, "未找到线路信息");
        }

        return new ResponseVo(line.getStations());
    }

    @RequestMapping(value = "/findByStations", method = RequestMethod.GET)
    public ResponseVo findByStations(@RequestParam Long startStationId, @RequestParam Long endStationId) {
        HashMap<String, String> results = this.lineService.findByStations(startStationId, endStationId);

        if (results == null || results.size() == 0) {
            return new ResponseVo(ResponseCode.NOT_FOUND, "未找到线路信息");
        }

        List<TicketDTO> dtos = new ArrayList<>();
        //途经线路，途经站台，站台数，票价
        for (int i = 0; i < results.keySet().size(); i++) {
            String key = (String)results.keySet().toArray()[i];
            String name = (String)results.get(key);

            //这里使用order来生成价格
            Order order = new Order();
            order.setFangan(key);

            TicketDTO ticketDTO = new TicketDTO();
            ticketDTO.setStartStationId(startStationId);
            ticketDTO.setEndStationId(endStationId);
            ticketDTO.setFangan(key);
            ticketDTO.setFanganCN(name);
            ticketDTO.setPrice(order.getTotalAmount());

            dtos.add(ticketDTO);
        }

        return new ResponseVo(dtos);
    }
}
