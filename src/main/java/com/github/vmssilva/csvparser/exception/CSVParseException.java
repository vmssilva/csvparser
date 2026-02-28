package com.github.vmssilva.csvparser.exception;

public class CSVParseException extends RuntimeException {

  private int line;
  private int column;

  public CSVParseException(String message) {
    super(message);
  }

  public CSVParseException(String message, int line, int column) {
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
