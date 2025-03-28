package com.boreksolutions.hiresenseapi.core.city;

import com.boreksolutions.hiresenseapi.core.city.dto.request.CreateCity;
import com.boreksolutions.hiresenseapi.core.city.dto.response.CityDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public ResponseEntity<Long> createCity(@Valid @RequestBody CreateCity createCity) {
        return ResponseEntity.ok(cityService.createCity(createCity));

    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(@PathVariable Long id) {
        CityDto cityDto = cityService.getCityById(id);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities() {
        List<CityDto> cities = cityService.getAllCities();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityDto> updateCity(@PathVariable Long id, @Valid @RequestBody CreateCity updateCity) {
        CityDto cityDto = cityService.updateCity(id, updateCity);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}