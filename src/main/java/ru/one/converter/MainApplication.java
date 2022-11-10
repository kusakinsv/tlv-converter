package ru.one.converter;

import ru.one.converter.model.Converter;
import ru.one.converter.model.TLV;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class MainApplication {
    public static void main(String[] args) {
        try {
            Path dataPath = Paths.get("").toAbsolutePath();
            String path = dataPath + "\\";
            String inputBinFileName = path + args[0];
            String outputjsonFileName = path + args[1];
            System.out.println(inputBinFileName);
            System.out.println(outputjsonFileName);

            FileInputStream data1file = new FileInputStream(new File(String.valueOf(inputBinFileName)));
            byte[] bytesForBuffer = data1file.readAllBytes();

            ByteBuffer buffer = ByteBuffer.wrap(bytesForBuffer);
            List<TLV> result = parse(buffer);
            System.out.println(result.toString());
            Converter converter = new Converter();
            converter.convert(result);




        } catch (Exception e) {
            System.out.println("Ошибка");
            e.printStackTrace();
        }
    }


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
