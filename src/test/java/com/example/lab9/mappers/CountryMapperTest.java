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
    void toDto_shouldMapEntityToDto() {
        // Given
        Country country = new Country();
        country.setId(1L);
        country.setName("United States");
        country.setCode("US");

        // When
        CountryDTO dto = countryMapper.toDto(country);

        // Then
        assertNotNull(dto);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("United States");
        assertThat(dto.getCode()).isEqualTo("US");
    }

    @Test
    void toEntity_shouldMapDtoToEntity() {
        // Given
        CountryDTO dto = new CountryDTO();
        dto.setId(1L);
        dto.setName("United States");
        dto.setCode("US");

        // When
        Country country = countryMapper.toEntity(dto);

        // Then
        assertNotNull(country);
        assertThat(country.getId()).isEqualTo(1L);
        assertThat(country.getName()).isEqualTo("United States");
        assertThat(country.getCode()).isEqualTo("US");
    }

    @Test
    void toDto_shouldHandleNullEntity() {
        // When
        CountryDTO dto = countryMapper.toDto(null);

        // Then
        assertNull(dto);
    }

    @Test
    void toEntity_shouldHandleNullDto() {
        // When
        Country country = countryMapper.toEntity(null);

        // Then
        assertNull(country);
    }

    @Test
    void toDtoList_shouldMapListOfEntitiesToDtos() {
        // Given
        Country country1 = new Country();
        country1.setId(1L);
        country1.setName("United States");
        country1.setCode("US");

        Country country2 = new Country();
        country2.setId(2L);
        country2.setName("China");
        country2.setCode("CN");

        List<Country> countries = Arrays.asList(country1, country2);

        // When
        List<CountryDTO> dtos = countries.stream().map(countryMapper::toDto).toList(); // Since mapper doesn't have list method, simulate

        // Then
        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getId()).isEqualTo(1L);
        assertThat(dtos.get(1).getId()).isEqualTo(2L);
    }
}