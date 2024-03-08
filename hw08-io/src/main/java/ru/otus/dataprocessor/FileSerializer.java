package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class FileSerializer implements Serializer {

    private final String fileName;

    private final ObjectMapper mapper;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
        this.mapper = JsonMapper.builder().build();
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // формирует результирующий json и сохраняет его в файл
        try(var writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(mapper.writeValueAsString(
                    Map.copyOf(data).entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue(Double::compareTo))
                            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> k, LinkedHashMap::new))
            ));
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
