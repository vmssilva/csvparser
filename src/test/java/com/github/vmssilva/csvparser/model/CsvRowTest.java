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
    CsvRow row = new CsvRow(List.of("1", "Galaxy X1 Smartphone", "Electronics", "1499.90"));
    assertEquals(4, row.size());
  }

  @Test
  @DisplayName("Should return all values in the row")
  void testGetAllValuesInRow() {
    row = new CsvRow(List.of("1", "Galaxy X1 Smartphone", "Electronics", "1499.90"));

    assertTrue(row.values().size() == 4);
    assertEquals(List.of("1", "Galaxy X1 Smartphone", "Electronics", "1499.90"), row.values());
    assertNotEquals(List.of("5", "Galaxy X1 Smartphone", "Electronics", "1499.90"), row.values());
  }

  @Test
  @DisplayName("Should throw an CSVParseException if an invalid index has been passed as argument")
  void testGetValueWithAnInvalidIndex() {
    row = new CsvRow(List.of("1", "Galaxy X1 Smartphone", "Electronics", "1499.90"));
    assertThrows(CsvParseException.class, () -> row.getString(5));
    assertThrows(CsvParseException.class, () -> row.getString(-1));
  }

  @Test
  @DisplayName("Should return the correct value as Character")
  void testGetChar() {
    row = new CsvRow(List.of("Y"));
    assertEquals(Character.class, row.getChar(0).getClass());
    assertEquals('Y', row.getChar(0));
  }

  @Test
  @DisplayName("Should return correct value as String")
  void testGetString() {
    row = new CsvRow(List.of("Smartphone", "Electronics"));
    assertEquals("Electronics", row.getString(1));
  }

  @Test
  @DisplayName("Should return the correct value as Int")
  void testGetInt() {
    row = new CsvRow(List.of("35"));
    assertEquals(Integer.class, row.getInt(0).getClass());
    assertEquals(35, row.getInt(0));
  }

  @Test
  @DisplayName("Should return the correct value as Float")
  void testGetFLoat() {
    row = new CsvRow(List.of("1.73"));
    assertEquals(Float.class, row.getFloat(0).getClass());
    assertEquals(1.73F, row.getFloat(0));
  }

  @Test
  @DisplayName("Should return the correct value as Double")
  void testGetDouble() {
    row = new CsvRow(List.of("1.73"));
    assertEquals(Double.class, row.getDouble(0).getClass());
    assertEquals(1.73D, row.getDouble(0));
  }

  @Test
  @DisplayName("Should return the correct value as Long")
  void testGetLong() {
    row = new CsvRow(List.of("1"));
    assertEquals(Long.class, row.getLong(0).getClass());
    assertEquals(1L, row.getDouble(0));
  }

  @Test
  void testGetBoolean() {
    row = new CsvRow(List.of("true"));
    assertEquals(Boolean.class, row.getBoolean(0).getClass());
  }

  @ParameterizedTest
  @ValueSource(strings = { "\"false\"", "\"true\"", "not", "-1", "-0", "11", "00", "", " " })
  @DisplayName("Should throw an CSVParseException cause found an invalid boolean value")
  void testGetBooleanWithAnInvalidValue(String input) {
    row = new CsvRow(List.of(input));

    assertThrows(CsvParseException.class, () -> row.getBoolean(0));
  }

  @ParameterizedTest
  @ValueSource(strings = { "true", "yes", "y", "1" })
  @DisplayName("Should return the correct value as Boolean")
  void testGetBooleanTrue(String input) {
    row = new CsvRow(List.of(input));
    assertTrue(row.getBoolean(0));
  }

  @ParameterizedTest
  @ValueSource(strings = { "false", "no", "n", "0" })
  void testGetBooleanFalse(String input) {
    row = new CsvRow(List.of(input));
    assertFalse(row.getBoolean(0));
  }
}
