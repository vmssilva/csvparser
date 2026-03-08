package com.github.vmssilva.csvparser.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;

import com.github.vmssilva.csvparser.exception.CsvParseException;

public class CsvRowTest {

  @Test
  @DisplayName("Should return correct number of elements")
  void testTotalOfElements() {
    CsvRow row = new CsvRow(List.of("1", "Galaxy X1 Smartphone", "Electronics", "1499.90"));
    assertEquals(4, row.size());
  }

  @Test
  @DisplayName("Should return all values in the row")
  void testGetAllValuesInRow() {
    CsvRow row = new CsvRow(List.of("1", "Galaxy X1 Smartphone", "Electronics", "1499.90"));

    assertEquals(4, row.values().size());
    assertEquals(List.of("1", "Galaxy X1 Smartphone", "Electronics", "1499.90"), row.values());
    assertNotEquals(List.of("5", "Galaxy X1 Smartphone", "Electronics", "1499.90"), row.values());
  }

  @Test
  @DisplayName("Should return all keys in the row")
  void testGetAllKeys() {
    CsvRow row = new CsvRow(List.of("1", "Galaxy X1 Smartphone", "Electronics", "1499.90"));
    assertEquals(List.of(0, 1, 2, 3), row.keys());
  }

  @Test
  @DisplayName("Should throw a CSVParseException for invalid index")
  void testGetValueWithAnInvalidIndex() {
    CsvRow row = new CsvRow(List.of("1", "Galaxy X1 Smartphone", "Electronics", "1499.90"));
    assertThrows(CsvParseException.class, () -> row.getString(5));
    assertThrows(CsvParseException.class, () -> row.getString(-1));
  }

  @Test
  @DisplayName("Should return the correct char")
  void testGetChar() {
    CsvRow row = new CsvRow(List.of("Y"));
    assertEquals('Y', row.getChar(0));
  }

  @Test
  @DisplayName("Should throw CsvParseException for invalid char")
  void testGetAnInvalidChar() {
    CsvRow row = new CsvRow(List.of("char"));
    assertThrows(CsvParseException.class, () -> row.getChar(0));
  }

  @Test
  @DisplayName("Should return correct value as string")
  void testGetString() {
    CsvRow row = new CsvRow(List.of("Smartphone", "Electronics"));
    assertEquals("Electronics", row.getString(1));
  }

  @Test
  @DisplayName("Should return the correct value as int")
  void testGetInt() {
    CsvRow row = new CsvRow(List.of("35"));
    assertEquals(35, row.getInt(0));
  }

  @Test
  @DisplayName("Should return the correct value as float")
  void testGetFloat() {
    CsvRow row = new CsvRow(List.of("1.73"));
    assertEquals(1.73F, row.getFloat(0), 0.0001F);
  }

  @Test
  @DisplayName("Should return the correct value as double")
  void testGetDouble() {
    CsvRow row = new CsvRow(List.of("1.73"));
    assertEquals(1.73D, row.getDouble(0), 0.0001D);
  }

  @Test
  @DisplayName("Should return the correct value as long")
  void testGetLong() {
    CsvRow row = new CsvRow(List.of("1"));
    assertEquals(1L, row.getLong(0));
  }

  @ParameterizedTest
  @ValueSource(strings = { "true", "yes", "y", "1" })
  @DisplayName("Should return the correct value as boolean")
  void testGetBooleanTrue(String input) {
    CsvRow row = new CsvRow(List.of(input));
    assertTrue(row.getBoolean(0));
  }

  @ParameterizedTest
  @ValueSource(strings = { "false", "no", "n", "0" })
  @DisplayName("Should return the correct value as boolean")
  void testGetBooleanFalse(String input) {
    CsvRow row = new CsvRow(List.of(input));
    assertFalse(row.getBoolean(0));
  }

  @ParameterizedTest
  @ValueSource(strings = { "", " ", "banana" })
  @DisplayName("Should throws CsvParseException for unrecongnized boolean values")
  void shouldThrowExceptionForInvalidBoolean(String input) {
    CsvRow row = new CsvRow(List.of(input));
    assertThrows(CsvParseException.class, () -> row.getBoolean(0));
  }

  @Test
  void shouldThrowExceptionForInvalidInteger() {
    CsvRow row = new CsvRow(List.of("abc"));
    assertThrows(NumberFormatException.class, () -> row.getInt(0));
  }

  @Test
  void shouldThrowExceptionForInvalidFloat() {
    CsvRow row = new CsvRow(List.of("abc"));
    assertThrows(NumberFormatException.class, () -> row.getFloat(0));
  }

  @Test
  void shouldThrowExceptionForInvalidDouble() {
    CsvRow row = new CsvRow(List.of("abc"));
    assertThrows(NumberFormatException.class, () -> row.getDouble(0));
  }

  @Test
  void shouldThrowExceptionForInvalidLong() {
    CsvRow row = new CsvRow(List.of("abc"));
    assertThrows(NumberFormatException.class, () -> row.getLong(0));
  }
}
