package com.github.vmssilva.csvparser.model;

import java.util.List;

public interface Row {

  List<String> values();

  List<Integer> keys();

  String getString(int index);

  int size();

  Integer getInt(int index);

  Float getFloat(int index);

  Double getDouble(int index);

  Long getLong(int index);

  Character getChar(int index);

  Boolean getBoolean(int index);

}
