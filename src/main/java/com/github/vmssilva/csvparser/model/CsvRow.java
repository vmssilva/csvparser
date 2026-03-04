package com.github.vmssilva.csvparser.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.github.vmssilva.csvparser.exception.CsvParseException;

public record CsvRow(List<String> values) implements Row {

  public CsvRow(List<String> values) {
    this.values = requireNonNull(values);
  }

  @Override
  public String getString(int index) {
    if (index < 0 || index >= size())
      throw new CsvParseException("Index out of bound");

    return values.get(index);
  }

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

    if (value.length() > 1)
      throw new CsvParseException("value must be a valid char");

    return getString(index).charAt(0);
  }

  public Boolean getBoolean(int index) {

    String value = getString(index).toLowerCase();
    return switch (value) {
      case "y", "yes", "1", "true" -> true;
      case "n", "no", "0", "false" -> false;
      default -> throw new CsvParseException("value must be a valid boolean");
    };
  }
}
