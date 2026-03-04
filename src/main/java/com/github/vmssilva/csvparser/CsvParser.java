package com.github.vmssilva.csvparser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.vmssilva.csvparser.config.CsvConfig;
import com.github.vmssilva.csvparser.model.CsvDocument;
import com.github.vmssilva.csvparser.model.CsvRow;
import com.github.vmssilva.csvparser.model.Document;
import com.github.vmssilva.csvparser.model.Row;

public class CsvParser {

  public Document parse(InputStream inputStream) {
    return null;
  }

  public Document parse(InputStream inputStream, CsvConfig config) {
    return null;
  }

  public Document parse(String csv) {
    return parse(csv, new CsvConfig());
  }

  public Document parse(String csv, CsvConfig config) {
    List<Row> rows = new ArrayList<>();
    List<String> cols = new ArrayList<>();
    StringBuilder value = new StringBuilder();
    char delimiter = config.getDelimiter();
    char lineBreak = config.getLineBreakCharacter();
    char quoteCharacter = config.getQuoteCharacter();
    boolean hasHeader = config.hasHeader();

    int current = 0;

    csv = csv
        .trim()
        .replace("\t\n+", "\n")
        .replace("\n+", "\n")
        .replace("^\n+", "")
        .replace("\n+$", "")
        .trim();

    while (current < csv.length()) {
      char c = csv.charAt(current++);
      value.append(c);

      if (c == '\\') {
        // space to handle escape charater
      }

      if (c == quoteCharacter) {
        // space to handle quote charater
      }

      if (c == delimiter || c == lineBreak) {
        // Ensure that the delimiter or linebreak character is not added to the cols
        cols.add(value.toString().substring(0, value.length() - 1));
        // cleaning the value string to the next iteration
        value.setLength(0);
      }

      if (c == lineBreak) {
        rows.add(new CsvRow(cols));
        // cleaning the cols to the next iteration
        cols = new ArrayList<>();
      }

      if (current >= csv.length()) {
        // adding last field to cols
        cols.add(value.toString());
        rows.add(new CsvRow(cols));
      }
    }

    return new CsvDocument(rows, hasHeader);
  }

}
