package com.example.lab9.mappers;
import com.example.lab9.dtos.CountryDTO;
import com.example.lab9.models.Country;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryMapper {

    CountryDTO toDto(Country country);

    Country toEntity(CountryDTO dto);
}