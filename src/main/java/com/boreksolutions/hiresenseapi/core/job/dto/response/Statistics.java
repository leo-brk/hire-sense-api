package com.boreksolutions.hiresenseapi.core.job.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {
    private String name;
    private List<StatItem> statItems;

    public Statistics(String name) {
        this.name = name;
    }
}
