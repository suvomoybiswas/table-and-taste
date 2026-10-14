package com.tableandtaste.backend.controller;

import com.tableandtaste.backend.dto.MenuItemDTO;
import com.tableandtaste.backend.dto.MenuItemRequestDTO;
import com.tableandtaste.backend.entity.MenuItem;
import com.tableandtaste.backend.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/menu")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public List<MenuItemDTO> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(
            @RequestBody @Valid MenuItemRequestDTO menuItemRequest) {

        MenuItem savedMenuItem =
                menuItemService.createMenuItem(menuItemRequest);

        return ResponseEntity.status(201).body(savedMenuItem);
    }

    @PutMapping("/{id}")
    public MenuItemDTO updateMenuItem(
            @PathVariable Long id,
            @RequestBody @Valid MenuItemRequestDTO menuItemRequest) {

        return menuItemService.updateMenuItem(id, menuItemRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(
            @PathVariable Long id) {

        menuItemService.deleteMenuItem(id);

        return ResponseEntity.noContent().build();
    }
}