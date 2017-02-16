package com.peach.ticket.web.dto;

import com.peach.ticket.domain.Sample;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by xyqin on 16/5/19.
 */
public class SampleDTO {

    private Long id;

    @NotNull
    @Size(min=2, max=30)
    private String name;

    public SampleDTO() { }

    public SampleDTO(Sample sample) {
        this.setId(sample.getId());
        this.setName(sample.getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
