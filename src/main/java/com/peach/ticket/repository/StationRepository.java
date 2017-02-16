package com.peach.ticket.repository;

import com.peach.ticket.domain.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 * Created by hgwang on 2017/2/13.
 */
public interface StationRepository extends Repository<Station, Long> {

    void save(Station entity);

    void delete(Long id);

    Station findById(Long id);

    Page<Station> findAll(Pageable pageable);
}
