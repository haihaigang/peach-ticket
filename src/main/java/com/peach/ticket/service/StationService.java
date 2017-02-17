package com.peach.ticket.service;

import com.peach.ticket.domain.Station;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by hgwang on 2017/1/25.
 */
public interface StationService {

    //1. /api/station/save 保存更新
    public void save(Station entity);

    //2. /api/station/remove 删除
    public void remove(long id);

    //3. /api/station/1 查询
    public Station findOne(long id);

    //4. /api/station/findAll 获取所有
    public Page<Station> findAll();

    //4. /api/station/findAll 获取所有
    public List<Station> findByCode(String code);
}
