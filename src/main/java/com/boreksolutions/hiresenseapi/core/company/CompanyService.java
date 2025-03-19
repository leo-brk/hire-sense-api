package com.boreksolutions.hiresenseapi.core.company;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.company.dto.request.CreateCompany;
import com.boreksolutions.hiresenseapi.core.company.dto.response.CompanyDto;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

    CompanyDto findCompanyById(Long id);

    CompanyDto findCompanyByName(String name);

    PageObject<CompanyDto> getCompaniesPage(Pageable pageable);

    Company createCompany(CreateCompany createCompany);

    void updateCompany(CompanyDto companyDto);

    void deleteCompanyById(Long id);
}