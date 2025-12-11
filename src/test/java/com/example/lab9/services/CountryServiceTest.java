package com.example.lab9.services;

import com.example.lab9.dtos.CountryDTO;
import com.example.lab9.mappers.CountryMapper;
import com.example.lab9.models.Country;
import com.example.lab9.repositories.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    private CountryRepository countryRepository;
    private CountryMapper countryMapper;
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        countryRepository = mock(CountryRepository.class);
        countryMapper = mock(CountryMapper.class);
        countryService = new CountryService();
        ReflectionTestUtils.setField(countryService, "countryRepository", countryRepository);
        ReflectionTestUtils.setField(countryService, "countryMapper", countryMapper);
    }

    @Test
    void getAllCountries() {
        Country country1 = new Country();
        country1.setId(1L);
        country1.setName("US");
        country1.setCode("US");

        Country country2 = new Country();
        country2.setId(2L);
        country2.setName("China");
        country2.setCode("CN");

        List<Country> countries = Arrays.asList(country1, country2);

        CountryDTO dto1 = new CountryDTO();
        dto1.setId(1L);
        dto1.setName("US");
        dto1.setCode("US");

        CountryDTO dto2 = new CountryDTO();
        dto2.setId(2L);
        dto2.setName("China");
        dto2.setCode("CN");

        when(countryRepository.findAll()).thenReturn(countries);
        when(countryMapper.toDto(country1)).thenReturn(dto1);
        when(countryMapper.toDto(country2)).thenReturn(dto2);

        List<CountryDTO> result = countryService.getAllCountries();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
        verify(countryRepository, times(1)).findAll();
    }

    @Test
    void getCountryById() {
        Long id = 1L;
        Country country = new Country();
        country.setId(id);
        country.setName("US");
        country.setCode("US");

        CountryDTO dto = new CountryDTO();
        dto.setId(id);
        dto.setName("US");
        dto.setCode("US");

        when(countryRepository.findById(id)).thenReturn(Optional.of(country));
        when(countryMapper.toDto(country)).thenReturn(dto);

        CountryDTO result = countryService.getCountryById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        verify(countryRepository, times(1)).findById(id);
    }

    @Test
    void getCountryById_shouldReturnNullWhenNotFound() {
        Long id = 1L;
        when(countryRepository.findById(id)).thenReturn(Optional.empty());

        CountryDTO result = countryService.getCountryById(id);

        assertThat(result).isNull();
        verify(countryRepository, times(1)).findById(id);
    }

    @Test
    void createCountry() {
        CountryDTO dto = new CountryDTO();
        dto.setName("US");
        dto.setCode("US");

        Country country = new Country();
        country.setName("US");
        country.setCode("US");

        Country saved = new Country();
        saved.setId(1L);
        saved.setName("US");
        saved.setCode("US");

        CountryDTO savedDto = new CountryDTO();
        savedDto.setId(1L);
        savedDto.setName("US");
        savedDto.setCode("US");

        when(countryMapper.toEntity(dto)).thenReturn(country);
        when(countryRepository.save(country)).thenReturn(saved);
        when(countryMapper.toDto(saved)).thenReturn(savedDto);

        CountryDTO result = countryService.createCountry(dto);

        assertThat(result.getId()).isEqualTo(1L);
        verify(countryRepository, times(1)).save(any(Country.class));
    }

    @Test
    void updateCountry() {
        Long id = 1L;
        CountryDTO dto = new CountryDTO();
        dto.setName("Updated US");
        dto.setCode("US");

        Country existing = new Country();
        existing.setId(id);
        existing.setName("US");
        existing.setCode("US");

        Country updated = new Country();
        updated.setId(id);
        updated.setName("Updated US");
        updated.setCode("US");

        CountryDTO updatedDto = new CountryDTO();
        updatedDto.setId(id);
        updatedDto.setName("Updated US");
        updatedDto.setCode("US");

        when(countryRepository.findById(id)).thenReturn(Optional.of(existing));
        when(countryRepository.save(existing)).thenReturn(updated);
        when(countryMapper.toDto(updated)).thenReturn(updatedDto);

        CountryDTO result = countryService.updateCountry(id, dto);

        assertThat(result.getName()).isEqualTo("Updated US");
        verify(countryRepository, times(1)).save(existing);
    }

    @Test
    void updateCountry_2() {
        Long id = 1L;
        CountryDTO dto = new CountryDTO();
        when(countryRepository.findById(id)).thenReturn(Optional.empty());

        CountryDTO result = countryService.updateCountry(id, dto);

        assertThat(result).isNull();
        verify(countryRepository, never()).save(any());
    }

    @Test
    void deleteCountry() {
        Long id = 1L;

        countryService.deleteCountry(id);

        verify(countryRepository, times(1)).deleteById(id);
    }
}