package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    ArrayList<PriorityNode> heap;
    /*
    If we implement a TreeMap, the key must be comparable; instead, HashMap does not
    require the key to be comparable.
     */
    HashMap<T, Integer> itemMap;

    private class PriorityNode {
        private T item;
        private double priority;

        private PriorityNode(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        private double getPriority() {
            return this.priority;
        }

        private T getItem() {
            return this.item;
        }

        private void setPriority(double priority) {
            this.priority = priority;
        }
    }

    public ArrayHeapMinPQ () {
        heap = new ArrayList<>();
        heap.add(null);  // We put the items starting from index 1.
        itemMap = new HashMap<>();
    }

    private int parent(int k) {
        return k / 2;
    }

    private int leftChild(int k) {
        return 2 * k;
    }

    private int rightChild(int k) {
        return 2 * k + 1;
    }

    @Override
    public void add(T item, double priority) {
        if(contains(item)) {
            throw new IllegalArgumentException(String.format("Item %s already exists in the heap!", item));
        }
        PriorityNode PNode = new PriorityNode(item, priority);
        heap.add(PNode);
        int index = heap.indexOf(PNode);
        itemMap.put(PNode.getItem(), index);
        swim(index);
    }

    private void swim(int k) {
        while(parent(k) >= 1 && heap.get(parent(k)).getPriority() > heap.get(k).getPriority()) {
            swap(parent(k), k);
            k = parent(k);
        }
    }

    private void swap(int a, int b) {
        PriorityNode aNode = heap.get(a);
        PriorityNode bNode = heap.get(b);
        heap.set(a, bNode);
        heap.set(b, aNode);
        itemMap.put(aNode.getItem(), b);
        itemMap.put(bNode.getItem(), a);
    }
    @Override
    public boolean contains(T item) {
        return itemMap.containsKey(item);
    }

    @Override
    public T getSmallest() {
        return heap.get(1).getItem();
    }

    @Override
    public T removeSmallest() {
        T smallest = getSmallest();
        heap.set(1, heap.get(heap.size() - 1));
        itemMap.remove(smallest);
        sink(1);
        heap.remove(heap.size() - 1);
        return smallest;
    }

    private void sink(int k ) {
        int smaller = smallerChild(k);
        if (heap.get(smaller).getPriority() < heap.get(k).getPriority()) {
            swap(smaller, k);
            sink(smaller);
        }
    }

    private int smallerChild(int k) {
        int left = leftChild(k) < heap.size()? leftChild(k) : k;
        int right = rightChild(k) < heap.size()? rightChild(k) : left;
        return heap.get(right).getPriority() < heap.get(left).getPriority()? right: left;
    }

    @Override
    public int size() {
        return heap.size() - 1;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException(String.format("Item %s does not exist in the heap!", item));
        }
        int idx = itemMap.get(item);
        heap.get(idx).setPriority(priority);
        if (parent(idx) >= 1 && heap.get(parent(idx)).getPriority() > heap.get(idx).getPriority()) {
            swim(idx);
        }
        if (smallerChild(idx) < heap.size() && heap.get(smallerChild(idx)).getPriority() < heap.get(idx).getPriority()) {
            sink(idx);
        }
    }

}

