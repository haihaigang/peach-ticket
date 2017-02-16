package com.peach.ticket.repository;

import com.peach.ticket.domain.Line;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 * Created by hgwang on 2017/2/13.
 */
public interface LineRepository extends Repository<Line, Long> {

    void save(Line entity);

    void delete(Long id);

    Line findById(Long id);

    Page<Line> findAll(Pageable pageable);
}
