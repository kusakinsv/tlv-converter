package ru.one.converter.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class TLV {
    short tagType;
    byte[] value;

    @Override
    public String toString() {
        return "\nTLV {\n" +
                "tagType=" + tagType +
                "\nvalue=" + Arrays.toString(value) +
                "\n}";
    }
}
