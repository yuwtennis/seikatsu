package org.example.dags.realestate.mlit;

import com.fasterxml.jackson.databind.util.StdConverter;

/** JapaneseYearConverter. */
public class JapaneseYearConverter extends StdConverter<String, String> {
  /**
   * Convert.
   *
   * @param value String
   * @return String
   */
  @Override
  public String convert(String value) {
    return value.replace("年", "");
  }
}
