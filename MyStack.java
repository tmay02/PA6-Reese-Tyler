/**
 * TODO: Add your file header
 * Name:
 * Email:
 * Sources used: Put "None" if you did not have any external help
 * 
 * This file contains a MyStack class, which is an implementation for the Stack
 * ADT based on MyDeque. Elements can be added/removed from the queue in a LIFO
 * ordering.
 */

/**
 * This class implements the Stack ADT using a MyDeque instance variable called
 * theStack.
 */
public class MyStack<E> implements StackInterface<E> {
    MyDeque<E> theStack;

    /**
     * Constructor to create new MyStack that holds a MyDeque.
     * 
     * @param capacity The max amount of elements this data structure can hold.
     */
    public MyStack(int capacity) {
        // TODO: Add your implementation here
    }

    /**
     * Checks whether or not the stack is empty.
     * 
     * @return True if there are no elements in the stack, false otherwise.
     */
    @Override
    public boolean empty() {
        // TODO: Add your implementation here
        return false;
    }

    /**
     * Adds the specified element to the top of this StackInterface.
     * 
     * @param element the element to add to the stack
     */
    @Override
    public void push(E element) {
        // TODO: Add your implementation here
    }

    /**
     * Removes the element at the top of this StackInterface.
     * Returns the element removed, or null if there was no such element.
     * 
     * @return the element removed, or null if the size was zero.
     */
    @Override
    public E pop() {
        // TODO: Add your implementation here
        return null;
    }

    /**
     * Returns the element at the top of this stack, or null if there was no
     * such element.
     * 
     * @return the element at the top, or null if the size was zero
     */
    @Override
    public E peek() {
        // TODO: Add your implementation here
        return null;
    }

    /**
     * Returns the number of elements in this stack.
     * 
     * @return the number of elements in this stack.
     */
    @Override
    public int size() {
        return theStack.size();
    }

}
