package com.example.lab9.services;

import com.example.lab9.dtos.CountryDto;
import com.example.lab9.mappers.CountryMapper;
import com.example.lab9.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public List<CountryDto> findAll() {
        return countryRepository.findAll().stream()
                .map(c -> {
                    CountryDto dto = new CountryDto();
                    dto.setId(c.getId());
                    dto.setName(c.getName());
                    dto.setCode(c.getCode());
                    return dto;
                })
                .toList();
    }
}