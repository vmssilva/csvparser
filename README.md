# CSV Parser

A lightweight CSV parser written in Java, capable of handling:

* CSVs with or without headers
* Quoted fields (start, middle, end)
* Escape sequences (`\\`, `\"`, `\t`, `\n`, Unicode `\uXXXX`)
* Edge cases like empty CSVs or unclosed strings

---

## Installation

Add the project to your Java workspace or include it as a Maven dependency if packaged.

---

## Usage

### Basic CSV parsing

```java
import com.github.vmssilva.csvparser.CsvParser;
import com.github.vmssilva.csvparser.model.CsvDocument;
import com.github.vmssilva.csvparser.model.Row;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String csv = "Id,Name,Salary\n1,John Doe,5000";

        CsvParser parser = new CsvParser();
        CsvDocument doc = parser.parse(csv);

        for (Row row : doc.rows()) {
            System.out.println(row.values());
        }
    }
}
```

**Output:**

```
[1, John Doe, 5000]
```

---

### Parsing CSV with a header

If the CSV has a header, you can configure the parser to treat the first line as the header:

```java
import com.github.vmssilva.csvparser.CsvParser;
import com.github.vmssilva.csvparser.config.CsvConfig;
import com.github.vmssilva.csvparser.model.CsvDocument;

CsvConfig config = new CsvConfig();
config.setHasHeader(true);

String csv = "Id,Name,Salary\n1,John Doe,5000";

CsvParser parser = new CsvParser(config);
CsvDocument doc = parser.parse(csv);

// Access header
System.out.println(doc.getHeader().get().values()); // [Id, Name, Salary]

// Access rows (header excluded automatically)
doc.rows().forEach(row -> System.out.println(row.values()));
```

**Output:**

```
[Id, Name, Salary]
[1, John Doe, 5000]
```

---

### Handling quoted fields

The parser correctly handles CSV fields enclosed in quotes, including commas inside quotes:

```java
String csv = "Id,\"Name, Full\",Salary\n1,\"John, Doe\",5000";
CsvDocument doc = new CsvParser().parse(csv);

doc.rows().forEach(row -> System.out.println(row.values()));
```

**Output:**

```
[1, John, Doe, 5000]
```

---

### Escape sequences

Supports common escape sequences and Unicode:

```java
String csv = "Id,Description,Price\n1,Smartphone \\\"Refurbished\\\",399.90";
CsvDocument doc = new CsvParser().parse(csv);

System.out.println(doc.rows().get(1).values());
```

**Output:**

```
[1, Smartphone "Refurbished", 399.90]
```

Unicode example:

```java
String csv = "Id,Product,Price\n1,Phone,\\u20AC399.90";
CsvDocument doc = new CsvParser().parse(csv);
System.out.println(doc.rows().get(1).values()); // [1, Phone, €399.90]
```

---

### Error handling

The parser throws `CsvParseException` in the following cases:

* Unclosed quotes
* Invalid escape sequences
* Invalid boolean values (if using `CsvRow.getBoolean`)

```java
try {
    new CsvParser().parse("Id,\"Name,Price");
} catch (CsvParseException e) {
    System.out.println(e.getMessage()); // Unclosed string at index ...
}
```

---

### CsvRow data access

Each row allows convenient access to typed values:

```java
CsvRow row = (CsvRow) doc.rows().get(0);

int id = row.getInt(0);
String name = row.getString(1);
double price = row.getDouble(2);
boolean ok = row.getBoolean(3); // y, yes, 1, true
boolean no = row.getBoolean(4); // n, no, 0, false

```

**Notes:**

* Invalid conversions (e.g., `row.getInt("abc")`) throw `NumberFormatException`.
* Boolean values not recognized (`banana`, empty string) throw `CsvParseException`.

---

### Advanced Configuration

You can customize:

* `delimiter` (default `,`)
* `quote character` (default `"`)
* `newline character` (default `\n`)
* `hasHeader` (default `false`)

```java
CsvConfig config = new CsvConfig();
config.setDelimiter(';');
config.setQuoteCharacter('\'');
config.setHasHeader(true);
```

---

### Testing

The project comes with **unit tests** covering:

* Empty CSVs
* CSVs with only headers
* Quotes in start, middle, and end
* Escape characters and Unicode
* Invalid cases that throw exceptions

---

### License

MIT License – free to use in your projects and portfolio.

