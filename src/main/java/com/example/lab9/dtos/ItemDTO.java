package com.example.lab9.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;

@Data
@Setter
@Getter
public class ItemDTO {
    private Long id;
    private String name;
    private int price;
    private int quantity;
    private Long manufacturerId;
}