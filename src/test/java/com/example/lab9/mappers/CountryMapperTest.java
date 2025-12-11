package com.example.lab9.mappers;

import com.example.lab9.dtos.CountryDTO;
import com.example.lab9.models.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CountryMapperTest {

    private CountryMapper countryMapper;

    @BeforeEach
    void setUp() {
        countryMapper = Mappers.getMapper(CountryMapper.class);
    }

    @Test
    void toDto() {

        Country country = new Country();
        country.setId(1L);
        country.setName("United States");
        country.setCode("US");

        CountryDTO dto = countryMapper.toDto(country);

        assertNotNull(dto);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("United States");
        assertThat(dto.getCode()).isEqualTo("US");
    }

    @Test
    void toEntity() {
        CountryDTO dto = new CountryDTO();
        dto.setId(1L);
        dto.setName("United States");
        dto.setCode("US");

        Country country = countryMapper.toEntity(dto);

        assertNotNull(country);
        assertThat(country.getId()).isEqualTo(1L);
        assertThat(country.getName()).isEqualTo("United States");
        assertThat(country.getCode()).isEqualTo("US");
    }

    @Test
    void toDto_2() {
        CountryDTO dto = countryMapper.toDto(null);
//КЧАУУ
        assertNull(dto);
    }

    @Test
    void toEntity_2() {
        Country country = countryMapper.toEntity(null);
//
        assertNull(country);
    }

    @Test
    void toDtoList() {
        Country country1 = new Country();
        country1.setId(1L);
        country1.setName("United States");
        country1.setCode("US");

        Country country2 = new Country();
        country2.setId(2L);
        country2.setName("China");
        country2.setCode("CN");

        List<Country> countries = Arrays.asList(country1, country2);

        List<CountryDTO> dtos = countries.stream().map(countryMapper::toDto).toList();

        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getId()).isEqualTo(1L);
        assertThat(dtos.get(1).getId()).isEqualTo(2L);
    }
}