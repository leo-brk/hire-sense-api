package com.boreksolutions.hiresenseapi.core.company;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.config.exceptions.models.NotFoundException;
import com.boreksolutions.hiresenseapi.core.company.dto.request.CompanyFilter;
import com.boreksolutions.hiresenseapi.core.company.dto.request.CreateCompany;
import com.boreksolutions.hiresenseapi.core.company.dto.request.UpdateCompany;
import com.boreksolutions.hiresenseapi.core.company.dto.response.CompanyDto;
import com.boreksolutions.hiresenseapi.core.industry.Industry;
import com.boreksolutions.hiresenseapi.core.industry.IndustryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyCriteriaBuilder criteriaBuilder;
    private final CompanyMapper companyMapper;
    private final IndustryRepository industryRepository;

    @Override
    public Long createCompany(CreateCompany createCompany) {
        Company company = companyMapper.dtoToEntity(createCompany);

        Industry industry = industryRepository.findById(createCompany.getIndustryId())
                .orElseThrow(() -> new NotFoundException(
                        "Company not created: Industry with id" + createCompany.getIndustryId() + " not found"));

        company.setIndustry(industry);

        return companyRepository.save(company).getId();
    }

    public Long count() {
        return companyRepository.count();
    }

    @Override
    public CompanyDto findCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + id));
        return companyMapper.entityToDto(company);
    }

    @Override
    public PageObject<CompanyDto> filter(CompanyFilter filter, Pageable pageable) {
        Page<Company> companiesPage = criteriaBuilder.filterCompanies(filter, pageable);
        return companyMapper.pageToPageObject(companiesPage);
    }

    @Override
    public void updateCompany(UpdateCompany updateCompany) {
        Company company = companyRepository.findById(updateCompany.getId())
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + updateCompany.getId()));

        if (updateCompany.getIndustryId() != null) {
            Industry industry = industryRepository.findById(updateCompany.getIndustryId())
                    .orElseThrow(() -> new NotFoundException(
                            "Company not updated: Industry with id" + updateCompany.getIndustryId() + " not found"));
            company.setIndustry(industry);
        }

        companyMapper.updateDtoToEntity(updateCompany, company);
        companyRepository.save(company);
    }

    @Override
    public void deleteCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + id));
        company.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        companyRepository.save(company);
    }
}