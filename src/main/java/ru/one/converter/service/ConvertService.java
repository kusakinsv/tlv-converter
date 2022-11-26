package ru.one.converter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import org.springframework.stereotype.Component;
import ru.one.converter.utils.TlvParser;
import ru.one.converter.utils.TlvToDataConverter;
import ru.one.converter.models.Data;
import ru.one.converter.models.TLV;

import java.nio.ByteBuffer;
import java.util.List;
@Component
public class ConvertService {

    public JsonNode convert(byte[] reciviedBytes) {
        JsonNode jsonNode = Json.mapper().nullNode();
        try {
            ByteBuffer buffer = ByteBuffer.wrap(reciviedBytes);
            List<TLV> result = TlvParser.parseTlv(buffer);
            TlvToDataConverter tlvToDataConverter = new TlvToDataConverter();
            Data data = tlvToDataConverter.convertTlvToData(result);
            String jsonInString = new ObjectMapper().writeValueAsString(data);
            jsonNode = Json.mapper().readTree(jsonInString);
        } catch (Exception e) {
            System.out.println("Ошибка");
            e.printStackTrace();
        }
        return jsonNode;
    }

}
