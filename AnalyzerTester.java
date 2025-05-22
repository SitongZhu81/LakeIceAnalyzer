

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Tester class for FreezeTracker functionality.
 */
public class AnalyzerTester {

  /**
   * Tests adding records to an empty FreezeTracker and the end of a non-empty FreezeTracker.
   * 
   * Ensure that size has been updated correctly, that the first and last winters are correct, and
   * that all added records are present in the correct locations in the list.
   * 
   * @return true if all cases pass, false otherwise.
   */
  public static boolean testAdd() {
    // Create a new FreezeTracker
    LakeIceAnalyzer ft = new LakeIceAnalyzer();

    // Create two records
    IceDataEntry r1 = new IceDataEntry("2021-22", "December 5", "March 1", 85);
    IceDataEntry r2 = new IceDataEntry("2020-21", "December 10", "February 28", 80);

    // Add records to tracker
    ft.add(r1);
    ft.add(r2);

    // Validate size and order
    return ft.size() == 2 && ft.get(0).equals(r1) && ft.get(1).equals(r2)
        && ft.getHead().getLakeRecord().equals(r1) && ft.getTail().getLakeRecord().equals(r2);
  }

  /**
   * Tests removing records from different positions (beginning, middle, end). Your initial
   * FreezeTracker list should contain AT LEAST five records; none of these tests will clear out the
   * list (that's a different test, below).
   * 
   * Verify both return values and the list state.
   * 
   * @return true if all cases pass, false otherwise.
   */
  public static boolean testRemove() {
    // Create FreezeTracker and add 5 records
    LakeIceAnalyzer ft = new LakeIceAnalyzer();
    IceDataEntry r1 = new IceDataEntry("2022-23", "Dec 1", "Mar 1", 90);
    IceDataEntry r2 = new IceDataEntry("2021-22", "Dec 5", "Feb 28", 85);
    IceDataEntry r3 = new IceDataEntry("2020-21", "Dec 10", "Feb 15", 75);
    IceDataEntry r4 = new IceDataEntry("2019-20", "Dec 15", "Feb 10", 70);
    IceDataEntry r5 = new IceDataEntry("2018-19", "Dec 20", "Feb 5", 65);
    ft.add(r1);
    ft.add(r2);
    ft.add(r3);
    ft.add(r4);
    ft.add(r5);

    // Remove head, middle, and tail
    boolean removedHead = ft.remove(r1);
    boolean removedMid = ft.remove(r3);
    boolean removedTail = ft.remove(r5);

    // Validate state of list
    return removedHead && removedMid && removedTail && ft.size() == 2 && ft.get(0).equals(r2)
        && ft.get(1).equals(r4);
  }

  /**
   * Tests removing the ONLY value from a FreezeTracker.
   * 
   * Ensure all accessor methods behave correctly after removing it.
   * 
   * @return true if all cases pass, false otherwise.
   */
  public static boolean testRemoveOnly() {
    // Create FreezeTracker with one record
    LakeIceAnalyzer ft = new LakeIceAnalyzer();
    IceDataEntry r = new IceDataEntry("2023-24", "December 1", "March 2", 90);
    ft.add(r);

    // Remove the only record
    boolean removed = ft.remove(r);

    // Validate it is empty
    return removed && ft.isEmpty();
  }

  /**
   * Tests removing a record from FreezeTracker which is not present in the list.
   * 
   * Verify both the return value and the list state.
   * 
   * @return true if all cases pass, false otherwise.
   */
  public static boolean testRemoveDoesNotExist() {
    // Create tracker and add two valid records
    LakeIceAnalyzer ft = new LakeIceAnalyzer();
    IceDataEntry r1 = new IceDataEntry("2022-23", "Dec 1", "Mar 1", 90);
    IceDataEntry r2 = new IceDataEntry("2021-22", "Dec 5", "Feb 28", 85);
    IceDataEntry missing = new IceDataEntry("2000-01", "Dec 1", "Jan 1", 30);
    ft.add(r1);
    ft.add(r2);

    // Try removing a record that does not exist
    return !ft.remove(missing) && ft.size() == 2;
  }

  /**
   * Tests iterators (forward and backward). For full credit, this test MUST contain at least one
   * enhanced for loop with each type of iterator.
   * 
   * @return true if all cases pass, false otherwise.
   */
  public static boolean testIterators() {
    // Create FreezeTracker and add two records
    LakeIceAnalyzer ft = new LakeIceAnalyzer();
    IceDataEntry r1 = new IceDataEntry("2022-23", "Dec 1", "Mar 1", 90);
    IceDataEntry r2 = new IceDataEntry("2021-22", "Dec 5", "Feb 28", 85);
    ft.add(r1);
    ft.add(r2);

    // Use forward iterator
    ft.setReversed(false);
    String forward = "";
    for (IceDataEntry r : ft) {
      forward += r.getWinter() + " ";
    }

    // Use backward iterator
    ft.setReversed(true);
    String backward = "";
    for (IceDataEntry r : ft) {
      backward += r.getWinter() + " ";
    }

    // Validate order
    return forward.equals("2022-23 2021-22 ") && backward.equals("2021-22 2022-23 ");
  }

  /**
   * Tests merging multiple freeze records (provided!) for the same winter.
   * 
   * Add these records to a FreezeTracker and verify that merging them results in a list with a
   * single record with the correct values.
   * 
   * @return true if all cases pass, false otherwise.
   */
  public static boolean testMergeWinters() {
    // Create multiple freeze records for the same winter
    IceDataEntry freeze1 = new IceDataEntry("2019-20", "December 1", "January 15", 45);
    IceDataEntry freeze2 = new IceDataEntry("2019-20", "January 20", "March 10", 50);
    IceDataEntry freeze3 = new IceDataEntry("2019-20", "March 15", "April 5", 20);

    // Add to FreezeTracker and merge
    LakeIceAnalyzer ft = new LakeIceAnalyzer();
    ft.add(freeze1);
    ft.add(freeze2);
    ft.add(freeze3);
    ft.mergeWinters();

    // Validate merged result
    return ft.size() == 1 && ft.get(0).getDaysOfIceCover() == 115
        && ft.get(0).getFreezeDate().equals("December 1")
        && ft.get(0).getThawDate().equals("April 5");
  }

  /**
   * Tests cleaning the dataset (removing incomplete records). Create a FreezeTracker with some
   * valid and invalid records, and verify that all of the invalid records are removed (and none of
   * the valid ones).
   * 
   * @return true if all cases pass, false otherwise.
   */
  public static boolean testCleanData() {
    // Create records: one complete, two incomplete
    IceDataEntry r1 = new IceDataEntry("2020-21", "December 1", "March 1", 80);
    IceDataEntry r2 = new IceDataEntry("2021-22", null, "February 28", -1);
    IceDataEntry r3 = new IceDataEntry("2022-23", "December 5", null, -1);

    // Build data and initialize tracker
    ArrayList<IceDataEntry> list = new ArrayList<>();
    list.add(r1);
    list.add(r2);
    list.add(r3);
    LakeIceAnalyzer ft = new LakeIceAnalyzer(list);

    // Only valid record should remain
    return ft.size() == 1 && ft.get(0).equals(r1);
  }

  /**
   * Tests computing the average freeze duration.
   * 
   * @return true if all cases pass, false otherwise.
   */
  public static boolean testAverageFreezeDuration() {
    LakeIceAnalyzer ft = new LakeIceAnalyzer();
    ft.add(new IceDataEntry("2020-21", "December 1", "March 1", 1));
    ft.add(new IceDataEntry("2021-22", "December 10", "February 28", 2));
    float avg = ft.getAverageFreezeDuration();
    return Math.abs(avg - 1.5f) < 0.0001f;
  }

  /**
   * Tests finding the maximum number of days of ice cover in a single winter.
   * 
   * @return true if all cases pass, false otherwise.
   */
  public static boolean testMaxFreezeDuration() {
    // Add two records and check max
    LakeIceAnalyzer ft = new LakeIceAnalyzer();
    ft.add(new IceDataEntry("2020-21", "December 1", "March 1", 90));
    ft.add(new IceDataEntry("2021-22", "December 10", "February 28", 80));
    return ft.getMaxFreezeDuration() == 90;
  }

  /**
   * Tests finding the minimum number of days of ice cover in a single winter.
   * 
   * @return true if all cases pass, false otherwise.
   */
  public static boolean testMinFreezeDuration() {
    // Add two records and check min
    LakeIceAnalyzer ft = new LakeIceAnalyzer();
    ft.add(new IceDataEntry("2020-21", "December 1", "March 1", 90));
    ft.add(new IceDataEntry("2021-22", "December 10", "February 28", 80));
    return ft.getMinFreezeDuration() == 80;
  }

  /**
   * Tests finding the earliest freeze.
   * 
   * @return true if all cases pass, false otherwise.
   */
  public static boolean testGetEarliestFreeze() {
    // Add two records and find earliest freeze date
    LakeIceAnalyzer ft = new LakeIceAnalyzer();
    ft.add(new IceDataEntry("2020-21", "December 10", "March 1", 80));
    ft.add(new IceDataEntry("2021-22", "December 1", "March 5", 85));
    return ft.getEarliestFreeze().equals("December 1");
  }

  /**
   * Tests finding the latest thaw.
   * 
   * @return true if all cases pass, false otherwise.
   */
  public static boolean testGetLatestThaw() {
    // Add two records and find latest thaw date
    LakeIceAnalyzer ft = new LakeIceAnalyzer();
    ft.add(new IceDataEntry("2020-21", "December 10", "March 1", 80));
    ft.add(new IceDataEntry("2021-22", "December 1", "March 15", 90));
    return ft.getLatestThaw().equals("March 15");
  }

  /**
   * Tests filtering freeze records by a range of years.
   * 
   * Ensure that only records between the specified years (inclusive) are present in the result.
   * 
   * @return true if all cases pass, false otherwise.
   */
  public static boolean testFilterByYear() {
    // Add two records and filter by year 2020
    LakeIceAnalyzer ft = new LakeIceAnalyzer();
    ft.add(new IceDataEntry("2020-21", "December 1", "March 1", 90));
    ft.add(new IceDataEntry("2019-20", "December 5", "March 2", 88));
    LakeIceAnalyzer filtered = ft.filterByYear(2020);
    return filtered.size() == 1 && filtered.get(0).getWinter().equals("2020-21");
  }

  /**
   * Tests filtering freeze records by a range of ice cover duration values.
   * 
   * Ensure that only records within the duration range are included in the filtered list.
   * 
   * @return true if all cases pass, false otherwise.
   */
  public static boolean testFilterByDuration() {
    LakeIceAnalyzer ft = new LakeIceAnalyzer();
    ft.add(new IceDataEntry("2020-21", "December 1", "March 1", 80)); // lower bound
    ft.add(new IceDataEntry("2019-20", "December 5", "March 2", 100)); // upper bound
    LakeIceAnalyzer filtered = ft.filterByDuration(80, 100);
    return filtered.size() == 2;
  }

  /**
   * Main Method to Launch the tester methods.
   * 
   * @param args list of inputs if any.
   */
  public static void main(String[] args) {
    System.out.println("Running tests:");
    System.out.println("testAdd(): " + (testAdd() ? "PASSED" : "FAILED"));
    System.out.println("testRemove(): " + (testRemove() ? "PASSED" : "FAILED"));
    System.out.println("testRemoveOnly(): " + (testRemoveOnly() ? "PASSED" : "FAILED"));
    System.out
        .println("testRemoveDoesNotExist(): " + (testRemoveDoesNotExist() ? "PASSED" : "FAILED"));
    System.out.println("testIterators(): " + (testIterators() ? "PASSED" : "FAILED"));
    System.out.println("testMergeWinters(): " + (testMergeWinters() ? "PASSED" : "FAILED"));
    System.out.println("testCleanData(): " + (testCleanData() ? "PASSED" : "FAILED"));
    System.out.println(
        "testAverageFreezeDuration(): " + (testAverageFreezeDuration() ? "PASSED" : "FAILED"));
    System.out
        .println("testMaxFreezeDuration(): " + (testMaxFreezeDuration() ? "PASSED" : "FAILED"));
    System.out
        .println("testMinFreezeDuration(): " + (testMinFreezeDuration() ? "PASSED" : "FAILED"));
    System.out
        .println("testGetEarliestFreeze(): " + (testGetEarliestFreeze() ? "PASSED" : "FAILED"));
    System.out.println("testGetLatestThaw(): " + (testGetLatestThaw() ? "PASSED" : "FAILED"));
    System.out.println("testFilterByYear(): " + (testFilterByYear() ? "PASSED" : "FAILED"));
    System.out.println("testFilterByDuration(): " + (testFilterByDuration() ? "PASSED" : "FAILED"));

    boolean allTestsPassed =
        testAdd() && testRemove() && testRemoveOnly() && testRemoveDoesNotExist() && testIterators()
            && testMergeWinters() && testCleanData() && testAverageFreezeDuration()
            && testMaxFreezeDuration() && testMinFreezeDuration() && testGetEarliestFreeze()
            && testGetLatestThaw() && testFilterByYear() && testFilterByDuration();
    System.out.println("ALL TESTS: " + (allTestsPassed ? "PASSED" : "FAILED"));
  }
}
