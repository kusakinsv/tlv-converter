package ru.one.converter;

import ru.one.converter.model.Data;
import ru.one.converter.model.TlvToDataConverter;
import ru.one.converter.model.TLV;
import ru.one.converter.model.TLVParser;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            List<TLV> result = TLVParser.parse(buffer);
            TlvToDataConverter tlvToDataConverter = new TlvToDataConverter();
            Data data = tlvToDataConverter.convert(result);
            System.out.println(data);

        } catch (Exception e) {
            System.out.println("Ошибка");
            e.printStackTrace();
        }
    }

}
