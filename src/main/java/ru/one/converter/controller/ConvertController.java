package ru.one.converter.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.one.converter.service.ConvertService;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class ConvertController {
    @Autowired
    private ConvertService convertService;

    @PostMapping(path = "convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<?> multiUploadFileModel(@RequestPart(value = "file", required = true) MultipartFile bytes) {
            try {
                JsonNode jsonString = convertService.convert(bytes.getBytes());
                return ResponseEntity.accepted().body(jsonString);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
}

