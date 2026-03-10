package com.github.vmssilva.csvparser.model;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import com.github.vmssilva.csvparser.exception.CsvRowException;

public record CsvRow(List<String> values) implements Row {

  public CsvRow(List<String> values) {
    this.values = List.copyOf(requireNonNull(values));
  }

  @Override
  public List<Integer> keys() {
    return IntStream.range(0, values.size()).boxed().toList();
  }

  @Override
  public String getString(int index) {
    if (index < 0 || index >= size())
      throw new CsvRowException(
          "Index " + index + " is out of bounds for row of size " + size());

    return values.get(index);
  }

  @Override
  public int size() {
    return values.size();
  }

  public Integer getInt(int index) {
    return Integer.valueOf(getString(index));
  }

  public Float getFloat(int index) {
    return Float.valueOf(getString(index));
  }

  public Double getDouble(int index) {
    return Double.valueOf(getString(index));
  }

  public Long getLong(int index) {
    return Long.valueOf(getString(index));
  }

  public Character getChar(int index) {
    String value = getString(index);

    if (value.length() != 1)
      throw new CsvRowException(
          "Expected a single character at index " + index + " but found \"" + value + "\"");

    return value.charAt(0);
  }

  public Boolean getBoolean(int index) {

    String value = getString(index).toLowerCase();
    return switch (value) {
      case "y", "yes", "1", "true" -> true;
      case "n", "no", "0", "false" -> false;
      default -> throw new CsvRowException("Invalid boolean value: '" + value + "'");
    };
  }

  public CsvRowIterator iterator() {
    return new CsvRowIterator(values());
  }

  public static class CsvRowIterator implements Iterator<String> {

    private final List<String> values;
    private int pos;

    public CsvRowIterator(List<String> values) {
      this.values = values;
    }

    @Override
    public boolean hasNext() {
      return pos < this.values.size();
    }

    @Override
    public String next() {
      return this.values.get(pos++);
    }

  }

}
