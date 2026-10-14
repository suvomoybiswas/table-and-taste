package com.tableandtaste.backend.dto;

public class AddOrderItemRequest {
    private Long menuItemId;
    private Integer quantity;

    public AddOrderItemRequest (Long menuItemId, Integer quantity){

        this.menuItemId = menuItemId;
        this.quantity = quantity;
    }
    public AddOrderItemRequest() {
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
