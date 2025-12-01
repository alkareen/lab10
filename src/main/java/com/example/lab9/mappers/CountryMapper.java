package com.example.lab9.mappers;
import com.example.lab9.dtos.CountryDto;
import com.example.lab9.models.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryDto toDto(Country country);
    Country toEntity(CountryDto dto);
}