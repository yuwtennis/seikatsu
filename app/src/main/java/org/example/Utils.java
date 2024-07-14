package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class Utils {
    public static Boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /***
     *
     * @param arr
     * @return
     */
    public static String asJsonStr(String[] arr) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(arr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
