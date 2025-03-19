package com.boreksolutions.hiresenseapi.core.industry;

import com.boreksolutions.hiresenseapi.core.industry.dto.response.IndustryDto;
import com.boreksolutions.hiresenseapi.core.industry.dto.request.CreateIndustry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndustryServiceImpl implements IndustryService {

    private final IndustryRepository industryRepository;

    @Override
    public IndustryDto createIndustry(CreateIndustry createIndustry) {
        Industry industry = new Industry();
        industry.setName(createIndustry.getName());
        Industry savedIndustry = industryRepository.save(industry);
        return mapToDto(savedIndustry);
    }

    @Override
    public List<IndustryDto> getAllIndustries() {
        return industryRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public IndustryDto getIndustryById(Long id) {
        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Industry not found"));
        return mapToDto(industry);
    }

    @Override
    public IndustryDto updateIndustry(Long id, CreateIndustry updateIndustry) {
        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Industry not found"));
        industry.setName(updateIndustry.getName());
        Industry updatedIndustry = industryRepository.save(industry);
        return mapToDto(updatedIndustry);
    }

    @Override
    public void deleteIndustry(Long id) {
        industryRepository.deleteById(id);
    }

    private IndustryDto mapToDto(Industry industry) {
        IndustryDto industryDto = new IndustryDto();
        industryDto.setId(industry.getId());
        industryDto.setName(industry.getName());
        return industryDto;
    }
}