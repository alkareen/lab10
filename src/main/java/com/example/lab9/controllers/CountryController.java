package com.example.lab9.controllers;

import com.example.lab9.dtos.CountryDTO;
import com.example.lab9.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping
    public List<CountryDTO> getAll() {
        return countryService.getAllCountries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getById(@PathVariable("id") Long id) {
        CountryDTO dto = countryService.getCountryById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CountryDTO> create(@RequestBody CountryDTO countryDTO) {
        CountryDTO created = countryService.createCountry(countryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryDTO> update(@PathVariable("id") Long id,
                                             @RequestBody CountryDTO countryDTO) {
        CountryDTO updated = countryService.updateCountry(id, countryDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }
}