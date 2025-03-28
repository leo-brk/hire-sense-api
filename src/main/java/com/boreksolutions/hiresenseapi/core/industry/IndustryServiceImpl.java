package com.boreksolutions.hiresenseapi.core.industry;

import com.boreksolutions.hiresenseapi.config.exceptions.models.NotFoundException;
import com.boreksolutions.hiresenseapi.core.industry.dto.request.CreateIndustry;
import com.boreksolutions.hiresenseapi.core.industry.dto.response.IndustryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndustryServiceImpl implements IndustryService {

    private final IndustryRepository industryRepository;
    private final IndustryMapper industryMapper;

    @Override
    public Long createIndustry(CreateIndustry createIndustry) {
        Industry industry = new Industry();
        industry.setName(createIndustry.getName());
        return industryRepository.save(industry).getId();
    }

    @Override
    public IndustryDto getIndustryById(Long id) {
        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Industry with id: " + id + " not found"));
        return industryMapper.entityToDto(industry);
    }

    @Override
    public List<IndustryDto> getAllIndustries() {
        List<Industry> industries = industryRepository.findAll();
        return industryMapper.entityListToDtoList(industries);
    }

    @Override
    public IndustryDto updateIndustry(Long id, CreateIndustry updateIndustry) {
        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Industry with id: " + id + " not found"));
        industry.setName(updateIndustry.getName());
        Industry updatedIndustry = industryRepository.save(industry);
        return industryMapper.entityToDto(updatedIndustry);
    }

    @Override
    public void deleteIndustry(Long id) {
        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Industry with id: " + id + " not found"));
        industry.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        industryRepository.save(industry);
    }
}