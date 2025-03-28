package com.boreksolutions.hiresenseapi.core.industry;

import com.boreksolutions.hiresenseapi.core.industry.dto.request.CreateIndustry;
import com.boreksolutions.hiresenseapi.core.industry.dto.response.IndustryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/industry")
@RequiredArgsConstructor
public class IndustryController {

    private final IndustryService industryService;

    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody CreateIndustry createIndustry) {
        return ResponseEntity.ok(industryService.createIndustry(createIndustry));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IndustryDto> read(@PathVariable Long id) {
        return ResponseEntity.ok(industryService.getIndustryById(id));
    }

    @GetMapping
    public ResponseEntity<List<IndustryDto>> findAll() {
        return ResponseEntity.ok(industryService.getAllIndustries());
    }

    @PutMapping("/{id}")
    public ResponseEntity<IndustryDto> update(@PathVariable Long id,
                                              @Valid @RequestBody CreateIndustry updateIndustry) {
        return ResponseEntity.ok(industryService.updateIndustry(id, updateIndustry));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        industryService.deleteIndustry(id);
        return ResponseEntity.ok().build();
    }
}