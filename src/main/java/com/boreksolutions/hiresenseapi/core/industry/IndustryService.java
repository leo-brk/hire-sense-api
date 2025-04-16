package com.boreksolutions.hiresenseapi.core.industry;

import com.boreksolutions.hiresenseapi.core.industry.dto.response.IndustryDto;
import com.boreksolutions.hiresenseapi.core.industry.dto.request.CreateIndustry;

import java.util.List;

public interface IndustryService {

    Long createIndustry(CreateIndustry createIndustry);

    IndustryDto getIndustryById(Long id);

    List<IndustryDto> getAllIndustries();

    List<IndustryDto> findIndustriesStartingWith(String name);

    List<IndustryDto> searchIndustriesByName(String name);

    IndustryDto updateIndustry(Long id, CreateIndustry updateIndustry);

    void deleteIndustry(Long id);
}