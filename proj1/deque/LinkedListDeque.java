package deque;
import java.util.Iterator;

import static org.junit.Assert.assertTrue;

public class LinkedListDeque<T> implements Deque<T>{
    private Node head;
    private Node tail;
    private int size;
    public LinkedListDeque(){head=null;tail=null;size=0;}
    public class Node{
        public T val;
        public Node prev;
        public Node next;
        public Node(T val, Node prev, Node next ){
            this.val=val;
            this.prev=prev;
            this.next=next;
        }
    }

    public void addFirst(T item){
        if(size==0){
            head=new Node(item,null,null);
            tail=head;
        }
        else if(size==1){
            head=new Node(item,null,tail);
            tail.prev = head;
        }
        else{
            head.prev=new Node(item,null,head);
            head=head.prev;
        }
        size++;
    }
    public void addLast(T item){
        if(size==0){
            head=new Node(item,null,null);
            tail=head;
        }
        else if(size==1){
            tail=new Node(item,head,null);
            head.next = tail;
        }
        else{
            tail.next=new Node(item,tail,null);
            tail=tail.next;
        }
        size++;
    }
    public int size() {
        return size;
    }

    public void printDeque() {
        Node p=head;
        while(p!=null){
            System.out.print(p.val+" ");
            p=p.next;
        }
        System.out.println();
    }
    public T removeFirst(){
        if(isEmpty()){
            return null;
        }
        T p = head.val;
        head = head.next;
        size--;
        return p;
    }
    public T removeLast(){
        if(isEmpty()){
            return null;
        }
        T p=tail.val;
        tail=tail.prev;
        size--;
        return p;
    }
    public T get(int index){
        int i=0;
        Node p=head;
        while(i!=index){
            i++;
            p=p.next;
        }
        return p.val;
    }
    public T getRecursiveHelper(int index,Node p){
        if(index==0){
            return p.val;
        }
        else{
            return getRecursiveHelper(--index,p.next);
        }
    }


    public T getRecursive(int index){
        return getRecursiveHelper(index,head);
    }
    public Iterator<T> iterator(){
        return  new LLDIteration();
    }
    private class LLDIteration implements Iterator{
        private Node sentinal;
        public LLDIteration(){
            sentinal=head;
        }
        public T next(){
            Node p=sentinal.next;
            sentinal=sentinal.next;
            return p.val;
        }
        public boolean hasNext(){
            return sentinal.next==null;
        }
    }
    public boolean equals(Object o){
        if(this==o){
            return true;
        }
        if(o==null){
            return false;
        }
        if(this.getClass()!=o.getClass()){
            return false;
        }
        LinkedListDeque<T> o1=(LinkedListDeque<T>) o;
        if(this.size()!=o1.size()){
            return false;
        }
        Node p=this.head;
        Node p1=o1.head;
        while(p!=null){
            if(p.val!=p1.val){
                return false;
            }
        }
        return true;
    }

}
