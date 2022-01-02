/* This kind of data structure is similar to a linked list */
public class SLList {
    /* By 'private', it means this class definition should
    NEVER be used by another class */
    private static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n){
            item = i;
            next = n;
        }
    }

    /** The sentinel node should never be modified */
    private IntNode sentinel;
    private int size;

    /** This constructor creates an empty list */
    public SLList(){
        size = 0;
        sentinel = new IntNode(0, null);
    }

    public SLList(int i){
        sentinel = new IntNode(0, null);
        sentinel.next = new IntNode(i, null);
        size = 1;
    }
    /** We can write different constructors, but with different arguments */
    public SLList(int[] IntArray){
        size = IntArray.length;
        sentinel = new IntNode(0, null);
        IntNode p = sentinel;

        for (int i=0; i < size; i++){
            p.next = new IntNode(IntArray[i], null);
            p = p.next;
        }
    }

    public void addAdjacent(){
        IntNode p = sentinel;
        int len = size;
        for (int i = 0; i < size -2; i++){
            if (p.next.item == p.next.next.item){
                p.next.next.item = p.next.next.item * 2;
                p.next = p.next.next;
                size -= 1;
            }
            p = p.next;
        }
    }

    public int getFirst() {
        return sentinel.next.item;
    }

    /** Add an integer x to the front of the list
     * add another IntNode to the front of the current INtNode
     * @param x
     * the integer you would like to add */
    public void addFirst(int x){
        sentinel.next = new IntNode(x, sentinel.next);
        size +=1;
    }

    public void addLastI(int x){
        /** First scan the pointer to the end. Sentinel node should never be changed */

        IntNode p = sentinel;
        while (p.next != null){
            p = p.next;
        }
        p.next = new IntNode(x, null);
        size +=1;
    }

    public void addSquare(int x){
        IntNode p = sentinel.next;
        while (p.next != null){
            p.next = new IntNode(p.item * p.item, p.next);
            p = p.next.next;
        }
        IntNode InsertNode = new IntNode(x, null);
        p.next = new IntNode(p.item * p.item, InsertNode);
        size = size*2 +1;
    }

    public int size(){
        return size;
    }

    public void deleteFirst(){
        if (size ==1){
            sentinel.next = null;
            size -=1;
            return;
        }
        sentinel.next = sentinel.next.next;
        sentinel.next.next = null;
        size -=1;
    }

    public static void main(String[] args){
        int[] a = new int[]{1,2};
        SLList Y = new SLList(a);
        Y.addSquare(5);
        Y.addSquare(7);
        System.out.println(Y.size());


    }
}
