package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.FeatureCollection;

public final class Utils {
    private Utils() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param s
     * @return Boolean
     */
    public static Boolean isNullOrEmpty(final String s) {
        return s == null || s.isEmpty();
    }

    /***
     *
     * @param arr
     * @return String
     */
    public static String asJsonStr(final String[] arr) {
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
     * @return Object
     */
    public static Object asJson(final String jsonStr, final Class<?> schema) {
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
     * @return FeatureCollection
     */
    public static FeatureCollection asFeatureCollection(final String jsonStr) {
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
     * @return String
     */
    public static String validateStr(final String str) {
        return str.isEmpty() ? Magics.IS_EMPTY.getValue() : str;
    }

    /**
     *
     * @param val
     * @return int
     */
    public static int asInt(final String val) {
        int result = 0;

        try {
            result = Integer.parseInt(val);
        } catch (NumberFormatException e) {
            result = Integer.parseInt(
                    Magics.IS_UNEXPECTED_NUMERIC_VAL.getValue());
        }

        return result;
    }

    /**
     *
     * @param val
     * @return float
     */
    public static float asFloat(final String val) {
        float result = 0;

        try {
            result = Float.parseFloat(val);
        } catch (NumberFormatException e) {
            result =
                    Float.parseFloat(
                            Magics.IS_UNEXPECTED_NUMERIC_VAL.getValue());
        }

        return result;
    }

    /**
     *
     * @param val
     * @return String
     */
    public static String from4digitStr(final String val) {
        return String.format(
                "%s-%s-%s",
                val.substring(asInt(Magics.NUM_0.getValue()),
                        asInt(Magics.NUM_4.getValue())),
                val.substring(asInt(Magics.NUM_4.getValue()),
                        asInt(Magics.NUM_6.getValue())),
                val.substring(asInt(Magics.NUM_6.getValue()),
                        asInt(Magics.NUM_8.getValue())));
    }
}
