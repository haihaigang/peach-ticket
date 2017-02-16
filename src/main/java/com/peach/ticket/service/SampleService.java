package com.peach.ticket.service;

import com.peach.ticket.domain.Sample;
import org.springframework.data.domain.Page;

/**
 * Created by xyqin on 16/4/24.
 */
public interface SampleService {

    Sample findSample(Long id);

    Page<Sample> getAllSample();

    void addSample();
}
