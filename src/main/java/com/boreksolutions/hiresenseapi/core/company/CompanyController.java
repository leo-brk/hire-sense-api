package com.boreksolutions.hiresenseapi.core.company;

import com.boreksolutions.hiresenseapi.common.PageObject;
import com.boreksolutions.hiresenseapi.core.company.dto.request.CompanyFilter;
import com.boreksolutions.hiresenseapi.core.company.dto.request.CreateCompany;
import com.boreksolutions.hiresenseapi.core.company.dto.request.UpdateCompany;
import com.boreksolutions.hiresenseapi.core.company.dto.response.CompanyDto;
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

    @PostMapping
    public ResponseEntity<Long> createCompany(@Valid @RequestBody CreateCompany createCompany) {
        return ResponseEntity.ok(companyService.createCompany(createCompany));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable("id") Long id) {
        return ResponseEntity.ok(companyService.findCompanyById(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageObject<CompanyDto>> getCompanies(@RequestBody CompanyFilter filter, Pageable pageable) {
        return ResponseEntity.ok(companyService.filter(filter, pageable));
    }

    @PutMapping
    public ResponseEntity<Void> updateCompany(@Valid @RequestBody UpdateCompany updateCompany) {
        companyService.updateCompany(updateCompany);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompanyById(id);
        return ResponseEntity.ok().build();
    }
}