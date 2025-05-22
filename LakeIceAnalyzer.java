/**
 * LakeIceAnalyzer – An iterable doubly-linked list that stores yearly ice-cover
 * records and supports filtering & statistical analysis.
 *
 * Originally inspired by public lake-ice data; rewritten as an independent
 */

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A doubly-linked list implementation for managing freeze-thaw records of Lake Mendota. Implements
 * ListADT and Iterable, providing operations for adding, removing, merging, and analyzing freeze
 * data.
 */
public class LakeIceAnalyzer implements ListADT<IceDataEntry>, Iterable<IceDataEntry> {
  /**
   * Pointer to head of the linked list.
   */
  private DoubleNode head;
  /**
   * Pointer to tail of the linked list.
   */
  private DoubleNode tail;
  /**
   * Number of elements in the list.
   */
  private int size;
  /**
   * Whether to traverse the list is reverse-chronological order.
   */
  private boolean reversed;

  /**
   * Constructs an empty FreezeTracker.
   */
  public LakeIceAnalyzer() {
    head = null;
    tail = null;
    size = 0;
    reversed = false;
  }

  /**
   * Constructs a FreezeTracker and initializes it with an ArrayList of LakeRecords. This
   * constructor processes the provided dataset. After this process, the linked list will contain
   * exactly one cleaned and merged record per winter.
   * 
   * @param records The list of LakeRecord objects read from FreezeData.csv. This list may contain
   *                missing or duplicate entries, which are handled during initialization.
   */
  public LakeIceAnalyzer(ArrayList<IceDataEntry> records) {
    // list to store processed records
    ArrayList<IceDataEntry> processed = new ArrayList<>();

    // process each record in the list records
    for (IceDataEntry r : records) {

      // skip null elements in records
      if (r == null) {
        // CITE - learned how to use continue - https://www.w3schools.com/java/java_break.asp
        continue;
      }

      // skip incomplete record in the list records
      if (!r.hasCompleteData()) {
        continue;
      }

      r.updateDuration(); // updates the freeze duration

      processed.add(r.copy());

    }

    // initialize fields
    reversed = false;
    size = 0;
    head = null;

    DoubleNode prev = null; // stores the previous node

    // use processed to build the linked list
    for (IceDataEntry r : processed) {

      // create a node to be the current node
      DoubleNode cur = new DoubleNode(r);

      // initialize the head when head is null
      if (head == null) {
        head = cur;
      }

      // except for the head, set previous node's next to the current node, and set the current
      // node's previous to the previous node, connecting previous and current node
      if (prev != null) {
        prev.setNext(cur);
        cur.setPrev(prev);
      }

      // after connecting prev and cur, store current node in prev, making it the previous node in
      // the next iteration
      prev = cur;
      size++;
    }

    mergeWinters();

    // set tail to the last record
    tail = prev;
  }

  /**
   * Returns the number of records in the list.
   * 
   * @return The size of the list.
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Checks if the list is empty.
   * 
   * @return True if the list is empty, false otherwise.
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Clears all records from the list.
   */
  @Override
  public void clear() {
    head = null;
    tail = null;
    size = 0;
  }

  /**
   * Specifies which direction the list should be traversed in the future
   * 
   * @param reversed whether to traverse the list backwards
   */
  public void setReversed(boolean reversed) {
    this.reversed = reversed;
  }

  /**
   * Getter method for head
   * 
   * @return head of the linked list
   */
  public DoubleNode getHead() {
    return head;
  }

  /**
   * Getter method for tail
   * 
   * @return tail of the linked list
   */
  public DoubleNode getTail() {
    return tail;
  }


  /**
   * Appends a new freeze record to the end of the linked list in O(1) time.
   *
   * <br>
   * <br>
   * Note: This method is closely related to the learning objectives of the assignment, and so we'll
   * pay special attention to it during manual grading. Be sure to leave comments explaining each
   * algorithmic step you use!
   *
   * @param record The record to add.
   */
  @Override
  public void add(IceDataEntry record) {

    // create a node containing the record to add
    // it's to be added to the end of the linked list as the new tail
    DoubleNode newTail = new DoubleNode(record);

    // check if the linked list is empty
    if (head == null && tail == null) {

      // when list is empty, set both head and tail to the node containing the record to add
      head = newTail;
      tail = newTail;

      // updates the size of list
      size++;

    } else {
      // When list is not empty:

      // set the new tail's previous pointer to the old tail
      newTail.setPrev(tail);
      // set the old tail's next pointer to the new tail
      tail.setNext(newTail);

      // after connecting old tail and new tail, store the reference of new tail in the tail pointer
      // of the linked list
      tail = newTail;

      // updates the size of list
      size++;
    }
  }

  /**
   * Removes the given node from the linked list in O(1) time. Note: this method does not verify
   * that the given node is a member of the list, and should only be used as a helper function
   * inside the FreezeTracker class.
   *
   * <br>
   * <br>
   * Note: This method is closely related to the learning objectives of the assignment, and so we'll
   * pay special attention to it during manual grading. Be sure to leave comments explaining each
   * algorithmic step you use!
   *
   * @param node the node to be removed
   * @throws IllegalArgumentException if node is null
   */
  private void removeNode(DoubleNode node) {

    // throw IllegalArgumentException if node is null
    if (node == null) {
      throw new IllegalArgumentException("ERROR: cannot remove null node");
    }

    // make sure that node's previous pointer is not null to prevent NullPointerException
    if (node.getPrev() != null) {
      // connect node's previous node to node's next node, by setting node's prev's next pointer to
      // node's next
      node.getPrev().setNext(node.getNext());
    }

    // make sure that node's next pointer is not null to prevent NullPointerException
    if (node.getNext() != null) {
      // connect node's next node to node's previous node, by setting node's next's previous pointer
      // to node's prev
      node.getNext().setPrev(node.getPrev());
    }

    // if removing the only node, update head and tail pointer to null
    if (node == head && node == tail) {
      head = null;
      tail = null;
    }

    // if removing the head, update head pointer to the node after it
    if (node == head) {
      head = node.getNext();
    }

    // if removing the tail, update tail pointer to the node before it
    if (node == tail) {
      tail = node.getPrev();
    }

    // set next pointer of node to null
    node.setNext(null);

    // set previous pointer of node to null
    node.setPrev(null);

  }

  /**
   * Removes the first node in the list that contains the given record.
   *
   * <br>
   * <br>
   * Note: This method is closely related to the learning objectives of the assignment, and so we'll
   * pay special attention to it during manual grading. Be sure to leave comments explaining each
   * algorithmic step you use!
   *
   * @param record the record to be removed
   * @return boolean indicating whether the record was found in the list
   */
  @Override
  public boolean remove(IceDataEntry record) {

    DoubleNode node = find(record);
    if (node != null) {
      removeNode(node);
      size--;
      return true;
    }

    return false;
  }

  /**
   * Finds the given record in the list
   * 
   * @param record the LakeRecord to search for
   * @return The first LinkedNode containing the given record, or null if none exists
   */
  public DoubleNode find(IceDataEntry record) {

    // if list is empty, return null
    if (head == null) {
      return null;
    }

    // create a node to store current node. and start from head of the linked list
    DoubleNode cur = head;

    // traverse the linked list, but leaving the tail
    while (cur != tail) {

      // if found the record in the list, remove the node contaning the record and return true
      if (cur.getLakeRecord().equals(record)) {
        return cur;
      }

      // store current node's next pointer in cur to be the current node in the next iteration
      cur = cur.getNext();
    }

    // now cur contains the tail pointer
    // check if the record is in tail pointer. if it is, return cur
    if (cur.getLakeRecord().equals(record)) {
      return cur;
    }

    // return null when not found the record in the list
    return null;
  }

  /**
   * Returns the LakeRecord at index i in the list, using zero-indexing.
   * 
   * @param i a non-negative integer
   * @return The LakeRecord at the given index
   * @throws IndexOutOfBoundsException if i is negative or greater than size()-1
   */
  public IceDataEntry get(int i) {

    // throw IndexOutOfBoundsException if i is negative or greater than size()-1
    if (i < 0 || i > size - 1) {
      throw new IndexOutOfBoundsException(
          "ERROR: Index out of bounds (i is negative or greater than size()-1)");
    }

    // create a node to store current node. and start from head of the linked list
    DoubleNode cur = head;

    int index = 0; // for tracking index of iteration

    // traverse the linked list
    while (index <= i) {

      // returns the LakeRecord at index i
      if (index == i) {
        return cur.getLakeRecord();
      }

      // store current node's next pointer in cur to be the current node in the next iteration
      cur = cur.getNext();

      // increase index
      index++;
    }
    return null;
  }

  /**
   * Provides an iterator for traversal. The direction of traversal is head-to-tail if this.reversed
   * is false, and tail-to-head otherwise.
   *
   * @return An iterator traversing the list.
   */
  @Override
  public Iterator<IceDataEntry> iterator() {

    // if reversed is false, create and return a head-to-tail Iterator, otherwise create and return
    // a tail-to-head Iterator
    if (!reversed) {
      ForwardIterator iterf = new ForwardIterator(head);
      return iterf;

    } else {
      BackwardIterator iterb = new BackwardIterator(tail);
      return iterb;
    }
  }


  /**
   * Removes all nodes with missing freeze or thaw dates
   */
  public void removeIncompleteRecords() {

    Iterator<IceDataEntry> iter = iterator(); // create a iterator

    // use the iterator to traverse the list and remove all nodes with missing freeze or thaw dates
    IceDataEntry rec = null;
    while (iter.hasNext()) {
      rec = iter.next();
      if (!rec.hasCompleteData()) {
        remove(rec);
      }
    }

  }

  /**
   * Fixes all LakeRecords contained in this list with incorrect durations (Hint: LakeRecord already
   * has a method for this!)
   */
  public void updateDurations() {

    Iterator<IceDataEntry> iter = iterator(); // create a iterator

    // use the iterator to traverse the list and fix all LakeRecords contained in this list with
    // incorrect durations
    IceDataEntry rec = null;
    while (iter.hasNext()) {
      rec = iter.next();
      rec.updateDuration();
    }

  }

  /**
   * Merges consecutive nodes containing records from the same winter. The merged node contains a
   * single record with the earliest freeze date, the latest thaw date, and the total number of days
   * of ice cover. Note that merging discards the middle thaw and freeze dates, and so this method
   * should only be used after calling updateDurations().
   *
   * <br>
   * <br>
   * Note: This method is closely related to the learning objectives of the assignment, and so we'll
   * pay special attention to it during manual grading. Be sure to leave comments explaining each
   * algorithmic step you use!
   */
  public void mergeWinters() {

    // create a node to be the current node and start from the head of the list
    DoubleNode cur = head;

    // traverse the list until current node is null
    while (cur != null) {

      // Merges consecutive nodes containing records from the same winter.
      while (cur.getNext() != null
          && cur.getLakeRecord().getWinter().equals(cur.getNext().getLakeRecord().getWinter())) {

        // get the next node with the same winter
        DoubleNode next = cur.getNext();

        // merge next's record into the current node
        cur.getLakeRecord().mergeWith(next.getLakeRecord());

        // change tail to current node if there is no node after next node
        if (next == tail) {
          tail = next.getPrev();
        }

        // remove next node by changing pointers
        cur.setNext(next.getNext());

        // if there is a node after the next node, set that node's previous pointer to current node
        if (next.getNext() != null) {
          next.getNext().setPrev(cur);
        }

        // reduce the size of list
        size--;
      }

      // move to the next winter
      cur = cur.getNext();
    }

  }

  /**
   * Returns a new linked list containing all the records falling between year1 and year 2,
   * inclusive. The returned list should not contain any references to nodes or records from the
   * original list, and the relative ordering of nodes should not change.
   *
   * @param year1 minimum allowable year for the new list
   * @param year2 maximum allowable year for the new list
   * @return a new, filtered linked list covering the given range of years.
   */
  public LakeIceAnalyzer filterByYear(int year1, int year2) {
    // list to store records falling between year1 and year2
    ArrayList<IceDataEntry> records = new ArrayList<>();

    // create a node to be the current node and start from the head of the list
    DoubleNode cur = head;

    // traverse the linked list and add all the records falling between year1 and year2 into records
    while (cur != null) {
      if (cur.getLakeRecord().getYear() >= year1 && cur.getLakeRecord().getYear() <= year2) {

        records.add(cur.getLakeRecord().copy());
      }
      cur = cur.getNext();
    }

    // create and return a FreezeTracker which has a new linked list containing all the records
    // falling between year1 and year2
    LakeIceAnalyzer rt = new LakeIceAnalyzer(records);
    return rt;
  }

  /**
   * Returns a new linked list containing all of the records from the given year. The returned list
   * should not contain any references to nodes or records from the original list, and the relative
   * ordering of nodes should not change.
   *
   * @param year the single year covered by the new list
   * @return a new linked list containing only nodes from the given year
   */
  public LakeIceAnalyzer filterByYear(int year) {
    // list to store records falling between year1 and year2
    ArrayList<IceDataEntry> records = new ArrayList<>();

    // create a node to be the current node and start from the head of the list
    DoubleNode cur = head;

    // traverse the linked list and add copies of all the records from the given year
    while (cur != null) {
      if (cur.getLakeRecord().getYear() == year) {

        records.add(cur.getLakeRecord().copy());
      }
      cur = cur.getNext();
    }

    // create and return a FreezeTracker which has a new linked list containing all the records
    // falling between year1 and year2
    LakeIceAnalyzer rt = new LakeIceAnalyzer(records);
    return rt;
  }

  /**
   * Returns a new linked list containing all of the records whose total days of ice cover are
   * between low and high, inclusive. The returned list should not contain any references to nodes
   * or records from the original list, and the relative ordering of nodes should not change.
   *
   * @param low  The minimum allowed duration for the new list
   * @param high The maximum allowed duration for the new list
   * @return a new list containing only records with duration in the given range
   */
  public LakeIceAnalyzer filterByDuration(int low, int high) {
    // list to store records falling between year1 and year2
    ArrayList<IceDataEntry> records = new ArrayList<>();

    // create a node to be the current node and start from the head of the list
    DoubleNode cur = head;

    // traverse the linked list and add copies of all the records from the given year
    while (cur != null) {
      if (cur.getLakeRecord().getDaysOfIceCover() <= high
          && cur.getLakeRecord().getDaysOfIceCover() >= low) {

        records.add(cur.getLakeRecord().copy());
      }
      cur = cur.getNext();
    }

    // create and return a FreezeTracker which has a new linked list containing all the records
    // falling between year1 and year2
    LakeIceAnalyzer rt = new LakeIceAnalyzer(records);
    return rt;
  }


  /**
   * Finds the latest date at which the lake thawed.
   * 
   * @return The date of the latest thaw, e.g. "April 15"
   */
  public String getLatestThaw() {

    Iterator<IceDataEntry> iter = iterator(); // create a iterator

    String latestDate = iter.next().getThawDate(); // stores the latest thaw date

    // use the iterator to traverse the list and compare thaw dates of each record with latestDate
    while (iter.hasNext()) {
      String date = iter.next().getThawDate(); // store thaw date of current record in date

      // if thaw date of current record is greater than latestDate, change latestDate to thaw date
      // of current record
      if (DateUtil.compareDates(date, latestDate) > 0) {
        latestDate = date;
      }
    }

    return latestDate;
  }

  /**
   * Finds the earliest date at which the lake froze.
   *
   * @return The day of the earliest freeze, e.g. "December 2"
   */
  public String getEarliestFreeze() {

    Iterator<IceDataEntry> iter = iterator(); // create a iterator

    // stores the earliest date at which the lake froze
    String earliestDate = iter.next().getFreezeDate();

    // use the iterator to traverse the list and compare freeze dates of each record with latestDate
    while (iter.hasNext()) {
      String date = iter.next().getFreezeDate(); // store freeze date of current record in date

      // if freeze date of current record is less than earliest, change earliestDate to freeze
      // date of current record
      if (DateUtil.compareDates(date, earliestDate) < 0) {
        earliestDate = date;
      }
    }

    return earliestDate;
  }

  /**
   * Finds the average (arithmetic mean) number of days of ice cover across the entire list
   *
   * @return The average number of days of ice cover across all nodes, or 0 if list is empty.
   */
  public float getAverageFreezeDuration() {

    if (head == null) {
      return 0;
    }

    Iterator<IceDataEntry> iter = iterator(); // create a iterator

    // stores the average number of days of ice cover across all nodes
    float sum = 0;

    // use the iterator to traverse the list and sum up days of ice cover of all nodes
    while (iter.hasNext()) {
      sum += iter.next().getDaysOfIceCover();
    }

    return sum / size;


  }

  /**
   * Finds the maximum number of days of ice cover across the entire list
   * 
   * @return The maximum number of days of ice cover across all nodes, or 0 if the list is empty.
   */
  public int getMaxFreezeDuration() {
    if (head == null) {
      return 0;
    }

    Iterator<IceDataEntry> iter = iterator(); // create a iterator

    // stores the maximum number of days of ice cover
    int max = iter.next().getDaysOfIceCover();

    // use the iterator to traverse the list and compare number of days of ice cover of each record
    // with max
    while (iter.hasNext()) {
      int n = iter.next().getDaysOfIceCover(); // store number of days of ice cover of current
                                               // record

      // if number of days of ice cover of current record is greater than max, change
      // max to n
      if (n > max) {
        max = n;
      }
    }

    return max;
  }

  /**
   * Finds the minimum number of days of ice cover across the entire list
   * 
   * @return The minimum number of days of ice cover across all nodes, or 0 if the list is empty.
   */
  public int getMinFreezeDuration() {
    if (head == null) {
      return 0;
    }

    Iterator<IceDataEntry> iter = iterator(); // create a iterator

    // stores the minimum number of days of ice cover
    int min = iter.next().getDaysOfIceCover();

    // use the iterator to traverse the list and compare number of days of ice cover of each record
    // with min
    while (iter.hasNext()) {
      int n = iter.next().getDaysOfIceCover(); // store number of days of ice cover of current
                                               // record

      // if number of days of ice cover of current record is less than min, change
      // min to n
      if (n < min) {
        min = n;
      }
    }

    return min;

  }

  /**
   * Creates a string representation of the tracker with each node on a new line. The order of the
   * nodes depends on whether the string is currently reversed.
   *
   * @return a String representation of the list
   */
  public String toString() {
    if (head == null) {
      return null;
    }

    Iterator<IceDataEntry> iter = iterator(); // create a iterator

    // stores the string representation of the tracker
    String s = "";

    // use the iterator to traverse the list and add string representation of each node to s
    while (iter.hasNext()) {
      s += iter.next().toString() + "\n";
    }

    return s;
  }
}
