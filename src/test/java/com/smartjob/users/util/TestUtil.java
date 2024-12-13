package com.smartjob.users.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class TestUtil {
    private final static ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
    }

    public static <T> T convertJsonToDto(String filePath, TypeReference<T> typeReference) throws IOException {
        File file = new File(filePath);
        return OBJECT_MAPPER.readValue(file, typeReference);
    }

    public static <T> T convertJsonToDto(String filePath, Class<T> valueType) throws IOException {
        File file = new File(filePath);
        return OBJECT_MAPPER.readValue(file, valueType);
    }
}
