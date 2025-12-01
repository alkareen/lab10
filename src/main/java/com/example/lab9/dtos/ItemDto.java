package com.example.lab9.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String name;
    private int price;
    private int quantity;
    private CountryDto manufacturer;
}