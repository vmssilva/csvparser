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
  public List<Integer> keys() {
    return List.of();
  }

  @Override
  public String getString(int index) {
    return values.get(index);
  }

  @Override
  public int size() {
    return values.size();
  }

}

class CsvDocumentTest {

  @Test
  @DisplayName("Should return header correctly")
  void shouldReturnHeader() {

    List<Row> rows = List.of(
        new FakeRow(List.of("Name", "Category")),
        new FakeRow(List.of("Smartphone", "Electronics")));

    CsvDocument doc = new CsvDocument(rows, true);
    assertEquals(Optional.of(new FakeRow(List.of("Name", "Category"))), doc.getHeader());

    doc = new CsvDocument(rows, false);
    assertEquals(Optional.empty(), doc.getHeader());
  }

  @Test
  @DisplayName("Should return correct number of rows")
  void shouldReturnCorrectRowCount() {
    List<Row> rows = List.of(
        new FakeRow(List.of("Name", "Category")),
        new FakeRow(List.of("Smartphone", "Electronics")));

    CsvDocument doc = new CsvDocument(rows, false);
    assertEquals(2, doc.size());

    doc = new CsvDocument(rows, true);
    assertEquals(1, doc.size());
  }

  @Test
  @DisplayName("Should return all rows")
  void shouldReturnAllRow() {

    List<Row> rows = List.of(
        new FakeRow(List.of("Name", "Category")),
        new FakeRow(List.of("Smartphone", "Electronics")));

    assertEquals(rows, new CsvDocument(rows, false).rows());
    assertNotEquals(rows, new CsvDocument(rows, true).rows());

  }

  @Test
  @DisplayName("SShould return the correct row by index")
  void shouldReturnRowByIndex() {

    List<Row> rows = List.of(
        new FakeRow(List.of("Name", "Category")),
        new FakeRow(List.of("Smartphone", "Electronics")));

    CsvDocument doc = new CsvDocument(rows, false);
    assertEquals(Optional.of(new FakeRow(List.of("Name", "Category"))), doc.getRow(0));
    assertEquals(Optional.of(new FakeRow(List.of("Smartphone", "Electronics"))), doc.getRow(1));

    doc = new CsvDocument(rows, true);
    assertEquals(Optional.of(new FakeRow(List.of("Smartphone", "Electronics"))), doc.getRow(0));
  }

  @Test
  @DisplayName("Should return Optional.empty() when index is out of bounds")
  void tshouldReturnEmptyOptionalWhenIndexIsInvalid() {
    List<Row> rows = List.of(
        new FakeRow(List.of("Name", "Category")),
        new FakeRow(List.of("Smartphone", "Electronics")));

    CsvDocument doc = new CsvDocument(rows, false);

    assertEquals(Optional.empty(), doc.getRow(-1));
    assertEquals(Optional.empty(), doc.getRow(2));

    doc = new CsvDocument(rows, true);

    assertEquals(Optional.empty(), doc.getRow(-1));
    assertEquals(Optional.empty(), doc.getRow(1));
  }

  @Test
  @DisplayName("Should throw NullPointerException if rows are null")
  void shouldThrowExceptionWhenRowsAreNull() {
    assertThrows(NullPointerException.class, () -> new CsvDocument(null, false));
    assertThrows(NullPointerException.class, () -> new CsvDocument(null, true));
  }

  @Test
  void shouldHandleEmptyDocument() {

    CsvDocument doc = new CsvDocument(List.of(), false);

    assertEquals(0, doc.size());
    assertEquals(List.of(), doc.rows());
    assertEquals(Optional.empty(), doc.getHeader());
    assertEquals(Optional.empty(), doc.getRow(0));
  }

  @Test
  void shouldHandleDocumentWithOnlyHeader() {

    List<Row> rows = List.of(
        new FakeRow(List.of("Name", "Category")));

    CsvDocument doc = new CsvDocument(rows, true);

    assertEquals(0, doc.size());
    assertEquals(Optional.of(rows.get(0)), doc.getHeader());
  }

}
