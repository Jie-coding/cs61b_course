public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int lowerBounds;
    private int upperBounds;


    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = items.length/2;
        nextLast = nextFirst + 1;
        lowerBounds = 0;
        upperBounds = items.length - 1;
    }

    public void addFirst(T x){
        size ++;
        items[nextFirst] = x;
        nextFirst = minusOne(nextFirst);
    }

    public void addLast(T x) {
        size++;
        items[nextLast] = x;
        nextLast = PlusOne(nextLast);
    }

    public T removeFirst(){
        size--;
        nextFirst = PlusOne(nextFirst);
        if (get(nextFirst) != null){
            T temp = items[nextFirst];
            items[nextFirst] = null;
            return temp;
        }
        else{return null;}
    }

    public T removeLast() {
        size--;
        nextLast = minusOne(nextLast);
        if (get(nextLast) != null) {
            T temp = items[nextLast];
            items[nextLast] = null;
            return temp;
        } else {
            return null;
        }
    }
    private int minusOne(int index) {
        index -= 1;
        if (index < lowerBounds ){
            index = upperBounds;
        }
        return index;
    }

    private int PlusOne(int index){
        index += 1;
        if (index > upperBounds) {
            index = lowerBounds;
        }
        return index;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public T get(int index) {
        if (index >= items.length){
            return null;
        }
        else{
            return items[index];
        }
    }

    public void printDeque() {
        for(int i=0; i < items.length ; i++) {
            if(items[i]!=null) {
                System.out.print(items[i] + " ");
            }
        }
        System.out.println();
    }
    public static void main(String[] args){
        ArrayDeque A = new ArrayDeque();
        A.addLast('a');
        A.addLast('b');
        A.addFirst('c');
        A.addLast('d');
        A.addLast('e');
        A.addFirst('f');


        A.removeFirst();
        A.removeLast();
        A.removeLast();
        A.removeFirst();
        A.removeLast();
        A.printDeque();
    }
}
