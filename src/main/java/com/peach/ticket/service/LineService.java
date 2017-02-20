package com.peach.ticket.service;

import com.peach.ticket.domain.Line;
import org.springframework.data.domain.Page;

import java.util.HashMap;

/**
 * Created by hgwang on 2017/1/25.
 */
public interface LineService {

    //1. /api/line/save 保存更新
    public void save(Line entity);

    //2. /api/line/remove 删除
    public void remove(long id);

    //3. /api/line/1 查询
    public Line findOne(long id);

    //4. /api/line/findAll 获取所有
    public Page<Line> findAll();

    //5. /api/line/findByStations 根据站台查询线路
    public HashMap<String, String> findByStations(Long startStationId, Long endStationId);

}
