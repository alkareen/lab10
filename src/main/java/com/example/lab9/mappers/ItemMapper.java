package com.example.lab9.mappers;


import com.example.lab9.dtos.CountryDto;
import com.example.lab9.dtos.ItemDto;
import com.example.lab9.models.Item;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring", uses = CountryMapper.class)
public interface ItemMapper {
    ItemDto toDto(Item item);
    Item toEntity(ItemDto dto);
    @ObjectFactory
    default CountryDto createCountryDto() {
        return new CountryDto();
    }
}