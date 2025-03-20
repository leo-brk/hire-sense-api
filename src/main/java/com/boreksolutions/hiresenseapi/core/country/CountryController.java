package com.boreksolutions.hiresenseapi.core.country;

import com.boreksolutions.hiresenseapi.core.country.dto.request.CreateCountry;
import com.boreksolutions.hiresenseapi.core.country.dto.response.CountryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @PostMapping
    public ResponseEntity<CountryDto> createCountry(@Valid @RequestBody CreateCountry createCountry) {
        CountryDto countryDto = countryService.createCountry(createCountry);
        return new ResponseEntity<>(countryDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable Long id) {
        CountryDto countryDto = countryService.getCountryById(id);
        return new ResponseEntity<>(countryDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryDto> updateCountry(
            @PathVariable Long id, @Valid @RequestBody CreateCountry updateCountry) {
        CountryDto countryDto = countryService.updateCountry(id, updateCountry);
        return new ResponseEntity<>(countryDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}