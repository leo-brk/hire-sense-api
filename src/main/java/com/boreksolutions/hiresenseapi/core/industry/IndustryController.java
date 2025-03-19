package com.boreksolutions.hiresenseapi.core.industry;

import com.boreksolutions.hiresenseapi.core.industry.dto.response.IndustryDto;
import com.boreksolutions.hiresenseapi.core.industry.dto.request.CreateIndustry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/industries")
@RequiredArgsConstructor
public class IndustryController {

    private final IndustryService industryService;

    // Create a new industry
    @PostMapping
    public ResponseEntity<IndustryDto> createIndustry(@Valid @RequestBody CreateIndustry createIndustry) {
        IndustryDto industryDto = industryService.createIndustry(createIndustry);
        return new ResponseEntity<>(industryDto, HttpStatus.CREATED);
    }

    // Get all industries
    @GetMapping
    public ResponseEntity<List<IndustryDto>> getAllIndustries() {
        List<IndustryDto> industries = industryService.getAllIndustries();
        return new ResponseEntity<>(industries, HttpStatus.OK);
    }

    // Get an industry by ID
    @GetMapping("/{id}")
    public ResponseEntity<IndustryDto> getIndustryById(@PathVariable Long id) {
        IndustryDto industryDto = industryService.getIndustryById(id);
        return new ResponseEntity<>(industryDto, HttpStatus.OK);
    }

    // Update an industry by ID
    @PutMapping("/{id}")
    public ResponseEntity<IndustryDto> updateIndustry(
            @PathVariable Long id, @Valid @RequestBody CreateIndustry updateIndustry) {
        IndustryDto industryDto = industryService.updateIndustry(id, updateIndustry);
        return new ResponseEntity<>(industryDto, HttpStatus.OK);
    }

    // Delete an industry by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndustry(@PathVariable Long id) {
        industryService.deleteIndustry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}