package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    private final ObjectMapper mapper;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
        this.mapper = JsonMapper.builder().build();
    }

    @Override
    public List<Measurement> load() {
        // читает файл, парсит и возвращает результат
        try {
            return Arrays
                    .stream(mapper.readValue(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName), Measurement[].class))
                    .toList();
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
