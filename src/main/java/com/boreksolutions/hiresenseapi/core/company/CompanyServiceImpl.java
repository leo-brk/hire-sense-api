package com.boreksolutions.hiresenseapi.core.company;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.company.dto.request.CreateCompany;
import com.boreksolutions.hiresenseapi.core.company.dto.response.CompanyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyDto findCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + id));
        return companyMapper.entityToDto(company);
    }
    @Override
    public CompanyDto findCompanyByName(String name) {
        Company company = companyRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Company not found with name: " + name));
        return companyMapper.entityToDto(company);
    }
    @Override
    public PageObject<CompanyDto> getCompaniesPage(Pageable pageable) {
        Page<Company> companiesPage = companyRepository.findAll(pageable);
        return companyMapper.pageToPageObject(companiesPage);
    }
    @Override
    public Company createCompany(CreateCompany createCompany) {
        Company company = companyMapper.dtoToEntity(createCompany);
        return companyRepository.save(company);
    }
    @Override
    public void updateCompany( CompanyDto companyDto) {
        Company company = companyRepository.findById(companyDto.getId())
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + companyDto.getId()));
        companyMapper.updateDtoToEntity(companyDto, company);
        companyRepository.save(company);
    }
    @Override
    public void deleteCompanyById(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new RuntimeException("Company not found with ID: " + id);
        }
        companyRepository.deleteById(id);
    }
}