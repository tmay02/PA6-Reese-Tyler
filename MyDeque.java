/**
 * Name: Tyler May, Reese Whitlock
 * ID: A16792035, A17074829
 * Email: tjmay@ucsd.edu, rwhitlock@ucsd.edu
 * Sources used: Coding done jointly
 * 
 * The purpose of MyDeque.java is to do a manual implementation of the ADT
 * Deque. It implements generic interface DequeInterface<E> held in file
 * DequeInterface.java.
 */


/**
 * Public generic class MyDeque<E> implements ADT deque using an array.
 * Instance variables include:
 *  - Object[] data - holds all data for the deque.
 *  - int size - holds the amount of non-null elements in the array.
 *  - int rear - tracks which index holds the end of the deque.
 *  - int front - tracks which index holds the front of the deque.
 */
public class MyDeque<E> implements DequeInterface<E> {
    Object[] data;
    int size;
    int rear;
    int front;
    private static final int DEFAULT_CAPACITY = 10;
    
    /**
     * Constructor for MyDeque
     * @param initialCapacity - initial size of data
     */
    public MyDeque(int initialCapacity) {
        if(initialCapacity < 0) {
            throw new IllegalArgumentException();
        }

        this.data = new Object[initialCapacity];
        this.size = 0;
        this.rear = 0;
        this.front = 0;
    }

    /**
     * @return size of deque
     */
    public int size(){
        return this.size;
    }

    /**
     * Doubles the capacity of the Object array data.
     */
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

    /**
     * Adds an element to the front of the deque
     * @param element - element to add
     */
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
            this.front = this.data.length - 1;
            this.data[this.front] = element;
            this.size++;
            return;
        }
        this.data[this.front - 1] = element;
        this.front = this.front - 1;
        this.size++;
    }

    /**
     * Adds an element to the back of the deque
     * @param element - element to add
     */
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

        if(this.rear == this.data.length - 1){
            this.rear = 0;
            this.data[this.rear] = element;
            this.size++;
            return;
        }
        this.data[this.rear + 1] = element;
        this.rear = this.rear + 1;
        this.size++;
    }

    /**
     * Removes an element from the front of the deque
     * @return element that was removed
     */
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

    /**
     * Removes an element from the back of the deque
     * @return element that was removed
     */
    @SuppressWarnings("unchecked")
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

    /**
     * @return element at the front of the deck
     */
    @SuppressWarnings("unchecked")
    public E peekFirst(){
        if(this.size() == 0) {
            return null;
        }

        return (E) this.data[front];
    }

    /**
     * @return element at the back of the deck
     */
    @SuppressWarnings("unchecked")
    public E peekLast(){
        if(this.size() == 0) {
            return null;
        }
        return (E) this.data[rear];
    }
}