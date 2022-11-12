package ru.one.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.one.converter.models.Data;
import ru.one.converter.models.TLV;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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

            FileInputStream data1file = new FileInputStream((inputBinFileName));
            byte[] bytesForBuffer = data1file.readAllBytes();
            data1file.close();

            ByteBuffer buffer = ByteBuffer.wrap(bytesForBuffer);
            List<TLV> result = TLVParser.parse(buffer);
            TlvToDataConverter tlvToDataConverter = new TlvToDataConverter();
            Data data = tlvToDataConverter.convertTlvToData(result);
            byte[] bytes = new ObjectMapper().writeValueAsBytes(data);

            FileOutputStream fileOutputStream = new FileOutputStream(outputjsonFileName);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            System.out.println("Ошибка");
            e.printStackTrace();
        }
    }

}
