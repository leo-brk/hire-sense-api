package com.boreksolutions.hiresenseapi.core.company;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.company.dto.request.CompanyFilter;
import com.boreksolutions.hiresenseapi.core.company.dto.request.CreateCompany;
import com.boreksolutions.hiresenseapi.core.company.dto.request.UpdateCompany;
import com.boreksolutions.hiresenseapi.core.company.dto.response.CompanyDto;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

    Long createCompany(CreateCompany createCompany);

    CompanyDto findCompanyById(Long id);

    PageObject<CompanyDto> filter(CompanyFilter filter, Pageable pageable);

    void updateCompany(UpdateCompany updateCompany);

    void deleteCompanyById(Long id);
}