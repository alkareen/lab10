package com.example.lab9.mappers;


import com.example.lab9.dtos.ItemDTO;
import com.example.lab9.models.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(target = "manufacturerId", expression = "java(item.getManufacturer() != null ? item.getManufacturer().getId() : null)")
    ItemDTO toDto(Item item);

    @Mapping(target = "manufacturer", ignore = true)
    Item toEntity(ItemDTO dto);
}