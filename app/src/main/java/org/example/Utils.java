package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Utils. */
public final class Utils {
  private Utils() {
    throw new UnsupportedOperationException();
  }

  /**
   * Check if the string is null or empty.
   *
   * @param s String
   * @return Boolean
   */
  public static Boolean isNullOrEmpty(final String s) {
    return s == null || s.isEmpty();
  }

  /**
   * Convert an array to a JSON string.
   *
   * @param arr String[]
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
}
