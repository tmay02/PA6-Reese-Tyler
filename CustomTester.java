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
            System.out.println(deque.data[i]);
        }
        for(int i = 0; i < expected2.length; i++) {
            assertEquals(expected2[i], deque.data[i]);
        }
    }

    /**
     * Test the addFirst method when front is moved to the back of the array,
     * and when a null element is added.
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

    }

    /**
     * Test the addLast method when [TODO]
     */
    @Test
    public void testAddLast() {

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
     * Test the removeLast method when [TODO]
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

        assertEquals(Integer.valueOf(0), d.removeLast());

        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], d.data[i]);
        }
        assertEquals(2, d.size);
        assertEquals(6, d.rear);
        assertEquals(5, d.front);
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
