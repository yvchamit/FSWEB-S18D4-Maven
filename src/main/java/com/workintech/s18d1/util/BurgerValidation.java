package com.workintech.s18d1.util;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.exceptions.BurgerException;
import org.springframework.http.HttpStatus;

public class BurgerValidation {

    public static void checkName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new BurgerException("Name cannot be null or empty!", HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkPrice(Integer price) {
        if (price == null || price < 0) {
            throw new BurgerException("Price must be greater than or equal to 0!", HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkBreadType(String breadType) {
        try {
            BreadType.valueOf(breadType.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new BurgerException("Invalid bread type: " + breadType, HttpStatus.BAD_REQUEST);
        }
    }
}
