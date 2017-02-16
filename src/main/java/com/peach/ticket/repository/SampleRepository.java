package com.peach.ticket.repository;

import com.peach.ticket.domain.Sample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 * Created by xyqin on 16/4/24.
 */
public interface SampleRepository extends Repository<Sample, Long> {

    Sample findById(Long id);

    Page<Sample> findAll(Pageable pageable);

}
