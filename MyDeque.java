public class MyDeque<E> implements DequeInterface<E> {
    Object[] data;
    int size;
    int rear;
    int front;
    private static final int DEFAULT_CAPACITY = 10;
    
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
        return this.size;
    }

    public void expandCapacity(){
        if(this.data.length == 0){
            this.data = new Object[DEFAULT_CAPACITY];
            return;
        }
        Object[] newData = new Object[this.data.length * 2];

        int k = this.front;
        for(int i = 0; i < this.size(); i++){
            if(k > this.data.length - 1){
                k = 0;
            }
            newData[i] = this.data[k];
            k++;
        }

        this.front = 0;
        if(this.size() == 0){
            this.rear = 0;
            this.data = newData;
            return;
        }
        this.rear = this.size() - 1;
        this.data = newData;
    }

    public void addFirst(E element){
        if(element == null){
            throw new NullPointerException();
        }

        if(data.length == this.size()){
            this.expandCapacity();
        }

        if(this.size() == 0) {
            this.data[this.front] = element;
            this.size++;
            return;
        }

        if(this.front == 0){
            this.front = this.size() - 1;
            this.data[this.front] = element;
            this.size++;
            return;
        }
        this.data[this.front - 1] = element;
        this.front = this.front - 1;
        this.size++;
    }

    public void addLast(E element){
        if(element == null){
            throw new NullPointerException();
        }

        if(this.data.length == this.size()){
            this.expandCapacity();
        }

        if(this.size() == 0) {
            this.data[this.rear] = element;
            this.size++;
            return;
        }

        if(this.rear == this.size() - 1){
            this.rear = 0;
            this.data[this.rear] = element;
            this.size++;
            return;
        }
        this.data[this.rear + 1] = element;
        this.rear = this.rear + 1;
        this.size++;
    }

    @SuppressWarnings("unchecked")
    public E removeFirst(){
        if(this.size() == 0) {
            return null;
        }

        E returnElement = (E) this.data[front];

        this.data[front] = null;
        this.size--;
        if(this.front == this.data.length - 1) {
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
            this.rear = this.data.length - 1;
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