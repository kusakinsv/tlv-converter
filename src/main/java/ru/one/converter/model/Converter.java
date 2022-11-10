package ru.one.converter.model;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Converter {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss").withZone(ZoneId.of("UTC"));

    public void convert(List<TLV> tlvList){
        Data data = new Data();
        for (TLV TLV : tlvList) {
            if (TLV.getTagType() == 1){
                long unixSeconds = bytesToIntLE(TLV.getValue(), 0);
                LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixSeconds), ZoneId.of("UTC"));
                data.setDateTime(localDateTime.toString());

            }
            if (TLV.getTagType() == 2){
                long littleEndian = (long)bytesToIntLE(TLV.getValue(), 0);
                data.setOrderNumber(littleEndian);

            }
            if (TLV.getTagType() == 3){
                String customerName = new String(TLV.getValue(), Charset.forName("CP866"));
                data.setCustomerName(customerName);
            }

        }
        System.out.println(data);

    }


    private static int bytesToIntLE(byte[] bytes, int offset) {
        int i = offset + bytes.length;
        int value = bytes[--i];
        while (--i >= offset) {
            value <<= 8;
            value |= bytes[i] & 0xFF;
        }
        return value;
    }
}
