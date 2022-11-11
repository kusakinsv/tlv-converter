package ru.one.converter;

import ru.one.converter.models.Item;
import ru.one.converter.models.TLV;

import java.nio.charset.Charset;
import java.util.List;

public class TlvToItemConverter {

    public Item convertTLVtoItem(List<TLV> tlvList) {
            Item item = new Item();
            for (TLV tlv : tlvList) {
                if (tlv.getTagType() == 11){
                    String itemName = new String(tlv.getValue(), Charset.forName("CP866"));
                    item.setName(itemName);
                }
                if (tlv.getTagType() == 12){
                    int littleEndianPrice = Utils.bytesToIntLE(tlv.getValue());
                    item.setPrice(littleEndianPrice);
                }
                if (tlv.getTagType() == 13){
                    double quantity = Utils.bytesToFVLN(tlv.getValue());
                    item.setQuantity(quantity);
                }
                if (tlv.getTagType() == 14){
                    item.setSum((long)(item.getPrice() * item.getQuantity()));
                }
            }
        return item;
    }



}
