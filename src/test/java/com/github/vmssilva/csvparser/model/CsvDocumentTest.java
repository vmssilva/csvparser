package com.github.vmssilva.csvparser.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

record FakeRow(List<String> values) implements Row {
  @Override
  public String getString(int index) {
    return null;
  }
}

class CsvDocumentTest {

  Document doc;
  List<Row> rows;

  @Test
  @DisplayName("Should return header correctly")
  void testGetHeader() {

    rows = List.of(
        new FakeRow(List.of("Name", "Category")),
        new FakeRow(List.of("Smartphone", "Electroics")));

    doc = new CsvDocument(rows, true);
    assertEquals(Optional.of(new FakeRow(List.of("Name", "Category"))), doc.getHeader());

    doc = new CsvDocument(rows, false);
    assertEquals(Optional.empty(), doc.getHeader());
  }

  @Test
  @DisplayName("Should return total of rows correctly")
  void testTotalOfElements() {
    rows = List.of(
        new FakeRow(List.of("Name", "Category")),
        new FakeRow(List.of("Smartphone", "Electronics")));

    doc = new CsvDocument(rows, false);
    assertEquals(2, doc.size());

    doc = new CsvDocument(rows, true);
    assertEquals(1, doc.size());
  }

  @Test
  @DisplayName("Should that return all rows")
  void testGetAllElements() {

    rows = List.of(
        new FakeRow(List.of("Name", "Category")),
        new FakeRow(List.of("Smartphone", "Electronics")));

    assertEquals(rows, new CsvDocument(rows, false).rows());
    assertNotEquals(rows, new CsvDocument(rows, true).rows());

  }

  @Test
  @DisplayName("Should return correct row")
  void testGetRowByIndex() {

    rows = List.of(
        new FakeRow(List.of("Name", "Category")),
        new FakeRow(List.of("Smartphone", "Electronics")));

    doc = new CsvDocument(rows, false);
    assertEquals(Optional.of(new FakeRow(List.of("Name", "Category"))), doc.getRow(0));
    assertEquals(Optional.of(new FakeRow(List.of("Smartphone", "Electronics"))), doc.getRow(1));

    doc = new CsvDocument(rows, true);
    assertEquals(Optional.of(new FakeRow(List.of("Smartphone", "Electronics"))), doc.getRow(0));
  }

  @Test
  @DisplayName("Should return an Optional.empty() If an index is out of bounds")
  void testGetRowByIndexWithAnInvalidIndex() {
    rows = List.of(
        new FakeRow(List.of("Name", "Category")),
        new FakeRow(List.of("Smartphone", "Electronics")));

    doc = new CsvDocument(rows, false);

    assertEquals(Optional.empty(), doc.getRow(-1));
    assertEquals(Optional.empty(), doc.getRow(2));

    doc = new CsvDocument(rows, true);

    assertEquals(Optional.empty(), doc.getRow(-1));
    assertEquals(Optional.empty(), doc.getRow(1));
  }

  @Test
  @DisplayName("Should throws NullPointerException if the rows is null")
  void testConstructorWithNullValue() {
    assertThrows(NullPointerException.class, () -> new CsvDocument(null, false));
    assertThrows(NullPointerException.class, () -> new CsvDocument(null, true));
  }

}
