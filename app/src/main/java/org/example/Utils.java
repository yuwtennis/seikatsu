package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;

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

    /**
     *
     * @param jsonStr
     * @param schema
     * @return
     */
    public static Object asJson(String jsonStr, Class<?> schema) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(jsonStr, schema);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param jsonStr
     * @return
     */
    public static FeatureCollection asFeatureCollection(String jsonStr) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(jsonStr, FeatureCollection.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param str
     * @return
     */
    public static String validateStr(String str) {
        return str.isEmpty() ? Magics.IS_EMPTY.value : str;
    }

    /**
     *
     * @param val
     * @return
     */
    public static int asInt(String val) {
        int result = 0 ;

        try {
            result = Integer.parseInt(val);
        } catch (NumberFormatException e) {
            result = Integer.parseInt(Magics.IS_UNEXPECTED_NUMERIC_VAL.value);
        }

        return result;
    }

    /**
     *
     * @param val
     * @return
     */
    public static float asFloat(String val) {
        float result = 0 ;

        try {
            result = Float.parseFloat(val);
        } catch (NumberFormatException e) {
            result = Float.parseFloat(Magics.IS_UNEXPECTED_NUMERIC_VAL.value);
        }

        return result;
    }

    /**
     *
     * @param val
     * @return
     */
    public static String from4digitStr(String val) {
        return String.format("%s-%s-%s", val.substring(0, 4), val.substring(4, 6), val.substring(6, 8));
    }
}
