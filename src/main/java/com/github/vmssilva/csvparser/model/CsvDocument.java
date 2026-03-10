package com.github.vmssilva.csvparser.model;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record CsvDocument(List<Row> rows, boolean hasHeader) {

  public CsvDocument(List<Row> rows, boolean hasHeader) {
    this.rows = Objects.requireNonNull(rows);
    this.hasHeader = hasHeader;
  }

  public List<Row> rows() {
    return List.copyOf(rows.subList(headerOffset(), rows.size()));
  }

  public Optional<Row> getRow(int index) {

    index += headerOffset();

    if ((index - headerOffset() < 0) || index - headerOffset() >= size())
      return Optional.empty();

    return Optional.of(rows.get(index));
  }

  public Optional<Row> getHeader() {
    if (!hasHeader || rows.isEmpty())
      return Optional.empty();

    return Optional.of(rows.get(0));
  }

  public int size() {
    int len = rows.size();
    return (len > 0) ? len - headerOffset() : 0;
  }

  private int headerOffset() {
    return hasHeader ? 1 : 0;
  }

  public CsvDocumentIterator iterator() {
    return new CsvDocumentIterator(rows());
  }

  public static class CsvDocumentIterator implements Iterator<Row> {

    private final List<Row> rows;
    private int pos;

    public CsvDocumentIterator(List<Row> rows) {
      this.rows = rows;
    }

    @Override
    public boolean hasNext() {
      return pos < this.rows.size();
    }

    @Override
    public Row next() {
      return this.rows.get(pos++);
    }

  }
}
