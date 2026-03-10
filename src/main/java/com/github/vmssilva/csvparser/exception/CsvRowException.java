package com.github.vmssilva.csvparser.exception;

public class CsvRowException extends RuntimeException {

  private int column;

  public CsvRowException(String message) {
    super(message);
  }

  public CsvRowException(String message, int column) {
    super(message);
    this.column = column;
  }

  public int getColumn() {
    return column;
  }

}
