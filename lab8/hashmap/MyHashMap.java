package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    @Override
    public Iterator<K> iterator() {
        return new MhmIterator();
    }
    public class MhmIterator implements Iterator{
        private Queue<K> myque;
        public MhmIterator(){
            myque = new LinkedList<>();
            for(Collection<Node> items : buckets){
                for(Node n : items){
                    myque.add(n.key);
                }
            }
        }
        @Override
        public boolean hasNext() {
            return myque.size() != 0;
        }

        @Override
        public K next() {
            return myque.poll();
        }
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int size, space;
    private double maxLoad;
    /** Constructors */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = new Collection[initialSize];
        size = 0;
        space = initialSize;
        this.maxLoad = maxLoad;
        for (int i = 0; i < initialSize; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear(){
        size = 0;
        buckets = null;
        space = 0;

    }

    @Override
    public boolean containsKey(K key) {
        if(get(key) != null){
            return true;
        }
        return false;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }
    public Node getNode(K key){
        if(size == 0){
            return null;
        }
        int index = Math.floorMod(key.hashCode(),buckets.length);
        for(Node n : buckets[index]){
            if(key.equals(n.key)){
                return n;
            }
        }
        return null;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
        } else {
            Node elem = createNode(key, value);
            buckets[Math.floorMod(key.hashCode(), space)].add(elem);
            size += 1;
            if (size / space > maxLoad) {
                resize(space * 2);
            }
        }
    }
    private void resize(int capacity){
        Collection<Node>[] newbuckets = new Collection[capacity];
        for(int i = 0; i < capacity; i++){
            newbuckets[i] = createBucket();
        }
        for(int i = 0; i < buckets.length; i++){
            for(Node n : buckets[i]){
                int index = Math.floorMod(n.key.hashCode(),capacity);
                newbuckets[index].add(n);
            }
        }
        buckets = newbuckets;
    }
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for(Collection<Node> items : buckets){
            for(Node n : items){
                set.add(n.key);
            }
        }
        return set;
    }

    @Override
    public V remove(K key) {
        Node node = getNode(key);
        if(node == null){
            return null;
        }
        else{
        buckets[Math.floorMod(key.hashCode(), space)].remove(node);
        return node.value;
        }
    }



    @Override
    public V remove(K key, V value) {
        return null;
    }
}
