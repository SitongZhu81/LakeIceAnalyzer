
import java.util.Iterator;
import java.util.NoSuchElementException;

// An iterator that traverses the linked list in the forward direction (head to tail).
public class ForwardIterator extends Object implements Iterator<IceDataEntry> {

  private DoubleNode current; // stores the current node during iteration

  public ForwardIterator(DoubleNode start) {
    this.current = start;
  }

  /**
   * Checks if there is a next element in the list.
   * 
   * Specified by: hasNext in interface Iterator<LakeRecord>
   * 
   * @return True if there is another element, false otherwise.
   */
  @Override
  public boolean hasNext() {
    return current != null;
  }

  /*
   * Returns the next LakeRecord and moves the iterator forward.
   * 
   * Specified by: next in interface Iterator<LakeRecord>
   * 
   * @return The next LakeRecord in the list.
   */
  @Override
  public IceDataEntry next() {
    if (!hasNext()) {
      throw new NoSuchElementException("ERROR: No next element left");
    }

    IceDataEntry rt = current.getLakeRecord();

    if (hasNext()) {
      current = current.getNext();
    }

    return rt;
  }

}
