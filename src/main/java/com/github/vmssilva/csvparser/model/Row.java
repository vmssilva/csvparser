package com.github.vmssilva.csvparser.model;

import java.util.List;

public interface Row {

  List<String> values();

  String getString(int index);

}
