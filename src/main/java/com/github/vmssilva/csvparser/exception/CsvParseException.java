package com.github.vmssilva.csvparser.exception;

public class CsvParseException extends RuntimeException {

  private int line;
  private int column;

  public CsvParseException(String message) {
    super(message);
  }

  public CsvParseException(String message, int line, int column) {
    super(message);
    this.line = line;
    this.column = column;
  }

  public int getLine() {
    return line;
  }

  public int getColumn() {
    return column;
  }

}
