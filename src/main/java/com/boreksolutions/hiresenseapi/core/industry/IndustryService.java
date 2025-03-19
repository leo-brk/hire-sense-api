package com.boreksolutions.hiresenseapi.core.industry;

import com.boreksolutions.hiresenseapi.core.industry.dto.response.IndustryDto;
import com.boreksolutions.hiresenseapi.core.industry.dto.request.CreateIndustry;

import java.util.List;

public interface IndustryService {

    IndustryDto createIndustry(CreateIndustry createIndustry);

    List<IndustryDto> getAllIndustries();

    IndustryDto getIndustryById(Long id);

    IndustryDto updateIndustry(Long id, CreateIndustry updateIndustry);

    void deleteIndustry(Long id);
}