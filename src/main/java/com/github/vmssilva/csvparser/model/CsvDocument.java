package com.github.vmssilva.csvparser.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record CsvDocument(List<Row> rows, boolean hasHeader) implements Document {

  public CsvDocument(List<Row> rows, boolean hasHeader) {
    this.rows = Objects.requireNonNull(rows);
    this.hasHeader = hasHeader;
  }

  public List<Row> rows() {
    return List.copyOf(rows.subList(head(), rows.size()));
  }

  public Optional<Row> getRow(int index) {

    index += head();

    if ((index - head() < 0) || index - head() >= size())
      return Optional.empty();

    return Optional.of(rows.get(index));
  }

  public Optional<Row> getHeader() {
    if (head() < 1 || rows.size() <= 0)
      return Optional.empty();

    return Optional.of(rows.get(0));
  }

  public int size() {
    int len = rows.size();
    return (len > 0) ? len - head() : 0;
  }

  private int head() {
    return hasHeader ? 1 : 0;
  }

}
