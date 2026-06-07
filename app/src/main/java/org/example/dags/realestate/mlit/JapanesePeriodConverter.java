package org.example.dags.realestate.mlit;

import com.fasterxml.jackson.databind.util.StdConverter;

/**
 * Converts the Japanese fiscal period to the format YYYYMM.
 */
public class JapanesePeriodConverter extends StdConverter<String, String> {

  /**
   * Convert.
   *
   * @param value String
   * @return String
   */
  @Override
  public String convert(String value) {
    if (value == null || value.isBlank()) {
      return null;
    }

    int year = Integer.parseInt(value.replaceAll("年.*", ""));
    int quarter = Integer.parseInt(value.replaceAll(".*第(\\d+)四半期", "$1"));
    int month = (quarter - 1) * 3 + 1;

    return String.format("%d%02d", year, month);
  }
}
