package com.boreksolutions.hiresenseapi.core.country;

import com.boreksolutions.hiresenseapi.core.country.dto.request.CreateCountry;
import com.boreksolutions.hiresenseapi.core.country.dto.response.CountryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @PostMapping
    public ResponseEntity<Long> createCountry(@Valid @RequestBody CreateCountry createCountry) {
        return ResponseEntity.ok(countryService.createCountry(createCountry));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable Long id) {
        return ResponseEntity.ok(countryService.getCountryById(id));
    }

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable Long id,
                                                    @Valid @RequestBody CreateCountry updateCountry) {
        return ResponseEntity.ok(countryService.updateCountry(id, updateCountry));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.ok().build();
    }
}