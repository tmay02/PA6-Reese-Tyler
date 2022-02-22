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
     * Test the constructor when initialCapacity is less than 0
     */
    @Test
    public void testMyDequeConstructor() {
        boolean testFail = false;
        try{
            MyDeque<Integer> d = new MyDeque<>(-1);
        } catch (IllegalArgumentException E) {
            testFail = true;
        }
        assertTrue(testFail);
    }

    /**
     * Test the expandCapacity method when elements are reordered
     */
    @Test
    public void testMyDequeExpandCapacity() {
        MyDeque<Integer> deque = new MyDeque<>(2);
        Integer[] nums = {2, 1};
        Integer[] expected = {1, 2};
        deque.data = nums;
        deque.front = 1;
        deque.rear = 0;
        deque.size = 2;
        deque.expandCapacity();
        assertEquals(4, deque.data.length);
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], deque.data[i]);
        }
        assertEquals(2, deque.size());
        deque.expandCapacity();
        assertEquals(8, deque.data.length);
        Integer[] nums2 = {1, 2, null, null, null, null, 4, 3};
        Integer[] expected2 = {4, 3, 1, 2, null, null, null, null};
        deque.data = nums2;
        deque.front = 6;
        deque.rear = 1;
        deque.size = 4;
        deque.expandCapacity();
        assertEquals(16, deque.data.length);
        for(int i = 0; i < expected2.length; i++) {
            assertEquals(expected2[i], deque.data[i]);
        }
    }

    /**
     * Test the addFirst method when front is moved to the back of the array,
     * when a null element is added, and when the capacity must be expanded.
     */
    @Test
    public void testAddFirst() {
        MyDeque<Integer> deque = new MyDeque<>(4);
        Integer[] nums = {1, 2, null, null};
        Integer[] expected = {1, 2, null, 3};
        deque.data = nums;
        deque.front = 0;
        deque.rear = 1;
        deque.size = 2;
        deque.addFirst(3);
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], deque.data[i]);
        }
        assertEquals(3, deque.size());
        assertEquals(3, deque.front);
        assertEquals(1, deque.rear);
        assertEquals(4, deque.data.length);

        boolean logic = false;
        try {
            deque.addFirst(null);
        } catch(NullPointerException E) {
            logic = true;
        }
        assertTrue(logic);

        deque.addFirst(4);
        Integer[] expected2 = {1, 2, 4, 3};
        for(int i = 0; i < expected2.length; i++) {
            assertEquals(expected2[i], deque.data[i]);
        }
        assertEquals(4, deque.size());
        assertEquals(2, deque.front);
        assertEquals(1, deque.rear);
        assertEquals(4, deque.data.length);

        deque.addFirst(5);
        Integer[] expected3 = {4, 3, 1, 2, null, null, null, 5};
        for(int i = 0; i < expected3.length; i++) {
            assertEquals(expected3[i], deque.data[i]);
        }
        assertEquals(5, deque.size());
        assertEquals(7, deque.front);
        assertEquals(3, deque.rear);
        assertEquals(8, deque.data.length);
    }

    /**
     * Test the addLast method when rear is moved to the front of the array,
     * and when a null element is added, and when the capacity must be expanded.
     */
    @Test
    public void testAddLast() {
        MyDeque<Integer> deque = new MyDeque<>(4);
        Integer[] nums = {null, null, 1, 2};
        Integer[] expected = {3, null, 1, 2};
        deque.data = nums;
        deque.front = 2;
        deque.rear = 3;
        deque.size = 2;
        deque.addLast(3);
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], deque.data[i]);
        }
        assertEquals(3, deque.size());
        assertEquals(2, deque.front);
        assertEquals(0, deque.rear);
        assertEquals(4, deque.data.length);

        boolean logic = false;
        try {
            deque.addLast(null);
        } catch(NullPointerException E) {
            logic = true;
        }
        assertTrue(logic);

        deque.addLast(4);
        Integer[] expected2 = {3, 4, 1, 2};
        for(int i = 0; i < expected2.length; i++) {
            assertEquals(expected2[i], deque.data[i]);
        }
        assertEquals(4, deque.size());
        assertEquals(2, deque.front);
        assertEquals(1, deque.rear);
        assertEquals(4, deque.data.length);

        deque.addLast(5);
        Integer[] expected3 = {1, 2, 3, 4, 5, null, null, null};
        for(int i = 0; i < expected3.length; i++) {
            assertEquals(expected3[i], deque.data[i]);
        }
        assertEquals(5, deque.size());
        assertEquals(0, deque.front);
        assertEquals(4, deque.rear);
        assertEquals(8, deque.data.length);
    }

    /**
     * Test the removeFirst method when elements loop from one end of
     * the array to another and first is at the end
     */
    @Test
    public void testRemoveFirst() {
        MyDeque<Integer> d = new MyDeque<>(7);
        Integer[] nums = {2,3,null,null,null,null,1};
        Integer[] expected = {2,3,null,null,null,null,null};
        d.front = 6;
        d.rear = 1;
        d.data = nums;
        d.size = 4;

        assertEquals(Integer.valueOf(1), d.removeFirst());

        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], d.data[i]);
        }
        assertEquals(3, d.size);
        assertEquals(1, d.rear);
        assertEquals(0, d.front);
        
    }

    /**
     * Test the removeLast method when elements loop from one end of
     * the array to another and rear is at index 0
     */
    @Test
    public void testRemoveLast() {
        MyDeque<Integer> d = new MyDeque<>(7);
        Integer[] nums = {3,null,null,null,null,1,2};
        Integer[] expected = {null,null,null,null,null,1,2};
        d.front = 5;
        d.rear = 0;
        d.data = nums;
        d.size = 3;

        assertEquals(Integer.valueOf(3), d.removeLast());

        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], d.data[i]);
        }
        assertEquals(2, d.size);
        assertEquals(6, d.rear);
        assertEquals(5, d.front);
    }

    /**
     * Test the peekFirst method when size is 0
     */
    @Test
    public void testPeekFirst(){
        MyDeque<Integer> d = new MyDeque<>(100);
        d.size = 0;

        assertEquals(null, d.peekFirst());
    }

    /**
     * Test the peekLast method when size is 0
     */
    @Test
    public void testPeekLast(){
        MyDeque<Integer> d = new MyDeque<>(100);
        d.size = 0;

        assertEquals(null, d.peekLast());
    }

    // ----------------MyStack class----------------
    /**
     * Test MyStack when capacity must be expanded.
     */
    @Test
    public void testMyStack(){
        MyStack<Integer> stack = new MyStack<>(1);
        assertTrue(stack.empty());
        stack.push(1);
        assertEquals(1, stack.size());
        stack.push(2);
        assertEquals(Integer.valueOf(2), stack.peek());
    }

    // ----------------MyQueue class----------------
    /**
     * Test MyQueue when the queue is initially empty
     */
    @Test
    public void testMyQueue(){
        MyQueue<Integer> q = new MyQueue<>(5);
        assertTrue(q.empty());
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        assertFalse(q.empty());
        assertEquals(Integer.valueOf(1), q.peek());
        assertEquals(Integer.valueOf(1), q.dequeue());
        assertEquals(2, q.size());
    }
}
