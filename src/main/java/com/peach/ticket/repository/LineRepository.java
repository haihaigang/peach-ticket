package com.peach.ticket.repository;

import com.peach.ticket.domain.Line;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by hgwang on 2017/2/13.
 */
public interface LineRepository extends Repository<Line, Long> {

    void save(Line entity);

    void delete(Long id);

    Line findById(Long id);

    List<Line> findAll();

    Page<Line> findAll(Pageable pageable);

    Page<Line> findByCodeContaining(String code, Pageable pageable);

    Page<Line> findByNameContaining(String name, Pageable pageable);

    Page<Line> findByCodeContainingAndNameContaining(String code, String name, Pageable pageable);

}
