package org.example.dags.realestate.mlit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/** Unit test for a simple App. */
public class JapaneseYearConverterTest {
  /** Fixture for testing deserialization. */
  private String jpnYear;

  /** Setup. */
  @Before
  public void setUp() {
    jpnYear = "2020年";
  }

  /** Test simple year conversion. */
  @Test
  public void testSimpleYearConversion() {
    JapaneseYearConverter converter = new JapaneseYearConverter();

    assertEquals("2020", converter.convert(jpnYear));
  }
}
