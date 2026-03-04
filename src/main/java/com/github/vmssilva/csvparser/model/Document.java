package com.github.vmssilva.csvparser.model;

import java.util.List;
import java.util.Optional;

public interface Document {
  List<Row> rows();

  Optional<Row> getRow(int index);

  Optional<Row> getHeader();

  int size();
}
