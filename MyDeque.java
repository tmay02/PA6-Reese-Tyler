public class MyDeque<E> implements DequeInterface<E> {
    Object[] data;
    int size;
    int rear;
    int front;
    
    public MyDeque(int initialCapacity) {
        if(initialCapacity < 0) {
            throw new IllegalArgumentException();
        }

        this.data = new Object[initialCapacity];
        this.size = 0;
        this.rear = 0;
        this.front = 0;
    }

    public int size(){

    }

    public void expandCapacity(){

    }

    public void addFirst(E element){

    }

    public void addLast(E element){

    }

    public E removeFirst(){
        if(this.size() == 0) {
            return null;
        }

        E returnElement = (E) this.data[front];

        this.data[front] = null;
        this.size--;
        if(this.front == this.size()) {
            this.front = 0;
        } else {
            this.front++;
        }

        return returnElement;
    }

    public E removeLast(){
        if(this.size() == 0) {
            return null;
        }

        E returnElement = (E) this.data[rear];

        this.data[rear] = null;
        this.size--;
        if(this.rear == 0) {
            this.rear = this.size() - 1;
        } else {
            this.rear--;
        }

        return returnElement;
    }

    public E peekFirst(){
        if(this.size() == 0) {
            return null;
        }

        return (E) this.data[front];
    }

    public E peekLast(){
        if(this.size() == 0) {
            return null;
        }

        return (E) this.data[rear];
    }
}