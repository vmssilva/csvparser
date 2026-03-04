package com.github.vmssilva.csvparser.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;

import com.github.vmssilva.csvparser.exception.CsvParseException;

public class CsvRowTest {

  CsvRow row;

  @Test
  @DisplayName("Should return total of elements correctly")
  void testTotalOfElements() {
    this.row = new CsvRow(List.of("1", "Galaxy X1 Smartphone", "Electronics", "1499.90"));
    assertEquals(4, row.size());
  }

  @Test
  @DisplayName("Should return all values in the row")
  void testGetAllValuesInRow() {
    this.row = new CsvRow(List.of("1", "Galaxy X1 Smartphone", "Electronics", "1499.90"));

    assertTrue(row.values().size() == 4);
    assertEquals(List.of("1", "Galaxy X1 Smartphone", "Electronics", "1499.90"), row.values());
    assertNotEquals(List.of("5", "Galaxy X1 Smartphone", "Electronics", "1499.90"), row.values());
  }

  @Test
  @DisplayName("Should return all keys in the row")
  void testGetAllKeys() {
    this.row = new CsvRow(List.of("1", "Galaxy X1 Smartphone", "Electronics", "1499.90"));
    assertEquals(List.of(0, 1, 2, 3), row.keys());
  }

  @Test
  @DisplayName("Should throw an CSVParseException for invalid index")
  void testGetValueWithAnInvalidIndex() {
    this.row = new CsvRow(List.of("1", "Galaxy X1 Smartphone", "Electronics", "1499.90"));
    assertThrows(CsvParseException.class, () -> row.getString(5));
    assertThrows(CsvParseException.class, () -> row.getString(-1));
  }

  @Test
  @DisplayName("Should return the a valid char")
  void testGetChar() {
    this.row = new CsvRow(List.of("Y"));
    assertEquals(Character.class, row.getChar(0).getClass());
    assertEquals('Y', row.getChar(0));
  }

  @Test
  @DisplayName("Should throw an CSVParseException for invalid char")
  void testGetAnInvalidChar() {
    this.row = new CsvRow(List.of("char"));
    assertThrows(CsvParseException.class, () -> row.getChar(0));
  }

  @Test
  @DisplayName("Should return correct value as String")
  void testGetString() {
    this.row = new CsvRow(List.of("Smartphone", "Electronics"));
    assertEquals("Electronics", row.getString(1));
  }

  @Test
  @DisplayName("Should return the correct value as Int")
  void testGetInt() {
    this.row = new CsvRow(List.of("35"));
    assertEquals(Integer.class, row.getInt(0).getClass());
    assertEquals(35, row.getInt(0));
  }

  @Test
  @DisplayName("Should return the correct value as Float")
  void testGetFloat() {
    this.row = new CsvRow(List.of("1.73"));
    assertEquals(Float.class, row.getFloat(0).getClass());
    assertEquals(1.73F, row.getFloat(0), 0.0001F);
  }

  @Test
  @DisplayName("Should return the correct value as Double")
  void testGetDouble() {
    this.row = new CsvRow(List.of("1.73"));
    assertEquals(Double.class, row.getDouble(0).getClass());
    assertEquals(1.73D, row.getDouble(0), 0.0001D);
  }

  @Test
  @DisplayName("Should return the correct value as Long")
  void testGetLong() {
    this.row = new CsvRow(List.of("1"));
    assertEquals(Long.class, row.getLong(0).getClass());
    assertEquals(1L, row.getLong(0));
  }

  @ParameterizedTest
  @ValueSource(strings = { "true", "yes", "y", "1" })
  @DisplayName("Should return the correct value as Boolean")
  void testGetBooleanTrue(String input) {
    this.row = new CsvRow(List.of(input));
    assertTrue(row.getBoolean(0));
    assertEquals(Boolean.class, row.getBoolean(0).getClass());
  }

  @ParameterizedTest
  @ValueSource(strings = { "", " ", "n", "false" })
  @DisplayName("Should return false for unknown booleans")
  void testGetBooleanFalse(String input) {
    this.row = new CsvRow(List.of(input));
    assertFalse(row.getBoolean(0));
    assertEquals(Boolean.class, row.getBoolean(0).getClass());
  }

}
