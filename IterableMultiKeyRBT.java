
// --== CS400 Fall 2023 File Header Information ==--
// Name: Darshan Golchha
// Email: dgolchha@wisc.edu
// Group: C37
// TA: Connor Bailey
// Lecturer: Florian Heimerl
// Notes to Grader: N/A
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import org.junit.jupiter.api.Test;

/**
 * This class extends the RedBlackTree class to store multiple objects per key
 * by keeping lists of objects in each node of the tree.
 * 
 * @param <T>
 */
public class IterableMultiKeyRBT<T extends Comparable<T>> extends RedBlackTree<KeyListInterface<T>>
        implements IterableMultiKeySortedCollectionInterface<T> {

    private Comparable<T> startPoint; // the starting point for iterations
    private int numKeys; // the number of values in the tree

    /**
     * Returns an iterator that does an in-order iteration over the tree.
     * 
     * @return the iterator object
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            // stack that stores the nodes that need to be visited
            Stack<Node<KeyListInterface<T>>> stack = getStarStack();
            // iterator over the keylist of the current node
            Iterator<T> currentKeyIterator = null;

            /**
             * Returns true if the iteration has more elements.
             * 
             * @return true if the iteration has more elements
             */
            @Override
            public boolean hasNext() {
                // if the stack is empty and the current key iterator is null, there are no
                // more elements to iterate
                return !stack.isEmpty() || (currentKeyIterator != null && currentKeyIterator.hasNext());
            }

            /**
             * Returns the next element in the iteration by going through the tree in order
             * and returning the next element in the keylist of the current node till
             * there are no more elements to iterate.
             * 
             * @return the next element in the iteration
             * @throws NoSuchElementException if the iteration has no more elements
             */
            @Override
            public T next() {
                // if the current key iterator has a next element, we return that element
                if (currentKeyIterator != null && currentKeyIterator.hasNext()) {
                    return currentKeyIterator.next();
                } else {
                    // if the stack is empty, there are no more elements to iterate
                    if (stack.isEmpty()) {
                        throw new NoSuchElementException("No more elements to iterate.");
                    }
                    // if the current key iterator is null, we need to get the next node from the
                    // stack and get the iterator for the keylist of that node
                    // we also add the next node in the stack
                    Node<KeyListInterface<T>> currNode = stack.pop();
                    Node<KeyListInterface<T>> nextNode;
                    currentKeyIterator = currNode.data.iterator();
                    // Add the next node in the stack
                    if(currNode.down[1] != null){
                        nextNode = currNode.down[1];
                    
                        //while the nextNode is not null,
                        // push the nextNode onto the stack and set the nextNode to the left child of the nextNode
                        while(nextNode != null){
                            stack.push(nextNode);
                            nextNode = nextNode.down[0];
                        }
                    }
                    return currentKeyIterator.next();
                }
            }
        };
    }

    /**
     * Returns a stack that stores the nodes that need to be visited for the
     * iteration.
     * 
     * @return the stack that stores the nodes that need to be visited for the
     */
    protected Stack<Node<KeyListInterface<T>>> getStarStack() {
        // if no iteration start point was set, initialising the stack
        // with the root of the tree to the smallest key in the tree
        if (startPoint == null) {
            Stack<Node<KeyListInterface<T>>> startStack = new Stack<>();
            Node<KeyListInterface<T>> currNode = this.root;
            // going down the left side of the tree till we reach the smallest key
            while (currNode != null) {
                startStack.push(currNode);
                currNode = currNode.down[0];
            }
            return startStack;
        } else {
            // if an iteration start point was set, initialising the stack
            // with the root of the tree to the key closest to the start point
            Stack<Node<KeyListInterface<T>>> starStack = new Stack<>();
            Node<KeyListInterface<T>> currNode = this.root;
            while (currNode != null) {
                // comparing the start point to the key of the current node
                int cmp = startPoint.compareTo(currNode.data.iterator().next());
                if (cmp == 0) {
                    // the start point is the key of the current node
                    // we add the current node to the stack and go down the left side of the tree
                    starStack.push(currNode);
                    break;
                } else if (cmp < 0) {
                    // the start point is smaller than the key of the current node
                    // we add the current node to the stack and go down the left side of the tree
                    starStack.push(currNode);
                    currNode = currNode.down[0];
                } else {
                    // the start point is larger than the key of the current node
                    // we go down the right side of the tree
                    currNode = currNode.down[1];
                }
            }
            return starStack;
        }
    }

    /**
     * Sets the starting point for iterations. Future iterations will start at the
     * starting point or the key closest to it in the tree. This setting is
     * remembered until it is reset. Passing in null disables the starting point.
     * 
     * @param startPoint the start point to set for iterations
     */
    @Override
    public void setIterationStartPoint(Comparable<T> key) {
        this.startPoint = key;
    }

    /**
     * Returns the number of values in the tree.
     * 
     * @return the number of values in the tree.
     */
    @Override
    public int numKeys() {
        return numKeys;
    }

    /**
     * Inserts value into tree that can store multiple objects per key by keeping
     * lists of objects in each node of the tree.
     * 
     * @param key object to insert
     * @return true if a new node was inserted, false if the key was added into an
     *         existing node
     */
    @Override
    public boolean insertSingleKey(T key) {
        // creating the new keylist
        KeyListInterface<T> newKeyList = new KeyList<>(key);
        // checking if the tree contains the key
        if (this.findNode(newKeyList) != null) {
            // if it does, we get the keylist that contains the key
            Node<KeyListInterface<T>> keyNode = this.findNode(newKeyList);
            // and add the key to the keylist
            keyNode.data.addKey(key);
            // and return false because we didn't add a new node
            numKeys++;
            return false;
        } else {
            // if it doesn't, we add the keylist to the tree
            this.insert(newKeyList);
            // and return true because we added a new node
            numKeys++;
            return true;
        }
    }

    /**
     * Removes value from tree that can store multiple objects per key by keeping
     * lists of objects in each node of the tree.
     * 
     * @param key object to remove
     * @return true if a node was removed, false if the key was removed from a
     *         keylist in a node
     */
    public void clear() {
        this.clear();
        numKeys = 0;
    }

    // Testing the iterator and the setIterationStartPoint method

    /**
     * This tester method tests the iterator and the setIterationStartPoint method
     * by creating a new IterableMultiKeyRBT tree, inserting some values into the
     * tree and iterating over the tree and checking the values.
     */
    @Test
    public void testIteratorSequence() {
        // Create a new IterableMultiKeyRBT tree
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();

        // Insert some values into the tree
        tree.insertSingleKey(1);
        tree.insertSingleKey(2);
        tree.insertSingleKey(3);

        // Iterate over the tree and check the values
        Integer[] expectedSequence = { 1, 2, 3 };
        int i = 0;
        for (Integer key : tree) {
            assertEquals(expectedSequence[i++], key);
        }
    }

    /**
     * This tester method specifically tests the setIterationStartPoint method
     * by creating a new IterableMultiKeyRBT tree, inserting some values into the
     * tree and changing the iteration start point and iterating over the tree and
     * checking the values.
     */
    @Test
    public void testSetIterationStartPoint() {
        // Create a new IterableMultiKeyRBT tree
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();

        // Insert some values into the tree
        tree.insertSingleKey(1);
        tree.insertSingleKey(2);
        tree.insertSingleKey(3);

        // Set the iteration start point to 2
        tree.setIterationStartPoint(2);

        // Iterate over the tree and check the values
        Integer[] expectedSequence = { 2, 3 };
        int i = 0;
        for (Integer key : tree) {
            assertEquals(expectedSequence[i++], key);
        }

        // Reset the iteration start point
        tree.setIterationStartPoint(null);

        // Iterate over the tree and check the values
        expectedSequence = new Integer[] { 1, 2, 3 };
        i = 0;
        for (Integer key : tree) {
            assertEquals(expectedSequence[i++], key);
        }
    }

    // Testing the numKeys method for duplicate values

    /**
     * This tester method tests the numKeys method for duplicate values by creating
     * a new IterableMultiKeyRBT tree, inserting some values into the tree and
     * checking the number of keys and the size of the tree.
     */
    @Test
    public void testNumKeysAndSize() {
        // Create a new IterableMultiKeyRBT tree
        IterableMultiKeyRBT<Integer> tree = new IterableMultiKeyRBT<>();

        // Insert some values into the tree
        tree.insertSingleKey(1);
        tree.insertSingleKey(3);
        tree.insertSingleKey(2);
        tree.insertSingleKey(2);
        tree.insertSingleKey(1);
        tree.insertSingleKey(3);

        // Check the number of keys
        assertEquals(6, tree.numKeys());
        assertTrue(tree.size() != tree.numKeys());
    }

    // Testing the iterator sequence for duplicate values with characters

    /**
     * This tester method tests the iterator sequence for duplicate values with
     * characters by creating a new IterableMultiKeyRBT tree, inserting some values
     * into the tree and iterating over the tree and checking the values.
     */
    @Test
    public void testIteratorSequenceChar() {
        // Create a new IterableMultiKeyRBT tree
        IterableMultiKeyRBT<Character> tree = new IterableMultiKeyRBT<>();

        // Insert some values into the tree
        tree.insertSingleKey('a');
        tree.insertSingleKey('b');
        tree.insertSingleKey('c');
        tree.insertSingleKey('a');
        tree.insertSingleKey('b');
        tree.insertSingleKey('c');

        // Iterate over the tree and check the values
        Character[] expectedSequence = { 'a', 'a', 'b', 'b', 'c', 'c' };
        int i = 0;
        for (Character key : tree) {
            assertEquals(expectedSequence[i++], key);
        }
    }

    // Testing the iterator sequence for duplicate values with boolean

    /**
     * This tester method tests the iterator sequence for duplicate values with
     * boolean by creating a new IterableMultiKeyRBT tree, inserting some values
     * into the tree and iterating over the tree and checking the values.
     */
    @Test
    public void testIteratorSequenceBoolean() {
        // Create a new IterableMultiKeyRBT tree
        IterableMultiKeyRBT<Boolean> tree = new IterableMultiKeyRBT<>();

        // Insert some values into the tree
        tree.insertSingleKey(true);
        tree.insertSingleKey(false);
        tree.insertSingleKey(true);
        tree.insertSingleKey(false);
        tree.insertSingleKey(true);
        tree.insertSingleKey(false);

        // Iterate over the tree and check the values
        Boolean[] expectedSequence = { false, false, false, true, true, true };
        int i = 0;
        for (Boolean key : tree) {
            assertEquals(expectedSequence[i++], key);
        }
    }

}