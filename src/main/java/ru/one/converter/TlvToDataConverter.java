package ru.one.converter;

import ru.one.converter.models.Data;
import ru.one.converter.models.Item;
import ru.one.converter.models.TLV;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TlvToDataConverter {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss").withZone(ZoneId.of("UTC"));

    public Data convert(List<TLV> tlvList) throws Exception {
        Data data = new Data();
        List<Item> result = new ArrayList<>();
        for (TLV tlv : tlvList) {
            if (tlv.getTagType() == 1){
                long unixSeconds = Utils.bytesToIntLE(tlv.getValue());
                LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixSeconds), ZoneId.of("UTC"));
                data.setDateTime(localDateTime.toString());
            }
            if (tlv.getTagType() == 2){
                long littleEndian = (long)Utils.bytesToIntLE(tlv.getValue());
                data.setOrderNumber(littleEndian);
            }
            if (tlv.getTagType() == 3){
                String customerName = new String(tlv.getValue(), Charset.forName("CP866"));
                data.setCustomerName(customerName);
            }
            if (tlv.getTagType() == 4){
            List<TLV> tlvItemList = TLVParser.parse(ByteBuffer.wrap(tlv.getValue()));
            TlvToItemConverter tlvToItemConverter = new TlvToItemConverter();
            result.add(tlvToItemConverter.convertTLVtoItem(tlvItemList));
            }
        }
        data.setItems(result);
        return data;
    }


}
