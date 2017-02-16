package com.peach.ticket.web;

import com.peach.ticket.domain.Sample;
import com.peach.ticket.service.SampleService;
import com.peach.ticket.web.dto.SampleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xyqin on 16/4/24.
 */
@RestController
public class SampleController {

    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    private SampleService sampleService;

    @RequestMapping(value = "/samples/{id}", method = RequestMethod.GET)
    public SampleDTO getSample(@PathVariable Long id) {
        logger.info("/samples/" + id);
        Sample sample = this.sampleService.findSample(id);
        SampleDTO dto = new SampleDTO(sample);
        return dto;
    }

    @RequestMapping(value="/samples/list", method = RequestMethod.GET)
    public List<SampleDTO> getSamples(){
        Page<Sample> samples = this.sampleService.getAllSample();

        List<SampleDTO> dtos = new ArrayList<SampleDTO>();
        for (Sample sample: samples.getContent()
             ) {
            dtos.add(new SampleDTO(sample));
        }

        return dtos;
    }

}
