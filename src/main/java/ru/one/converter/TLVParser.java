package ru.one.converter;

import ru.one.converter.model.TLV;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TLVParser {
    public static List<TLV> parse(ByteBuffer bb) throws Exception {
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
        } catch (IndexOutOfBoundsException e) {
            throw new Exception("Не читаемая TLV часть: " + bb.toString() + ".", e);
        }
        return TLVList;
    }
}
