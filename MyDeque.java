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
}