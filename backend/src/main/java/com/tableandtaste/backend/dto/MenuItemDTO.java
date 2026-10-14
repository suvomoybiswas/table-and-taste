package com.tableandtaste.backend.dto;

import com.tableandtaste.backend.entity.MenuItem;

import java.math.BigDecimal;

public class MenuItemDTO {

    private Long id;

    private String name;
    private BigDecimal price;

    private String description;

    private String category;

    public MenuItemDTO(
            Long id,
            String name,
            BigDecimal price,
            String description,
            String category) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    public static MenuItemDTO fromEntity(MenuItem menuItem) {

        return new MenuItemDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getPrice(),
                menuItem.getDescription(),
                menuItem.getCategory()
        );
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}