package com.github.vmssilva.csvparser;

import com.github.vmssilva.csvparser.model.Document;

public interface Parser {
  Document parse(String source);
}
