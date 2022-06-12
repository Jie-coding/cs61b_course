import java.util.*;
import java.util.LinkedList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class MyHashMap<K, V> implements Map61B<K, V> {

    // The default values for size and loadFactor
    private final int defaultInitialSize = 16;
    private final double defaultLoadFactor = 0.75;

    private double loadFactor; // loadFactor = n/m
    private int n;  // number of key-value mappings
    private int m;  // number of buckets in the array

    private List<Elem> hashMap;

    private class Elem extends Object {
        K key;
        V value;
        Elem next;
        private Elem(K key, V value, Elem next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        private V get(K key) {
            if (this.key.equals(key)) {
                return this.value;
            }
            else if (this.next == null) {
                return null;
            }
            return this.next.get(key);
        }
    }
    public MyHashMap() {
        this.n = 0;
        this.m = defaultInitialSize;
        this.loadFactor = defaultLoadFactor;
        hashMap = new ArrayList<>(defaultInitialSize);
        for (int i = 0; i < defaultInitialSize; i += 1) {
            hashMap.add(i, null);
        }
    }

    public MyHashMap(int initialSize) {
        this.n = 0;
        this.m = initialSize;
        this.loadFactor = defaultLoadFactor;
        hashMap = new ArrayList<>(initialSize);
        for (int i = 0; i < initialSize; i += 1) {
            hashMap.add(i, null);
        }
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.n = 0;
        this.m = initialSize;
        this.loadFactor = loadFactor;
        hashMap = new ArrayList<>(initialSize);
        for (int i = 0; i < initialSize; i += 1) {
            hashMap.add(i, null);
        }
    }

    @Override
    public void clear(){
        n = 0;
        for (int i = 0; i < m; i += 1) {
            hashMap.set(i, null);
        }
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key){
        if (key == null) {
            throw new IllegalArgumentException("Please provide a valid key argument!");
        }
        return get(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Please provide a valid key argument!");
        }
        int index = hash(key);
        if (hashMap.get(index) != null) {
            return hashMap.get(index).get(key);
        }
        return null;
    }

    private int hash(K key) {
        int code = key.hashCode();
        return Math.abs(code % m);
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return n;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Please pass in a valid key or value argument!");
        }

        loadFactor = (double) n / m;
        if (loadFactor > 1.5) {
            resize(2 * m);
        }

        int index = hash(key);
        hashAdd(index, key, value);
    }

    private void resize(int bigCap) {
        MyHashMap<K, V> temp = new MyHashMap<>(bigCap, (double) loadFactor / 2);
        for (int i = 0; i < m; i += 1) {
            Elem cur = hashMap.get(i);
            while (cur != null) {
                temp.put(cur.key, cur.value);
                cur = cur.next;
            }
        }
        this.n = temp.n;
        this.m = temp.m;
        this.hashMap = temp.hashMap;

    }
    private void hashAdd(int index, K key, V value) {
        Elem head = hashMap.get(index);
        if ( head == null) {
            hashMap.set(index, new Elem(key, value, null));
            n += 1;
            return;
        }

        Elem cur = head;
        while (cur != null) {
            if (cur.key.equals(key)) {
                cur.value = value;
                return;
            }
            if (cur.next == null) {
                cur.next = new Elem(key, value, null);
                n += 1;
                return;
            }
            cur = cur.next;
        }
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keyset = new HashSet<K>();
        for (int i = 0; i < m; i += 1) {
            if (hashMap.get(i) != null) {
                Elem cur = hashMap.get(i);
                while (cur != null) {
                    keyset.add(cur.key);
                    cur = cur.next;
                }
            }
        }
        return keyset;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        V getValue = get(key);
        if (getValue != null) {
            removeHelper(key);
        }
        return getValue;
    }

    private void removeHelper(K key) {
        int index = hash(key);
        Elem head = hashMap.get(index);
        if (head.key.equals(key)) {
            hashMap.set(index, head.next);
            return;
        }

        Elem cur = head;
        while (cur.next != null) {
            if (cur.next.key.equals(key)){
                cur.next = cur.next.next;
                return;
            }
            cur = cur.next;
        }
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        V getValue = get(key);
        if (getValue == value) {
            removeHelper(key);
        }
        return getValue;
    }

    public Iterator<K> iterator() {
        return new HashMapIterator();
    }

    private class HashMapIterator implements Iterator<K> {
        Stack<Elem> StackHashMap;
        public HashMapIterator() {
            StackHashMap = new Stack<Elem>();
            for (Elem item : hashMap) {
                while (item != null) {
                    StackHashMap.push(item);
                    item = item.next;
                }
            }
        }
        @Override
        public boolean hasNext() {
            return !StackHashMap.isEmpty();
        }
        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Elem item = StackHashMap.pop();
            return item.key;
        }
    }

    public static void main(String[] args) {

        MyHashMap<String, Integer> map = new MyHashMap<>(10);
        map.put("Roger", 25);
        map.put("Lucy", 18);
        map.put("Mary", 40);
        map.put("Adrian", 34);
        for (String item : map) {
            System.out.println(item);
        }

    }
}
