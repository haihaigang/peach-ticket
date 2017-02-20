package com.peach.ticket.service.impl;

import com.peach.ticket.domain.Line;
import com.peach.ticket.domain.Station;
import com.peach.ticket.repository.LineRepository;
import com.peach.ticket.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by hgwang on 2017/1/29.
 */
@Service
public class LineServiceImpl implements LineService {
    @Autowired
    private LineRepository lineRepository;

    public void save(Line entity) {
        lineRepository.save(entity);
    }

    public void remove(long id) {
        lineRepository.delete(id);
    }

    public Line findOne(long id) {
        return lineRepository.findById(id);
    }

    public Page<Line> findAll() {
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public int getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };

        return this.lineRepository.findAll(pageable);
    }

    public HashMap<String, String> findByStations(Long startStationId, Long endStationId) {
        if (startStationId == endStationId) {
            return null;
        }

        Page<Line> allLines = this.findAll();

        //找出所有的树的关系
        //分别找出所有以起始点开始的段，然后依次找下去直到找到目标点

        HashMap<Long, Station> trees = new HashMap<>();

        for (Line line : allLines.getContent()
                ) {
            List<Station> stations = line.getStations();

            int size = stations.size();
            if (size <= 1) {
                continue;
            }

            for (int i = 0; i < size; i++) {
                Station station = stations.get(i);

                if (!trees.containsKey(station.getId())) {
                    trees.put(station.getId(), station);
                }

                if ((i + 1) < size) {
                    Station nextStation = stations.get(i + 1);
                    trees.get(station.getId()).addStation(nextStation);
                }
                if ((i - 1) >= 0) {
                    Station nextStation = stations.get(i - 1);
                    trees.get(station.getId()).addStation(nextStation);
                }
            }
        }

        HashMap<String, String> results = new HashMap<>();
        Long startId = startStationId;
        List<Station> tempArr = new ArrayList<>();

        while (true) {
            Station currentStation = trees.get(startId);

            if (currentStation == null) {
                break;
            }

            tempArr.add(currentStation);

            if(currentStation.hasAllLines()){
                break;
            }

            // 判断当前节点是否是目标节点，若是则记录到查找结果中并查找下一个，
            // 否则判断当前节点的子节点是否存在，不存在则记录到查找结果中并查找下一个，存在则查找子节点的第一个
            if (startId == endStationId || currentStation.getNextStations().isEmpty()) {
                String tempKey = new String();
                String tempName = new String();
                for (int i = 0; i < tempArr.size(); i++) {
                    tempKey += tempArr.get(i).getId() + "-";
                    tempName += tempArr.get(i).getName() + "-";
                }
                //分别移除最后的分隔符
                tempKey = tempKey.substring(0, tempKey.length() - 1);
                tempName = tempName.substring(0, tempName.length() - 1);

                results.put(tempKey, tempName);
                startId = startStationId;
                tempArr = new ArrayList<>();

                //清除除了开始节点的所有计数
                for (int i = 0; i < trees.keySet().size(); i++) {
                    Long k =(Long)trees.keySet().toArray()[i];
                    if(k != startStationId){
                        trees.get(k).resetLines();
                    }
                }
            } else {
                if(currentStation.hasAllLines()){
                    startId = startStationId;
                    tempArr = new ArrayList<>();

                    //清除除了开始节点的所有计数
                    for (int i = 0; i < trees.keySet().size(); i++) {
                        Long k =(Long)trees.keySet().toArray()[i];
                        if(k != startStationId){
                            trees.get(k).resetLines();
                        }
                    }
                }else{
                    Set<Long> keys2 = currentStation.getNextStations().keySet();
                    for (int i = 0; i < keys2.size(); i++) {
                        Long temp = (Long)keys2.toArray()[i];
                        Station tempStation = currentStation.getNextStations().get(temp);

                        // 处理key总是数字小的在前
                        String key = new String ();
                        if(startId < temp){
                            key = startId.toString() + "-" + temp.toString();
                        }else{
                            key = temp.toString() + "-" + startId.toString();
                        }

                        if(currentStation.getLines().get(key)){
                            continue;
                        }

                        if(tempArr.contains(tempStation)){
                            currentStation.addLines(key);
                        }else{
                            currentStation.addLines(key);
                            startId = temp;
                            break;
                        }
                    }
                }
            }
        }

        return results;
    }

}
