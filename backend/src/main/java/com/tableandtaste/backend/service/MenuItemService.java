package com.tableandtaste.backend.service;

import com.tableandtaste.backend.MenuItemNotFoundException;
import com.tableandtaste.backend.dto.MenuItemDTO;
import com.tableandtaste.backend.entity.MenuItem;
import com.tableandtaste.backend.repository.MenuItemRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import com.tableandtaste.backend.dto.MenuItemRequestDTO;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItemDTO> getAllMenuItems() {

        return menuItemRepository.findAll()
                .stream()
                .map(MenuItemDTO::fromEntity)
                .toList();
    }

    public MenuItem createMenuItem(MenuItemRequestDTO menuItemRequest) {

        MenuItem menuItem = new MenuItem();

        menuItem.setName(menuItemRequest.getName());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setCategory(menuItemRequest.getCategory());

        return menuItemRepository.save(menuItem);
    }

    public MenuItemDTO updateMenuItem(Long id, MenuItemRequestDTO menuItemRequest) {

        MenuItem existingMenuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));

        existingMenuItem.setName(menuItemRequest.getName());
        existingMenuItem.setPrice(menuItemRequest.getPrice());
        existingMenuItem.setDescription(menuItemRequest.getDescription());
        existingMenuItem.setCategory(menuItemRequest.getCategory());

        MenuItem savedMenuItem = menuItemRepository.save(existingMenuItem);

        return MenuItemDTO.fromEntity(savedMenuItem);
    }

    public void deleteMenuItem(Long id) {

        MenuItem existingMenuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));

        menuItemRepository.delete(existingMenuItem);
    }
}