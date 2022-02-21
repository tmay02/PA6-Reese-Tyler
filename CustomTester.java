/**
 * TODO: Add your file header
 * Name:
 * ID:
 * Email:
 * Sources used: Put "None" if you did not have any external help
 * Some example of sources used would be Tutors, Zybooks, and Lecture Slides
 * 
 * 2-4 sentence file description here
 */

import org.junit.*;
import static org.junit.Assert.*;

/**
 * TODO: Add your class header
 * 
 * IMPORTANT: Do not change the method names and points are awarded
 * only if your test cases cover cases that the public tester file
 * does not take into account.
 */
public class CustomTester {
    // ----------------MyDeque class----------------
    /**
     * Test the constructor when [TODO: fill in a possible edge case here]
     */
    @Test
    public void testMyDequeConstructor() {

    }

    /**
     * Test the expandCapacity method when [TODO]
     */
    @Test
    public void testMyDequeExpandCapacity() {
        MyDeque<Integer> deque = new MyDeque<>(2);
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(3);
        assertEquals(4, deque.data.length);
        assertEquals(3, deque.size());
    }

    /**
     * Test the addFirst method when [TODO]`
     */
    @Test
    public void testAddFirst() {
        MyDeque<Integer> deque = new MyDeque<>(2);
        deque.addFirst(1);
        assertEquals(Integer.valueOf(1), deque.peekFirst());
        deque.addFirst(2);
        assertEquals(Integer.valueOf(2), deque.peekFirst());
        assertEquals(Integer.valueOf(1), deque.peekLast());
        deque.addLast(3);
        assertEquals(Integer.valueOf(3), deque.peekLast());
    }

    /**
     * Test the addLast method when [TODO]
     */
    @Test
    public void testAddLast() {

    }

    /**
     * Test the removeFirst method when [TODO]
     */
    @Test
    public void testRemoveFirst() {

    }

    /**
     * Test the removeLast method when [TODO]
     */
    @Test
    public void testRemoveLast() {

    }

    /**
     * Test the peekFirst method when [TODO]
     */
    @Test
    public void testPeekFirst(){

    }

    /**
     * Test the peekLast method when [TODO]
     */
    @Test
    public void testPeekLast(){

    }

    // ----------------MyStack class----------------
    /**
     * Test MyStack when [TODO]
     */
    @Test
    public void testMyStack(){
        // You can test any method from MyStack or a combination of methods
    }

    // ----------------MyQueue class----------------
    /**
     * Test MyQueue when [TODO]
     */
    @Test
    public void testMyQueue(){
        // You can test any method from MyQueue or a combination of methods
    }
}
