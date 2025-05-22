# Lake Ice Analyzer

A Java 17 project that implements a self-contained, iterable **doubly-linked list** for storing and analyzing historical lake ice records.  
The system reads structured freeze/thaw data, supports bidirectional iteration, filtering, and statistical analysis on winter ice durations.

---

##  Features

- **Custom doubly-linked list** built from scratch (no use of Java Collections)
- **Forward and backward iterators** for bidirectional traversal
- **Freeze event analysis**:
  - Average, shortest, longest freeze durations
  - Earliest freeze date, latest thaw date
- **Flexible filtering**:
  - Filter records by year range
  - Filter records by freeze duration range
- **Data cleaning**:
  - Handle missing or invalid data
  - Merge split freeze events across years

---

## 📂 Project Structure

| File | Description |
|------|-------------|
| `LakeIceAnalyzer.java` | Main list class; manages entries and provides analytics |
| `IceDataEntry.java` | Freeze/thaw record representation |
| `ForwardIterator.java`, `BackwardIterator.java` | Custom bidirectional iterators |
| `DateUtil.java` | Utilities for comparing and parsing dates |
| `DoubleNode.java` | Internal linked list node |
| `LakeRecordReader.java` | Reads lake record data from a file (optional) |
| `AnalyzerTester.java` | Test suite covering core functionality |
| `Main.java` | Sample driver with usage demo |

---

##  Example Usage

```java
LakeIceAnalyzer analyzer = new LakeIceAnalyzer();

analyzer.addEntry(new IceDataEntry(
    "Winter 2024–25",
    LocalDateTime.of(2024, 12, 1, 0, 0),
    LocalDateTime.of(2025, 3, 10, 0, 0)));

System.out.println("Earliest freeze: " + analyzer.getEarliestFreeze());
System.out.println("Average duration: " + analyzer.getAverageFreezeDuration() + " days");
System.out.println("Removed: " + analyzer.removeNext());