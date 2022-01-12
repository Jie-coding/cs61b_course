/**
 * @author Vivant Sakore on 1/29/2020
 */
public class BuggyIntDList extends IntDList {

    /**
     * @param values creates a BuggyIntDList with ints values.
     */
    public BuggyIntDList(Integer... values) {
        super(values);
    }


    /**
     * Merge IntDList `l` into the calling IntDList
     * Assume that the two IntDLists being merged are sorted individually before merge.
     * The resulting IntDList must also be sorted in ascending order.
     *
     * @param l Sorted IntDList to merge
     */
    public void mergeIntDList(IntDList l) {
        front = sortedMerge2(this.front, l.front);
    }

    /**
     * Recursively merge nodes after value comparison
     *
     * @param d1 Node 1
     * @param d2 Node 2
     * @return Nodes arranged in ascending sorted order
     */
    private DNode sortedMerge1(DNode d1, DNode d2) {
        if (d1 == null || d2 == null){
            return d1 == null ? d2 : d1;
        }
        DNode s = null;
        if (d1.val < d2.val){
            s = d1;
            d1 = d1.next;
        }
        else{
            s = d2;
            d2 = d2.next;
        }

        DNode head = s; // This is very important! Store the head pointer before merge the two lists
        while(d1 != null && d2 != null){
            if (d1.val < d2.val){
                d1.prev = s;
                s.next = d1;
                s = s.next;
                d1 = d1.next;
            }
            else {
                d2.prev = s;
                s.next = d2;
                s = s.next;
                d2 = d2.next;
            }
        }

        if (d1 == null){
            d2.prev = s;
            s.next = d2;
        }
        if (d2 == null){
            d1.prev = s;
            s.next = d1;
        }
        return head;

    }

    private DNode sortedMerge2(DNode d1, DNode d2){
        if (d1 == null || d2 == null){
            return d1 == null ? d2 : d1;
        }

        if (d1.val <= d2.val){
            d1.next = sortedMerge2(d1.next, d2);
            d1.next.prev = d1;
            d1.prev = null;
            return d1;
        }
        else {
            d2.next = sortedMerge2(d1, d2.next);
            d2.next.prev = d2;
            d2.prev = null;
            return d2;
        }

    }
    /**
     * Reverses IntDList in-place (destructive). Does not create a new IntDList.
     */
    public void reverse() {

        // FIXME: Below code has multiple problems. Debug the code to implement correct functionality.

        DNode temp = null;
        DNode p = this.front;
        DNode head = this.front;

        if (p != null && p.next != null){
            temp = p.next;
            p.next = null;
            p.prev = temp;
            p = p.prev;
        }
        else {return;}

        // HINT: What does this while loop do? Use Debugger and Java Visualizer to figure out.
        while (p.next != null) {
            temp = p.next;
            p.next = p.prev;
            p.prev = temp;
            p = p.prev;        // FIXME: Replace this line (if needed). HINT: Use debugger and Java Visualizer to figure out what it does.
        }

        // HINT: What does this if block do? Use Debugger and Java Visualizer to figure out.
        if (temp != null) {
            // ------ WRITE ADDITIONAL CODE HERE AND ONLY HERE (IF NEEDED) -----

            // -----------------------------------------------------------------
            p.next = p.prev;
            p.prev = null;
            front = temp;
            back = head; // FIXME: Replace this line (if needed). HINT: Use debugger and Java Visualizer to figure out what it does.
        }
    }
}
