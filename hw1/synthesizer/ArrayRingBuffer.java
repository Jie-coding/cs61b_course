// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;
import java.util.Iterator;
import java.util.Arrays;
//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> implements BoundedQueue<T>  {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    private int size;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int idxPos;
        private int loopedNum;
        public  ArrayRingBufferIterator(){
            idxPos = first;
            loopedNum = 0;
        }

        @Override
        public boolean hasNext(){
            int nextPos = idxPos + 1;
            nextPos %= capacity();
            return (rb[nextPos] != null);
        }

        @Override
        public T next(){
            if(!hasNext() || loopedNum == capacity()){
                return null;
            }
            T returnVaule = rb[idxPos];
            idxPos += 1;
            idxPos %= capacity();
            loopedNum += 1;
            return returnVaule;
        }
    }

    @Override
    public boolean equals(Object o){
        if(o == null || this.getClass() != o.getClass()) { return false;}
        if (this == o) {return true;}
        ArrayRingBuffer<T> otherObj = (ArrayRingBuffer<T>) o;
        return Arrays.equals(this.rb, otherObj.rb) && this.first == otherObj.first && this.last == otherObj.last;
    }

    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.rb = (T[]) new Object[capacity];
        this.first = 0;
        this.last = 0;
        this.size = 0;
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return size;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (isFull()){
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[last] = x;
        size += 1;
        last += 1;
        if (outOfIndex(last)) {
            last = 0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (isEmpty()){
            throw new RuntimeException("Ring Buffer underflow");
        }
        T deleted = rb[first];
        rb[first] = null;
        first += 1;
        size -= 1;
        if(outOfIndex(first)){
            first = 0;
        }
        return deleted;
    }

    private boolean outOfIndex(int m){
        return m > capacity() - 1;
    }
    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if(isEmpty()){
            throw new RuntimeException("Ring Buffer underflow");
        }
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
}

