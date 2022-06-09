import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;

    public class Node {
        private Node left, right;
        private int size;
        private K key;
        private V value;

        public Node(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
            this.left = null;
            this.right = null;
        }
    }
    @Override
    public void clear() {
        root = null;
    }

    private void checkKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Please pass in a valid key argument!");
        }
    }
    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        checkKey(key);
        return get(key) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        checkKey(key);
        return get(key, root);
    }

    /*
    A recursive method to find the value corresponding to the key.
    Base case is either null or x.value
     */
    private V get(K key, Node x) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(key, x.left);
        }
        else if (cmp > 0) {
            return get(key, x.right);
        }
        else {
            return x.value;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        checkKey(key);
        root = put(key, value, root);
    }

    private Node put(K key, V value, Node x){
        if(x == null) {
            return new Node(key, value, 1);
        }
        int cmp = key.compareTo(x.key);
        if(cmp < 0) {
            x.left = put(key, value, x.left);
        }
        else if(cmp > 0) {
            x.right = put(key, value, x.right);
        }
        else {
            x.value = value;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new TreeSet<K>();
        keySet(root, keySet);
        return keySet;
    }

    private void keySet(Node x, Set<K> set){
        if(x == null) {
            return;
        }
        keySet(x.left, set);
        set.add(x.key);
        keySet(x.right, set);
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        checkKey(key);
        V getValue = get(key);
        if(getValue != null) {
            root = remove(key, root);
        }
        return getValue;
    }

    private Node remove(K key, Node x) {
        if(x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if(cmp < 0) {
            x.left = remove(key, x.left);
        }
        else if(cmp > 0) {
            x.right = remove(key, x.right);
        }
        else {
            // Node with zero or one child
            if(x.left == null) {
                return x.right;
            }
            else if(x.right == null) {
                return x.left;
            }
            // Node with two children
            Node t = MinNode(x.right);
            x.key = t.key;
            x.value = t.value;
            x.right = remove(x.key, x.right);
        }
        x.size = 1 + size(x.right) + size(x.left);
        return x;
    }

    private Node MinNode(Node x) {
        while(x.left != null) {
            x = x.left;
        }
        return x;
    }
    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        checkKey(key);
        V getValue = get(key);
        if(getValue != value) {
            root = remove(key, root);
        }
        return getValue;
    }

    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
        private Stack<Node> iteratorStack;

        public BSTMapIterator() {
            iteratorStack = new Stack<>();
            moveLeft(root);
        }

        @Override
        public boolean hasNext() {
            return !iteratorStack.isEmpty();
        }

        private void moveLeft(Node x) {
            while(x != null) {
                iteratorStack.push(x);
                x = x.left;
            }
        }

        @Override
        public K next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            Node cur = iteratorStack.pop();
            moveLeft(cur.right);
            return cur.key;
        }
    }

    public void printInOrder() {
        Iterator<K> itr = new BSTMapIterator();
        while(itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
        System.out.println("\n" + "-----------------------");
    }
}