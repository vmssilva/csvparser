package com.github.vmssilva.csvparser;

import com.github.vmssilva.csvparser.config.CsvConfig;
import com.github.vmssilva.csvparser.model.CsvRow;

public class App {
  public static void main(String[] args) {

    var csv = """
        Id,Name,Category
        1,Samartphone,Electronics
        2,Laptop,Computer
        3,Mechanical Keyboard,Accessories""";

    var config = new CsvConfig();
    config.setHasHeader(true);
    var parser = new CsvParser(config);
    var doc = parser.parse(csv);

    System.out.println(doc.getRow(0).get().values());
    System.out.println("------------------------------");
    System.out.println();

    CsvRow row = (CsvRow) doc.getRow(0).get();

    var it = row.iterator();
    while (it.hasNext())
      System.out.println(it.next());

  }

}
