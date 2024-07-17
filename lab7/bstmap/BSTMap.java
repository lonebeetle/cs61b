package bstmap;

import edu.princeton.cs.algs4.BST;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>,Iterable<K>{
    private int size;
    private class BSTNode{
        private BSTNode left;
        private BSTNode right;
        private V val;
        private K key;
        public BSTNode(K key, V val){
            this.val = val;
            this.key = key;
            left = right = null;
            size++;
        }
    }
    private BSTNode root;
    @Override
    public void clear() {
        size = 0;
        root = null;
    }
    public BSTNode getnode(BSTNode node, K key){
        if(node == null){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if(cmp > 0){
            return getnode(node.right, key);
        }
        else if(cmp < 0){
            return getnode(node.left, key);
        }
        else{
            return node;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return getnode(root, key) != null;
    }

    @Override
    public V get(K key) {
        BSTNode node = getnode(root, key);
        return node == null ? null : node.val;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }
    public BSTNode put(BSTNode node, K key, V val){
        if(node == null){
            node = new BSTNode(key, val);
        }
        else {
            int cmp = key.compareTo(node.key);
            if (cmp > 0) {
                node.right = put(node.right, key, val);
            } else if (cmp < 0) {
                node.left = put(node.left, key, val);
            } else {
                node.val = val;
            }
        }
        return node;
    }
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        LinkedList<BSTNode> list = new LinkedList<>();
        list.addLast(root);
        BSTNode node;
        while(!list.isEmpty()){
            node = list.removeFirst();
            if(node == null){
                continue;
            }
            list.addLast(node.left);
            list.addLast(node.right);
            set.add(node.key);
        }
        return set;
    }

    @Override
    public V remove(K key) {
        if (containsKey(key)) {
            V targetValue = get(key);
            root = remove(root, key);
            size -= 1;
            return targetValue;
        }
        return null;
    }
    public BSTNode min(BSTNode node){
        if(node == null){
            return null;
        }
       BSTNode tmp = node;
        while(tmp.left != null){
            tmp = tmp.left;
        }
        return tmp;
    }
    public BSTNode  max(BSTNode node){
        if(node == null){
            return null;
        }
        BSTNode tmp = node;
        while(tmp.right != null){
            tmp = tmp.right;
        }
        return tmp;
    }



    public BSTNode remove(BSTNode node, K key){
        if(node == null){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if(cmp > 0){
            node.right = remove(node.right, key);
        }
        else if(cmp < 0){
            node.left = remove(node.left, key);
        }
        //find the node to be removed
        else{
            if(node.left == null){
                return node.right;
            }
            if(node.right == null){
                return node.left;
            }
            BSTNode tmp = node;
            node = min(node.right);
            node.left = tmp.left;
            node.right = remove(tmp.right, node.key);
        }
        return node;

    }
    @Override
    public V remove(K key, V value) {
        if (containsKey(key)) {
            V targetValue = get(key);
            if (targetValue.equals(value)) {
                root = remove(root, key);
                size -= 1;
                return targetValue;
            }
        }
        return null;
    }
    @Override
    public Iterator<K> iterator() {
        return new BSTIterator();
    }
    public class BSTIterator implements Iterator{
        LinkedList<BSTNode> list;
        public BSTIterator(){
            list = new LinkedList<>();
            list.addLast(root);
        }
        @Override
        public boolean hasNext() {
            return !list.isEmpty();
        }

        @Override
        public K next() {
             BSTNode node = list.removeFirst();
             list.addLast(node.left);
             list.addLast(node.right);
             return node.key;
        }
    }
    public void printInOrder(){
        printInOrder(root);
    }
    public void printInOrder(BSTNode node){
        if(node != null) {
            System.out.print(node.key+" ");
            printInOrder(node.left);
            printInOrder(node.right);
        }
    }
}
