
/**
 * Instantiable class representing a single node in a doubly-linked list of LakeRecords.
 */
public class DoubleNode {

  /**
   * The LakeRecord stored in this node
   */
  private IceDataEntry data;

  /**
   * A reference to the previous node in this linked list
   */
  private DoubleNode prev;

  /**
   * A reference to the next node in this linked list
   */
  private DoubleNode next;

  /**
   * Creates a new LinkedNode with the given data, previous and next nodes
   * 
   * @param data the LakeRecord to be contained in this node
   * @param prev a reference to the previous node in this list (may be null)
   * @param next a reference to the next node in this list (may be null)
   */
  public DoubleNode(IceDataEntry data, DoubleNode prev, DoubleNode next) {
    this.data = data;
    this.prev = prev;
    this.next = next;
  }

  /**
   * Creates a new LinkedNode with the given data which is not currently linked to any other nodes
   * 
   * @param data the data to be contained in this node
   */
  public DoubleNode(IceDataEntry data) {
    this.data = data;
  }

  /**
   * Accesses the LakeRecord stored in this node
   * 
   * @return the LakeRecord stored in this node
   */
  public IceDataEntry getLakeRecord() {
    return data;
  }

  /**
   * Accesses the previous node in the list
   * 
   * @return a reference to the previous node in this list (may be null)
   */
  public DoubleNode getPrev() {
    return prev;
  }

  /**
   * Updates the previous node for this node
   * 
   * @param newPrev the new previous node in this list (may be null)
   */
  public void setPrev(DoubleNode newPrev) {
    prev = newPrev;
  }

  /**
   * Accesses the next node in the list
   * 
   * @return a reference to the next node in this list (may be null)
   */
  public DoubleNode getNext() {
    return next;
  }

  /**
   * Updates the next node for this node
   * 
   * @param newNext the new next node in this list (may be null)
   */
  public void setNext(DoubleNode newNext) {
    next = newNext;
  }
}
