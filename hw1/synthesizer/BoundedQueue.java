package synthesizer;
import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T>{
    int capacity(); // returns size of the buffer
    int fillCount(); // returns number of items currently in the buffer
    void enqueue(T x); // add item x to the end of the queue
    T dequeue(); // delete and return item from the front of the queue
    T peek(); // return but not delete item from the front of the queue

    Iterator<T> iterator();
    default boolean isEmpty(){
        if (fillCount() == 0){
            return true;
        }
        return false;
    }

    default boolean isFull() {
        if(fillCount() == capacity()){
            return true;
        }
        return false;
    }
}
