package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>,Iterable<T>{
    protected T[] Item;
    protected int space;
    protected int size;
    protected int front;
    protected int back;

    public ArrayDeque() {
        space = 8;
        Item = (T[]) new Object[space];
        front = 0;
        back = 0;
        size = 0;
    }

    public void addFirst(T item) {
        if (size == space) {
            resize();
        }
        if (size == 0) {
            Item[front] = item;
            size++;
        } else {
            front = (front + space - 1) % space;
            Item[front] = item;
            size++;
        }
    }

    public void addLast(T item) {
        if (size == space) {
            resize();
        }
        if (size == 0) {
            Item[back] = item;
            size++;
        } else {
            back = (back + space + 1) % space;
            Item[back] =item;
            size++;
        }
    }
    public int size() {
        return size;
    }
    public int space() {
        return space;
    }

    public void printDeque() {
        for (int p = front; p == back; p = (++p) % space) {
            System.out.print(Item[p] + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            T tmp = Item[front];
            Item[front] = null;
            size--;
            return tmp;
        }
        T tmp = Item[front];
        Item[front] = null;
        front = (front + 1 + space) % space;
        size--;
        if (size * 4 < space && space>=16) {
            resize();
        }
        return tmp;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            T tmp = Item[back];
            Item[back] = null;
            size--;
            return tmp;
        }
        T tmp = Item[back];
        Item[back] = null;
        back = (back - 1 + space) % space;
        size--;
        if (size * 4 < space && space>=16) {
            resize();
        }
        return tmp;
    }

    public T get(int index) {
        if (size == 0) {
            return null;
        }
        return Item[(front + index) % space];
    }

    public void resize() {
        if (size == space) {
            int s = space;
            space *= 2;
            T[] NewItem = (T[]) new Object[space];
            for (int p = front, i = 0; p != back; p = (p + 1) % s, i++) {
                NewItem[i] = Item[p];
            }
            NewItem[s-1] = Item[back];
            Item = NewItem;
            front=0;
            back=size-1;
        } else {
            int s = space;
            space /= 2;
            T[] NewItem = (T[]) new Object[space];
            for (int p = front, i = 0; p != back; p = (p + 1) % s, i++) {
                NewItem[i] = Item[p];
            }

            NewItem[size()-1]=Item[back];
            front = 0;
            back = size() - 1;
            Item = NewItem;
        }

    }
    public void resize(int capacity){
        int s=space;
        T[] newitem = (T[]) new Object[capacity];
        space=capacity;
        for (int p = front, i = 0; p != back; p = (p + 1) % s, i++) {
            newitem[i] = Item[p];
        }
        front = 0;
        back = size() - 1;
        newitem[size()-1]=Item[back];
        Item = newitem;

    }
    public Iterator<T> iterator(){
        return new ArrayIterator();
    }
    public class ArrayIterator implements Iterator{
        private int index;
        ArrayIterator(){
            index = 0;
        }

        @Override
        public T next(){
            return get(index++);
        }

        @Override
        public boolean hasNext() {
            return index<size;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if(obj==null){
            return false;
        }
        if(this.getClass()!=obj.getClass()){
            return false;
        }
        ArrayDeque<T> o=(ArrayDeque<T>) obj;
        if(o.size()!=this.size()){
            return false;
        }
        for(int p=front,p1=o.front;p!=back;p=(p+1+space)%space,p1=(p1+1+o.space)%o.space){
            if(Item[p]!=o.Item[p1]){
                return false;
            }
        }
        if(Item[back]!=o.Item[o.back]){
            return false;
        }
        return true;
    }
}

