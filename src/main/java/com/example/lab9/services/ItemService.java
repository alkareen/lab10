package com.example.lab9.services;

import com.example.lab9.dtos.ItemDTO;
import com.example.lab9.mappers.ItemMapper;
import com.example.lab9.models.Country;
import com.example.lab9.models.Item;
import com.example.lab9.repositories.CountryRepository;
import com.example.lab9.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ItemMapper itemMapper;

    public List<ItemDTO> getAllItems() {
        return itemRepository.findAll().stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public ItemDTO getItemById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.map(itemMapper::toDto).orElse(null);
    }

    public ItemDTO createItem(ItemDTO itemDTO) {
        Item item = itemMapper.toEntity(itemDTO);
        Optional<Country> manufacturer = countryRepository.findById(itemDTO.getManufacturerId());
        if (manufacturer.isPresent()) {
            item.setManufacturer(manufacturer.get());
            Item saved = itemRepository.save(item);
            return itemMapper.toDto(saved);
        }
        throw new RuntimeException("Manufacturer not found");
    }

    public ItemDTO updateItem(Long id, ItemDTO itemDTO) {
        Optional<Item> existing = itemRepository.findById(id);
        if (existing.isPresent()) {
            Item item = existing.get();
            item.setName(itemDTO.getName());
            item.setPrice(itemDTO.getPrice());
            item.setQuantity(itemDTO.getQuantity());
            Optional<Country> manufacturer = countryRepository.findById(itemDTO.getManufacturerId());
            if (manufacturer.isPresent()) {
                item.setManufacturer(manufacturer.get());
                Item updated = itemRepository.save(item);
                return itemMapper.toDto(updated);
            }
            throw new RuntimeException("Manufacturer not found");
        }
        return null;
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}