package com.smartapps.daraja.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public class Converter {
    public static Map<String, Object> convertJsonToMap(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json,
                    new TypeReference<>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
