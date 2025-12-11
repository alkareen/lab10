package com.example.lab9.services;

import com.example.lab9.dtos.ItemDTO;
import com.example.lab9.mappers.ItemMapper;
import com.example.lab9.models.Country;
import com.example.lab9.models.Item;
import com.example.lab9.repositories.CountryRepository;
import com.example.lab9.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    private ItemRepository itemRepository;
    private CountryRepository countryRepository;
    private ItemMapper itemMapper;
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        countryRepository = mock(CountryRepository.class);
        itemMapper = mock(ItemMapper.class);
        itemService = new ItemService();
        ReflectionTestUtils.setField(itemService, "itemRepository", itemRepository);
        ReflectionTestUtils.setField(itemService, "countryRepository", countryRepository);
        ReflectionTestUtils.setField(itemService, "itemMapper", itemMapper);
    }

    @Test
    void getAllItems() {
        Country country = new Country();
        country.setId(1L);

        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("iPhone");
        item1.setPrice(999);
        item1.setQuantity(100);
        item1.setManufacturer(country);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("Huawei");
        item2.setPrice(800);
        item2.setQuantity(150);
        item2.setManufacturer(country);

        List<Item> items = Arrays.asList(item1, item2);

        ItemDTO dto1 = new ItemDTO();
        dto1.setId(1L);
        dto1.setName("iPhone");
        dto1.setPrice(999);
        dto1.setQuantity(100);
        dto1.setManufacturerId(1L);

        ItemDTO dto2 = new ItemDTO();
        dto2.setId(2L);
        dto2.setName("Huawei");
        dto2.setPrice(800);
        dto2.setQuantity(150);
        dto2.setManufacturerId(1L);

        when(itemRepository.findAll()).thenReturn(items);
        when(itemMapper.toDto(item1)).thenReturn(dto1);
        when(itemMapper.toDto(item2)).thenReturn(dto2);

        List<ItemDTO> result = itemService.getAllItems();

        assertThat(result).hasSize(2);
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void getItemById() {
        Long id = 1L;
        Country country = new Country();
        country.setId(1L);

        Item item = new Item();
        item.setId(id);
        item.setName("iPhone");
        item.setPrice(999);
        item.setQuantity(100);
        item.setManufacturer(country);

        ItemDTO dto = new ItemDTO();
        dto.setId(id);
        dto.setName("iPhone");
        dto.setPrice(999);
        dto.setQuantity(100);
        dto.setManufacturerId(1L);

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        when(itemMapper.toDto(item)).thenReturn(dto);

        ItemDTO result = itemService.getItemById(id);

        assertThat(result).isNotNull();
        verify(itemRepository, times(1)).findById(id);
    }

    @Test
    void getItemById_shouldReturnNullWhenNotFound() {
        Long id = 1L;
        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        ItemDTO result = itemService.getItemById(id);

        assertThat(result).isNull();
    }

    @Test
    void createItem() {
        ItemDTO dto = new ItemDTO();
        dto.setName("iPhone");
        dto.setPrice(999);
        dto.setQuantity(100);
        dto.setManufacturerId(1L);

        Item item = new Item();
        item.setName("iPhone");
        item.setPrice(999);
        item.setQuantity(100);

        Country country = new Country();
        country.setId(1L);

        Item saved = new Item();
        saved.setId(1L);
        saved.setName("iPhone");
        saved.setPrice(999);
        saved.setQuantity(100);
        saved.setManufacturer(country);

        ItemDTO savedDto = new ItemDTO();
        savedDto.setId(1L);
        savedDto.setName("iPhone");
        savedDto.setPrice(999);
        savedDto.setQuantity(100);
        savedDto.setManufacturerId(1L);

        when(itemMapper.toEntity(dto)).thenReturn(item);
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
        when(itemRepository.save(item)).thenReturn(saved);
        when(itemMapper.toDto(saved)).thenReturn(savedDto);

        ItemDTO result = itemService.createItem(dto);

        assertThat(result.getId()).isEqualTo(1L);
        verify(itemRepository, times(1)).save(item);
        assertThat(item.getManufacturer()).isEqualTo(country);
    }

    @Test
    void createItem_shouldThrowExceptionWhenManufacturerNotFound() {
        ItemDTO dto = new ItemDTO();
        dto.setManufacturerId(1L);

        Item item = new Item();

        when(itemMapper.toEntity(dto)).thenReturn(item);
        when(countryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> itemService.createItem(dto));
        verify(itemRepository, never()).save(any());
    }

    @Test
    void updateItem() {
        Long id = 1L;
        ItemDTO dto = new ItemDTO();
        dto.setName("Updated iPhone");
        dto.setPrice(1099);
        dto.setQuantity(50);
        dto.setManufacturerId(1L);

        Item existing = new Item();
        existing.setId(id);
        existing.setName("iPhone");
        existing.setPrice(999);
        existing.setQuantity(100);

        Country country = new Country();
        country.setId(1L);

        Item updated = new Item();
        updated.setId(id);
        updated.setName("Updated iPhone");
        updated.setPrice(1099);
        updated.setQuantity(50);
        updated.setManufacturer(country);

        ItemDTO updatedDto = new ItemDTO();
        updatedDto.setId(id);
        updatedDto.setName("Updated iPhone");
        updatedDto.setPrice(1099);
        updatedDto.setQuantity(50);
        updatedDto.setManufacturerId(1L);

        when(itemRepository.findById(id)).thenReturn(Optional.of(existing));
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
        when(itemRepository.save(existing)).thenReturn(updated);
        when(itemMapper.toDto(updated)).thenReturn(updatedDto);

        ItemDTO result = itemService.updateItem(id, dto);

        assertThat(result.getName()).isEqualTo("Updated iPhone");
        verify(itemRepository, times(1)).save(existing);
    }

    @Test
    void updateItem_2() {
        Long id = 1L;
        ItemDTO dto = new ItemDTO();
        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        ItemDTO result = itemService.updateItem(id, dto);

        assertThat(result).isNull();
        verify(itemRepository, never()).save(any());
    }

    @Test
    void updateItem_3() {
        Long id = 1L;
        ItemDTO dto = new ItemDTO();
        dto.setManufacturerId(1L);

        Item existing = new Item();
        existing.setId(id);

        when(itemRepository.findById(id)).thenReturn(Optional.of(existing));
        when(countryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> itemService.updateItem(id, dto));
        verify(itemRepository, never()).save(any());
    }

    @Test
    void deleteItem() {
        Long id = 1L;

        itemService.deleteItem(id);

        verify(itemRepository, times(1)).deleteById(id);
    }
}