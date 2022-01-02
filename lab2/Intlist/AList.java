/** Array based list.
 *  @author Josh Hug
 */

public class AList<arrayType> {
    private arrayType[] item;
    private int size;

    /** Creates an empty list. */
    public AList() {
        item = (arrayType[]) new Object[100];
        size = 0;
    }

    public AList(arrayType x){
        item = (arrayType[]) new Object[100];
        item[0] = x;
        size = 1;
    }

    private void resizeL(int capacity){
        arrayType[] a = (arrayType[]) new Object[capacity];
        System.arraycopy(item, 0, a, 0, size);
        item = a;
    }

    /** Inserts X into the back of the list. */
    public void addLast(arrayType x) {
        if (size == item.length){
            int factor = 2;
            resizeL(size* factor);
        }
        item[size] = x;
        size += 1;
    }
    private void resizeF(int capacity){
        arrayType[] a = (arrayType[]) new Object[capacity];
        System.arraycopy(item,0, a, 1, size);
        item = a;
    }
    public void addFirst(arrayType x){
        if (size == item.length){
            resizeF(size *2);
        }
        else {resizeF(item.length);}
        item[0] = x;
        size += 1;
    }

    /** Returns the item from the back of the list. */
    public arrayType getLast() {
        return item[size -1];
    }
    /** Gets the ith item in the list (0 is the front). */
    public arrayType get(int i) {
        return item[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public arrayType removeLast() {
        arrayType lastEle = item[size - 1];
        item[size - 1] = null;
        size -= 1;
        return lastEle;
    }

    public static void main(String[] args){
        AList<String> A1 = new AList<>("I");
        A1.addLast("Will");
        A1.addLast("get");
        A1.addLast("a");
        A1.addFirst("good");
        A1.addFirst("job");
        System.out.println(A1.size);
        System.out.println(A1.get(1));
    }
}
