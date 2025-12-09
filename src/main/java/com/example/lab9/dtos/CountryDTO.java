package com.example.lab9.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;

@Data
@Getter
@Setter
public class CountryDTO {
    private Long id;
    private String name;
    private String code;
}