package com.example.lab9.services;

import com.example.lab9.dtos.CountryDTO;
import com.example.lab9.mappers.CountryMapper;
import com.example.lab9.models.Country;
import com.example.lab9.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryMapper countryMapper;

    public List<CountryDTO> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(countryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CountryDTO getCountryById(Long id) {
        Optional<Country> country = countryRepository.findById(id);
        return country.map(countryMapper::toDto).orElse(null);
    }

    public CountryDTO createCountry(CountryDTO countryDTO) {
        Country country = countryMapper.toEntity(countryDTO);
        Country saved = countryRepository.save(country);
        return countryMapper.toDto(saved);
    }

    public CountryDTO updateCountry(Long id, CountryDTO countryDTO) {
        Optional<Country> existing = countryRepository.findById(id);
        if (existing.isPresent()) {
            Country country = existing.get();
            country.setName(countryDTO.getName());
            country.setCode(countryDTO.getCode());
            Country updated = countryRepository.save(country);
            return countryMapper.toDto(updated);
        }
        return null;
    }

    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }
}