package com.ucelebi.automobile.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Fail.fail;

/**
 * User: ucelebi
 * Date: 22.06.2023
 * Time: 07:24
 */
public class JsonMapperUtil {

    public static Object jsonToObject(String json, Class<?> type) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return new ObjectMapper().readValue(json, type);
        } catch (JsonProcessingException e) {
            fail("Failed to convert json to object");
            return null;
        }
    }

    public static String objectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("Failed to convert object to json");
            return null;
        }
    }
}
