package ru.one.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.one.converter.model.Data;
import ru.one.converter.model.TLV;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
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

            FileInputStream data1file = new FileInputStream(new File(String.valueOf(inputBinFileName)));
            byte[] bytesForBuffer = data1file.readAllBytes();
            data1file.close();
            ByteBuffer buffer = ByteBuffer.wrap(bytesForBuffer);
            List<TLV> result = TLVParser.parse(buffer);
            TlvToDataConverter tlvToDataConverter = new TlvToDataConverter();
            Data data = tlvToDataConverter.convert(result);
            ObjectMapper objectMapper = new ObjectMapper();
            String string = objectMapper.writeValueAsString(data);

            FileOutputStream fileOutputStream = new FileOutputStream(dataPath + "/" + args[1]);
            fileOutputStream.write(string.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            System.out.println("Ошибка");
            e.printStackTrace();
        }
    }

}
