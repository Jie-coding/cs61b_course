public class LinkedListDeque<T> {

    private class Node {
        public T item;
        public Node next;
        public Node prev;

        public Node (T i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node (null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;

    }

    public LinkedListDeque(T x) {
        size = 1;
        sentinel = new Node(null, null, null);
        sentinel.next = new Node(x, sentinel, sentinel);
        sentinel.prev = sentinel.next;
    }
    /*
    Add one node to the first of the deque
     */
    public void addFirst(T x) {
        size += 1;
        Node temp = sentinel.next;
        sentinel.next = new Node(x, sentinel.next, sentinel);
        temp.prev = sentinel.next;
    }
    /*
    Add one node to the last of the deque
     */
    public void addLast(T x) {
        size += 1;
        Node temp = sentinel.prev;
        sentinel.prev = new Node(x, sentinel, temp);
        temp.next = sentinel.prev;
    }

    /*
    Return the size of the LinkedListDeque
     */
    public int size() {
        return size;
    }

    /*
    Return true if deque is empty, otherwise return false
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    public T removeFirst() {
        size--;
        if (size() > 0) {
            Node temp = sentinel.next;
            sentinel.next = temp.next;
            temp.next.prev = sentinel;
            temp.next = null;
            temp.prev = null;
            return temp.item;
        }
        else {return null;}
    }

    public T removeLast() {
        size--;
        if(size() > 0) {
            Node temp = sentinel.prev;
            sentinel.prev = temp.prev;
            temp.prev.next = sentinel;
            temp.prev = null;
            temp.next = null;
            return temp.item;
        }
        else {return null;}
    }
    /*
    Print all the elements of the deque from first to last
     */
    public void printDeque() {
        Node p = sentinel;
        while(p.next != null && p.next != sentinel) {
            System.out.print(p.next.item + " ");
            p = p.next;
        }
        System.out.println();
    }
    /*
    Get the mth item from the deque
     */
    public T get(int m) {
        if (m > (size() - 1) || m < 0) {
            return null;
        }
        else {
            Node p = sentinel.next;
            while (m > 0){
                p = p.next;
                m --;
            }
            return p.item;
        }
    }

    public T getRecursive(int m) {
        if (m > (size() - 1) || m < 0) {
            return null;
        }
        else {
        return getRecursiveHelper(sentinel.next, m);
        }
    }

    private T getRecursiveHelper(Node p, int m) {
        if(m == 0){
            return p.item;
        }
        else{
            return getRecursiveHelper(p.next, m-1);
        }
    }
    public static void main(String[] args) {
        LinkedListDeque L = new LinkedListDeque(1);
        L.addFirst(2);
        L.addLast(3);
        L.addLast(4);
        L.printDeque();
        System.out.println(L.removeLast());
        L.printDeque();
    }
}
