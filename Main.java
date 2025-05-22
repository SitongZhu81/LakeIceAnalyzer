public class Main{
    public static void main(String[] args) {
        // Create some sample IceDataEntry objects
        IceDataEntry entry1 = new IceDataEntry("Winter 2021", "2021-12-01", "2022-03-01", 90);
        IceDataEntry entry2 = new IceDataEntry("Winter 2022", "2022-12-05", "2023-03-10", 95);

        // Create DoubleNode linked list nodes
        DoubleNode node1 = new DoubleNode(entry1);
        DoubleNode node2 = new DoubleNode(entry2);
        node1.setNext(node2);
        node2.setPrev(node1);

        // Use ForwardIterator to traverse the linked list
        ForwardIterator iterator = new ForwardIterator(node1);
        while (iterator.hasNext()) {
            IceDataEntry current = iterator.next();
            System.out.println(current);
        }
    }
}
