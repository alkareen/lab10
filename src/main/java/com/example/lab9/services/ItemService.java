package com.example.lab9.services;

import com.example.lab9.dtos.CountryDto;
import com.example.lab9.dtos.ItemDto;
import com.example.lab9.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemDto> findAll() {
        return itemRepository.findAll().stream()
                .map(item -> {
                    ItemDto dto = new ItemDto();
                    dto.setId(item.getId());
                    dto.setName(item.getName());
                    dto.setPrice(item.getPrice());
                    dto.setQuantity(item.getQuantity());

                    if (item.getManufacturer() != null) {
                        CountryDto countryDto = new CountryDto();
                        countryDto.setId(item.getManufacturer().getId());
                        countryDto.setName(item.getManufacturer().getName());
                        countryDto.setCode(item.getManufacturer().getCode());
                        dto.setManufacturer(countryDto);
                    }
                    return dto;
                })
                .toList();
    }
}