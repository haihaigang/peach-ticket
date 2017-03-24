package com.peach.ticket.service.impl;

import com.peach.ticket.domain.Station;
import com.peach.ticket.repository.StationRepository;
import com.peach.ticket.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hgwang on 2017/2/13.
 */
@Service
public class StationServiceImpl implements StationService {
    @Autowired
    private StationRepository stationRepository;

    public void save(Station entity) {
        this.stationRepository.save(entity);
    }

    public void remove(long id) {
        this.stationRepository.delete(id);
    }

    public Station findOne(long id) {
        return this.stationRepository.findById(id);
    }

    public Page<Station> findAll(int page, int size, String code, String name) {
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return page;
            }

            @Override
            public int getPageSize() {
                return size;
            }

            @Override
            public int getOffset() {
                return (page - 1) * size;
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

        if(!code.isEmpty() && !name.isEmpty()){
            return this.stationRepository.findByCodeContainingAndNameContaining(code, name, pageable);
        } else if(!code.isEmpty()){
            return this.stationRepository.findByCodeContaining(code, pageable);
        } else if(!name.isEmpty()){
            return this.stationRepository.findByNameContaining(name, pageable);
        }else{
            return this.stationRepository.findAll(pageable);
        }
    }

    public List<Station> findByCode(String code){
        return this.stationRepository.findByCodeContaining(code);
    }
}
