package ru.one.converter.utils;

import ru.one.converter.models.TLV;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TlvParser {
    public static List<TLV> parseTlv(ByteBuffer bb) throws Exception {
        List<TLV> TLVList = new ArrayList<>();
        try {
            while (bb.remaining() > 0) {
                byte tag = bb.get();
                bb.get();
                if(tag == 0x00)
                    continue;
                int length = bb.get();
                bb.get();
                byte[] value = new byte[length];
                bb.get(value, 0, length);
                TLV TLV = new TLV();
                TLV.setTagType(tag);
                TLV.setValue(value);
                TLVList.add(TLV);
            }
        } catch (Exception e) {
            throw new Exception("Не читаемая TLV часть: " + bb.toString() + ".", e);
        }
        return TLVList;
    }
}
