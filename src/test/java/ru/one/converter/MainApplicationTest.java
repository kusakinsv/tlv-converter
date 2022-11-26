package ru.one.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.one.converter.models.Data;
import ru.one.converter.models.Item;
import ru.one.converter.models.TLV;
import ru.one.converter.utils.TlvParser;
import ru.one.converter.utils.TlvToDataConverter;
import ru.one.converter.utils.TlvToItemConverter;
import ru.one.converter.utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.List;

class MainApplicationTest {
    private static final String PATH = (Paths.get("").toAbsolutePath() + "/src/test/resources/data-1.bin");
    private static byte[] bytes;

    @Test
    public void parseTLV() throws Exception {
        List<TLV> tlvList = TlvParser.parseTlv(ByteBuffer.wrap(bytes));
        Assertions.assertEquals(5, tlvList.size());
        Assertions.assertEquals(1, tlvList.get(0).getTagType());
        Assertions.assertEquals(2, tlvList.get(1).getTagType());
        Assertions.assertEquals(3, tlvList.get(2).getTagType());
        Assertions.assertEquals(4, tlvList.get(3).getTagType());
    }

    @Test
    public void convertTlvToData() throws Exception {
        List<TLV> tlvList = TlvParser.parseTlv(ByteBuffer.wrap(bytes));
        Data data = new TlvToDataConverter().convertTlvToData(tlvList);
        Assertions.assertEquals("2016-01-10T10:30", data.getDateTime());
        Assertions.assertEquals(160004L, data.getOrderNumber().longValue());
        Assertions.assertEquals("ООО Ромашка", data.getCustomerName());
        Assertions.assertEquals(2, data.getItems().size());
    }

    @Test
    public void convertTlvToItem() throws Exception {
        List<TLV> tlvList = TlvParser.parseTlv(ByteBuffer.wrap(bytes));
        List<TLV> itemTLV = TlvParser.parseTlv(ByteBuffer.wrap(tlvList.get(3).getValue()));
        Item item = new TlvToItemConverter().convertTlvToItem(itemTLV);
        Assertions.assertEquals("Дырокол", item.getName());
        Assertions.assertEquals(20000, item.getPrice());
        Assertions.assertEquals(2.0, item.getQuantity());
        Assertions.assertEquals(40000, item.getSum());
    }

    @Test
    public void bytesToIntLE() throws Exception {
        List<TLV> tlvList = TlvParser.parseTlv(ByteBuffer.wrap(bytes));
        long littleEndian = Utils.bytesToIntLE(tlvList.get(1).getValue());
        Assertions.assertEquals(160004, littleEndian);
    }

    @Test
    public void fvlnInBytesToDouble() throws Exception {
        List<TLV> tlvList = TlvParser.parseTlv(ByteBuffer.wrap(bytes));
        List<TLV> itemTLV = TlvParser.parseTlv(ByteBuffer.wrap(tlvList.get(3).getValue()));
        double value = Utils.fvlnInBytesToDouble(itemTLV.get(2).getValue());
        Assertions.assertEquals(2.0, value);
    }

    @BeforeAll
    public static void fillBuffer() throws IOException {
        bytes = new FileInputStream(PATH).readAllBytes();
    }

}
