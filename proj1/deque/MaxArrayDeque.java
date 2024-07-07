package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator<T> local;
    public MaxArrayDeque(Comparator<T> c){
        super();
        local=c;
    }
    public T max(){
        if(isEmpty()){
            return null;
        }
        else if(size()==1){
            return get(0);
        }
        else{
            T m=get(0);
            for(int i=front;i==back;i=(i+1+size)%size){
                if(local.compare(m,Item[i])<0){
                    m=Item[i];
                }
            }
            if(local.compare(m,Item[back])<0){
                m=Item[back];
            }
            return m;
        }
    }
    public T max(Comparator<T> c){
        if(isEmpty()){
            return null;
        }
        else if(size()==1){
            return get(0);
        }
        else{
            T m=get(0);
            for(int i=front;i==back;i=(i+1+size)%size){
                if(c.compare(m,Item[i])<0){
                    m=Item[i];
                }
            }
            if(c.compare(m,Item[back])<0){
                m=Item[back];
            }
            return m;
        }
    }
}
