package com.example.lab9.mappers;

import com.example.lab9.dtos.ItemDTO;
import com.example.lab9.models.Country;
import com.example.lab9.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ItemMapperTest {

    private ItemMapper itemMapper;

    @BeforeEach
    void setUp() {
        itemMapper = Mappers.getMapper(ItemMapper.class);
    }

    @Test
    void toDto_shouldMapEntityToDto() {
        // Given
        Country country = new Country();
        country.setId(1L);

        Item item = new Item();
        item.setId(1L);
        item.setName("iPhone 16");
        item.setPrice(999);
        item.setQuantity(100);
        item.setManufacturer(country);

        // When
        ItemDTO dto = itemMapper.toDto(item);

        // Then
        assertNotNull(dto);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("iPhone 16");
        assertThat(dto.getPrice()).isEqualTo(999);
        assertThat(dto.getQuantity()).isEqualTo(100);
        assertThat(dto.getManufacturerId()).isEqualTo(1L);
    }

    @Test
    void toEntity_shouldMapDtoToEntity() {
        // Given
        ItemDTO dto = new ItemDTO();
        dto.setId(1L);
        dto.setName("iPhone 16");
        dto.setPrice(999);
        dto.setQuantity(100);
        dto.setManufacturerId(1L);

        // When
        Item item = itemMapper.toEntity(dto);

        // Then
        assertNotNull(item);
        assertThat(item.getId()).isEqualTo(1L);
        assertThat(item.getName()).isEqualTo("iPhone 16");
        assertThat(item.getPrice()).isEqualTo(999);
        assertThat(item.getQuantity()).isEqualTo(100);
        assertNull(item.getManufacturer()); // Ignored as per mapping
    }

    @Test
    void toDto_shouldHandleNullEntity() {
        // When
        ItemDTO dto = itemMapper.toDto(null);

        // Then
        assertNull(dto);
    }

    @Test
    void toEntity_shouldHandleNullDto() {
        // When
        Item item = itemMapper.toEntity(null);

        // Then
        assertNull(item);
    }

    @Test
    void toDto_shouldHandleNullManufacturer() {
        // Given
        Item item = new Item();
        item.setId(1L);
        item.setName("iPhone 16");
        item.setPrice(999);
        item.setQuantity(100);
        item.setManufacturer(null);

        // When
        ItemDTO dto = itemMapper.toDto(item);

        // Then
        assertThat(dto.getManufacturerId()).isNull();
    }

    @Test
    void toDtoList_shouldMapListOfEntitiesToDtos() {
        // Given
        Country country = new Country();
        country.setId(1L);

        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("iPhone 16");
        item1.setPrice(999);
        item1.setQuantity(100);
        item1.setManufacturer(country);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("Huawei P60");
        item2.setPrice(800);
        item2.setQuantity(150);
        item2.setManufacturer(country);

        List<Item> items = Arrays.asList(item1, item2);

        // When
        List<ItemDTO> dtos = items.stream().map(itemMapper::toDto).toList(); // Simulate list mapping

        // Then
        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getId()).isEqualTo(1L);
        assertThat(dtos.get(1).getId()).isEqualTo(2L);
    }
}