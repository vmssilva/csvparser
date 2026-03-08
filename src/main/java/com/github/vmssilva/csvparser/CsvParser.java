package com.github.vmssilva.csvparser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.vmssilva.csvparser.config.CsvConfig;
import com.github.vmssilva.csvparser.exception.CsvParseException;
import com.github.vmssilva.csvparser.model.CsvDocument;
import com.github.vmssilva.csvparser.model.CsvRow;
import com.github.vmssilva.csvparser.model.Row;

public class CsvParser {

  private CsvConfig config;
  private String csv;
  private List<String> cols;
  private List<Row> rows;
  private int current;
  private StringBuilder value;

  public CsvParser(CsvConfig config) {
    this.config = Objects.requireNonNull(config);
  }

  public CsvParser() {
    this(new CsvConfig());
  }

  public CsvDocument parse(InputStream inputStream) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public CsvDocument parse(String source) {
    this.current = 0;
    this.value = new StringBuilder();
    this.cols = new ArrayList<>();
    this.rows = new ArrayList<>();
    this.csv = source;

    boolean hasHeader = config.hasHeader();

    while (!(isAtEnd())) {

      // return true if there's any character to consume
      if (scan(peek()))
        value.append(consume());

      if (isAtEnd())
        updateRows();

    }

    return new CsvDocument(rows, hasHeader);
  }

  private boolean scan(char c) {

    char delimiter = this.config.getDelimiter();
    char newLine = this.config.getNewLineCharacter();
    char quotes = this.config.getQuoteCharacter();

    if (c == '\\')
      return handleEscapes();

    if (c == quotes)
      return handleQuotes();

    if (c == delimiter)
      return handleDelimiter();

    if (c == newLine)
      return handleNewLine();

    return true;
  }

  private boolean handleDelimiter() {
    cols.add(value.toString());
    value.setLength(0);
    consume();
    return false;
  }

  private boolean handleNewLine() {
    updateRows();
    consume();
    return false;
  }

  private boolean handleQuotes() {

    char quotes = this.config.getQuoteCharacter();
    int started = current;
    boolean inQuotes = true;

    // consume quotes
    consume();

    while (!isAtEnd()) {

      if (!inQuotes)
        break;

      char c = consume();

      if (c == quotes) {
        started = current;
        inQuotes = !inQuotes;
        continue;
      }

      if (inQuotes) {
        value.append(c);
        continue;
      }

    }

    if (inQuotes)
      throw new CsvParseException("Unclosed string at index: " + started);

    return false;
  }

  private boolean handleEscapes() {
    if (isAtEnd())
      throw new CsvParseException("Invalid escape character found at index: " +
          current);

    // consume backslash character
    consume();
    char escaped = peek();

    switch (escaped) {
      case '\\' -> value.append('\\');
      case '"' -> value.append('"');
      case 't' -> value.append('\t');
      case 'f' -> value.append('\f');
      case 'b' -> value.append('\b');
      case 'n' -> value.append('\n');
      case '0' -> value.append('\0');
      case 'u' -> {

        int begin = current + 1;
        int end = begin + 4;

        if (end > csv.length())
          throw new CsvParseException("Invalid unicode character EOF");

        String unicodeStr = csv.substring(begin, end);

        try {
          char unicode = (char) Integer.parseInt(unicodeStr, 16);
          value.append(unicode);

          current += (end - begin);

        } catch (Exception e) {
          throw new CsvParseException("Invalid unicode character '\\u" + unicodeStr + "'");
        }
      }
      default ->
        throw new CsvParseException("Invalid escape character '\\" + escaped + "' found at index: " + current);
    }

    // consume escape character
    consume();

    return false;

  }

  private char consume() {
    return (isAtEnd()) ? '\0' : csv.charAt(current++);
  }

  private char peek() {
    return (isAtEnd()) ? '\0' : csv.charAt(current);
  }

  private boolean isAtEnd() {
    return current >= csv.length();
  }

  private void updateRows() {
    cols.add(value.toString());
    rows.add(new CsvRow(cols));
    value.setLength(0);
    cols = new ArrayList<>();
  }
}
