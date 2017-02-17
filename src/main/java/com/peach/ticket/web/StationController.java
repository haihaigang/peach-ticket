package com.peach.ticket.web;

import com.peach.ticket.domain.Station;
import com.peach.ticket.service.StationService;
import com.peach.ticket.web.dto.StationDTO;
import com.peach.ticket.web.response.ResponseCode;
import com.peach.ticket.web.response.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgwang on 2017/2/13.
 */
@RestController
@RequestMapping(value = "/api/station")
public class StationController {
    @Autowired
    private StationService stationService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseVo save(
            @RequestParam(required = false) int id,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) int status
    ) {
        Station oldStation = null;
        Station station = null;

        if (id != 0) {
            oldStation = this.stationService.findOne(id);
        }

        if (oldStation == null) {
//            TODO 新数据需要校验数据完整性
            station = new Station();
            station.setId(id);
        } else {
            station = oldStation;
        }

        station.setCode(code);
        station.setName(name);
        station.setStatus(status);

        this.stationService.save(station);
        return new ResponseVo(ResponseCode.OK, "保存成功");
    }

    @RequestMapping(value = "/{id}/remove", method = RequestMethod.POST)
    public ResponseVo remove(@PathVariable Long id) {
        this.stationService.remove(id);

        return new ResponseVo(ResponseCode.OK, "删除成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseVo getOne(@PathVariable Long id) {
        Station station = this.stationService.findOne(id);
        if (station == null) {
            return new ResponseVo(ResponseCode.NOT_FOUND, "未找到当前的用户");
        }

        return new ResponseVo(station);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseVo findAll() {
        Page<Station> stations = this.stationService.findAll();

        List<StationDTO> dtos = new ArrayList<StationDTO>();
        for (Station station : stations.getContent()
                ) {
            dtos.add(new StationDTO(station));
        }

        return new ResponseVo(stations);
    }

    @RequestMapping(value = "/enabled/{id}", method = RequestMethod.POST)
    public ResponseVo enabled(@PathVariable Long id, @RequestParam int status) {
        Station station = this.stationService.findOne(id);
        if (station == null) {
            return new ResponseVo(ResponseCode.NOT_FOUND, "未找到数据");
        }

        station.setStatus(status);

        this.stationService.save(station);

        return new ResponseVo(ResponseCode.OK, "操作成功");
    }

    @RequestMapping(value = "/findByCode", method = RequestMethod.GET)
    public ResponseVo findByCode(@RequestParam String code) {
        List<Station> stations = this.stationService.findByCode(code);

        return new ResponseVo(stations);
    }
}
