package com.github.vmssilva.csvparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.vmssilva.csvparser.config.CsvConfig;
import com.github.vmssilva.csvparser.exception.CsvParseException;
import com.github.vmssilva.csvparser.model.CsvDocument;

public class CsvParserTest {

  @Test
  @DisplayName("Should be able to parse with empty line")
  void shouldHandleEmptyCsv() {
    CsvDocument doc = parse("");

    assertEquals(0, doc.rows().size());
    assertEquals(Optional.empty(), doc.getHeader());
  }

  @Test
  @DisplayName("Should be able to parse with only header")
  void shouldHandleCsvWithOnlyHeader() {
    CsvConfig config = new CsvConfig();
    config.setHasHeader(true);

    CsvDocument doc = parse("Id,Name,Salary", config);

    assertEquals(0, doc.rows().size());
    assertEquals(List.of("Id", "Name", "Salary"), doc.getHeader().get().values());
  }

  @Test
  @DisplayName("Should be able to parse a csv with single line")
  void shouldBeParseWithSingleLine() {
    var csv = "Id,Name,Position,Salary";
    CsvDocument doc = parse(csv);
    assertEquals(List.of("Id", "Name", "Position", "Salary"), doc.rows().get(0).values());

  }

  @Test
  @DisplayName("Should be able to parse a csv with multiple lines")
  void shouldBeParseWithMultiplesLines() {
    var csv = """
        Id,Name,Position,Salary
        1,John Doe,Java Developer,5000.00""";

    CsvDocument doc = parse(csv);

    assertEquals(4, doc.rows().get(0).values().size());
    assertEquals(List.of("Id", "Name", "Position", "Salary"),
        doc.rows().get(0).values());
    assertEquals(List.of("1", "John Doe", "Java Developer", "5000.00"),
        doc.rows().get(1).values());
  }

  @Test
  @DisplayName("Should be able to parse a csv with quotes at the start")
  void shouldBeParseWithQuotesAtTheStart() {

    var csv = "\"Id,Name\",Category";

    CsvDocument doc = parse(csv);

    assertEquals(List.of("Id,Name", "Category"),
        doc.rows().get(0).values());
  }

  @Test
  @DisplayName("Should be able to parse a csv with quotes at the middle")
  void shouldBeParseWithQuotesAtTheMiddle() {

    var expected = List.of("Id", "Name,Category", "Price");
    CsvDocument doc = parse("Id,\"Name,Category\",Price");

    assertEquals(expected, doc.rows().get(0).values());
  }

  @Test
  @DisplayName("Should be able to parse a csv with quotes at the end")
  void shoulBeParseWithQuotesAtTheEnd() {
    var expected = List.of("Id", "Name", "Category,Price");
    CsvDocument doc = parse("Id,Name,\"Category,Price\"");
    assertEquals(expected, doc.rows().get(0).values());
  }

  @Test
  @DisplayName("Should be able to parse a csv with multiple quotes")
  void shouldBeParseWithQuotesInMultipleLines() {
    var expected = List.of(
        List.of("Id", "Name", "Category", "Price"),
        List.of("1", "Smartphone, Samsung Galaxy, S22", "Electronics", "399.90"));

    CsvDocument doc = parse("Id,Name,Category,Price\n1,\"Smartphone, Samsung\" \"Galaxy, S22\",Electronics,399.90");
    assertEquals(expected.get(1), doc.rows().get(1).values());
  }

  @Test
  @DisplayName("Should be able to parse csv with escapes characters")
  void shouldBeParseWithEscapesCharacters() {

    var csv = "Id,Name \\\"Description\\\",Price";
    var expected = List.of("Id", "Name \"Description\"", "Price");
    CsvDocument doc = parse(csv);

    assertEquals(expected, doc.rows().get(0).values());

  }

  @Test
  @DisplayName("Should be able to parse csv with multiple escape characters")
  void shouldBeParseWithMultiplesEscapesCharacters() {

    var csv = "1,Smartphone \\\\Samsung \\\"refurbished\\\",Electronics,50.00";
    var expected = List.of("1", "Smartphone \\Samsung \"refurbished\"", "Electronics", "50.00");
    CsvDocument doc = parse(csv);

    assertEquals(expected, doc.rows().get(0).values());
  }

  @Test
  @DisplayName("Should be able to parse csv with unicode escape characters")
  void shouldBeParseWithUnicodeCharacter() {

    var csv = "1,Smartphone Samsung,Electronics,\\u20AC50.00"; // Euro sign
    var expected = List.of("1", "Smartphone Samsung", "Electronics", "\u20AC50.00");
    CsvDocument doc = parse(csv);
    assertEquals(expected, doc.rows().get(0).values());
  }

  @Test
  @DisplayName("Should throw CsvParseException for invalid escape characters")
  void shouldThrowExceptionForWithInvalidEscapeCharacters() {
    Throwable t = assertThrows(CsvParseException.class, () -> parse("\\u002x").rows().get(0).values());
    assertTrue(t.getMessage().contains("Invalid unicode character"));
  }

  @Test
  @DisplayName("Should throw CsvParseException for unterminated strings")
  void shouldThrowExcetpionForUnterminatedStrings() {
    Throwable t = assertThrows(CsvParseException.class, () -> parse("Id,\"Name,Price"));
    assertTrue(t.getMessage().contains("Unterminated string"));
  }

  private CsvDocument parse(String csv) {
    return new CsvParser().parse(csv);
  }

  private CsvDocument parse(String csv, CsvConfig config) {
    return new CsvParser(config).parse(csv);
  }
}
