package com.peach.ticket.service.impl;

import com.peach.ticket.domain.Sample;
import com.peach.ticket.repository.SampleRepository;
import com.peach.ticket.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by xyqin on 16/4/25.
 */
@Service
public class SampleServiceImpl implements SampleService {

    @Autowired
    private SampleRepository sampleRepository;

    @Override
    public Sample findSample(Long id) {
        return this.sampleRepository.findById(id);
    }

    @Override
    public Page<Sample> getAllSample() {
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

        return this.sampleRepository.findAll(pageable);
    }

    @Override
    public void addSample() {

    }
}
