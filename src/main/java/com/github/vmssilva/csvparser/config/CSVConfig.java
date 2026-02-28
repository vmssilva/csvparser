package com.github.vmssilva.csvparser.config;

public class CSVConfig {

  private char delimiter = ',';
  private char quoteCharacter = '"';
  private boolean hasHeader = false;

  public char getDelimiter() {
    return delimiter;
  }

  public void setDelimiter(char delimiter) {
    this.delimiter = delimiter;
  }

  public char getQuoteCharacter() {
    return quoteCharacter;
  }

  public void setQuoteCharacter(char quoteCharacter) {
    this.quoteCharacter = quoteCharacter;
  }

  public boolean hasHeader() {
    return hasHeader;
  }

  public void setHasHeader(boolean hasHeader) {
    this.hasHeader = hasHeader;
  }
}
