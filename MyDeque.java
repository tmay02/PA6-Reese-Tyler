public class MyDeque<E> implements DequeInterface<E> {
    Object[] data;
    int size;
    int rear;
    int front;
    
    public MyDeque(int initialCapacity) {
        if(initialCapacity < 0) {
            throw new IllegalArgumentException();
        }

        data = new Object[initialCapacity];
        this.size = 0;
        this.rear = 0;
        this.front = 0;


    public int size(){

    }

    public void expandCapacity(){

    }

    public void addFirst(E element){

    }

    public void addLast(E element){

    }

    public E removeFirst(){

    }

    public E removeLast(){

    }

    public E peekFirst(){

    }

    public E peekLast(){
    }
}