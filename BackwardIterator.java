

import java.util.Iterator;
import java.util.NoSuchElementException;

// An iterator that traverses the linked list in the backward direction (tail to head).
public class BackwardIterator extends Object implements Iterator<IceDataEntry> {

  private DoubleNode current;

  /**
   * Constructs a backward iterator starting from the given node.
   * 
   * @param start The starting node (tail of the list).
   */
  public BackwardIterator(DoubleNode start) {
    this.current = start;
  }

  /**
   * Checks if there is a previous element in the list.
   */
  @Override
  public boolean hasNext() {
    return current != null;
  }

  /**
   * Returns the previous LakeRecord and moves the iterator backward.
   */
  @Override
  public IceDataEntry next() {
    if (!hasNext()) {
      throw new NoSuchElementException("No more elements.");
    }

    IceDataEntry data = current.getLakeRecord();
    current = current.getPrev();
    return data;
  }
}
