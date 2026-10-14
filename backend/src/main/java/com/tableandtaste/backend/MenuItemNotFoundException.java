package com.tableandtaste.backend;

public class MenuItemNotFoundException extends RuntimeException {

    public MenuItemNotFoundException(String message) {
        super(message);
    }
    public MenuItemNotFoundException(Long menuItemId) {
        super("Menu item " + menuItemId + " was not found");
    }
}
