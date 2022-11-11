package ru.one.converter.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Data {
    private String dateTime; //"2016-01-10T10:30:00";
    private Long orderNumber; // 160004L;
    private String customerName;  //"ООО Ромашка";
    private List<Item> items;

    @Override
    public String toString() {
        return "Data{" +
                "dateTime='" + dateTime + '\'' +
                ", orderNumber=" + orderNumber +
                ", customerName='" + customerName + '\'' +
                ", items=" + items +
                '}';
    }
}
