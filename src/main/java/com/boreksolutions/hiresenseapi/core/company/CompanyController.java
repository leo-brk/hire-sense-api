package com.boreksolutions.hiresenseapi.core.company;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.company.dto.response.CompanyDto;
import com.boreksolutions.hiresenseapi.core.company.dto.request.CreateCompany;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable("id") Long id) {
        return ResponseEntity.ok(companyService.findCompanyById(id));
    }

    @GetMapping
    public ResponseEntity<CompanyDto> getCompanyByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(companyService.findCompanyByName(name));
    }

    @GetMapping("/list")
    public ResponseEntity<PageObject<CompanyDto>> getCompanies(Pageable pageable) {
        return ResponseEntity.ok(companyService.getCompaniesPage(pageable));
    }

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@Valid @RequestBody CreateCompany createCompany) {
        Company company = companyService.createCompany(createCompany);
        return ResponseEntity.ok(companyService.findCompanyById(company.getId()));
    }

    @PutMapping
    public ResponseEntity<Void> updateCompany(@Valid @RequestBody CompanyDto companyDto) {
        companyService.updateCompany(companyDto);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompanyById(id);
        return ResponseEntity.noContent().build();
    }
}