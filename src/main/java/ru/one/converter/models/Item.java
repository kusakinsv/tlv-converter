package ru.one.converter.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    String name;
    long price;
    double quantity;
    long sum;

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
