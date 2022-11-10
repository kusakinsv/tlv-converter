package ru.one.converter.model;

import java.math.BigDecimal;

public class Item {
    String name;
    BigDecimal price;
    Integer quantity;
    BigDecimal sum;

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", sum=" + sum +
                '}';
    }
}
