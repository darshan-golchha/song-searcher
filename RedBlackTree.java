// --== CS400 Fall 2023 File Header Information ==--
// Name: Darshan Golchha
// Email: dgolchha@wisc.edu
// Group: C37
// TA: Connor Bailey
// Lecturer: Florian Heimerl
// Notes to Grader: This class contains two helper methods that are solely
// used for testing purposes. The two helper methods are checkNoRedRedViolation
// and calculateBlackHeight.
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class implements a Red-Black Tree (RBT) data structure.
 * 
 * @param <T>
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    /**
     * This class represents a node in a Red-Black Tree (RBT).
     * 
     * @param <T>
     */
    protected static class RBTNode<T> extends Node<T> {
        public int blackHeight = 0;

        public RBTNode(T data) {
            super(data);
        }

        public RBTNode<T> getUp() {
            return (RBTNode<T>) this.up;
        }

        public RBTNode<T> getDownLeft() {
            return (RBTNode<T>) this.down[0];
        }

        public RBTNode<T> getDownRight() {
            return (RBTNode<T>) this.down[1];
        }
    }

    /**
     * This method changes the Red-Black Tree structure or the color
     * of nodes to restore the properties of a RBT after its properties
     * have been violated.
     * 
     * @param node
     */
    protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> node) {
        // if the node is a root node, setting to black
        if (node.up == null) {
            node.blackHeight = 1;
            return;
        }
        // Ensuring that the parent is black or null
        if (node.getUp().blackHeight == 0) {
            // checking for the aunt node
            RBTNode<T> aunt = null;
            // setting aunt according to the parent's position.
            if (node.getUp().getUp() != null) {
                RBTNode<T> grandparent = node.getUp().getUp();

                // Check if the parent is the left child of the grandparent
                if (grandparent.down[0] != null && grandparent.down[0] != node.getUp()) {
                    aunt = grandparent.getDownLeft();
                }
                // Check if the parent is the right child of the grandparent
                else if (grandparent.down[1] != null && grandparent.down[1] != node.getUp()) {
                    aunt = grandparent.getDownRight();
                }
                // If the grandparent has no children, aunt remains null
            }
            // checking if the aunt is red or black/null
            if (aunt != null && aunt.blackHeight == 0) {
                // aunt was found to be red, so we recolor
                node.getUp().blackHeight = 1;
                aunt.blackHeight = 1;
                node.getUp().getUp().blackHeight = 0;
                // recursively calling the method on the grandparent
                enforceRBTreePropertiesAfterInsert(node.getUp().getUp());
            } else {
                // aunt was found to be black/null, so we rotate and color swap
                // if the node, parent and grandparent are not in a straight line, we rotate
                // to first get them in a straight line
                if (node == node.getUp().getDownRight() && node.getUp() == node.getUp().getUp().getDownLeft()) {
                    // left rotate
                    this.rotate(node, node.getUp());
                    node = node.getDownLeft();
                } else if (node == node.getUp().getDownLeft() && node.getUp() == node.getUp().getUp().getDownRight()) {
                    // right rotate
                    this.rotate(node, node.getUp());
                    node = node.getDownRight();
                }
                // else if the node, parent and grandparent are in a straight line, we directly
                // rotate and color swap
                if (node == node.getUp().getDownRight() && node.getUp() == node.getUp().getUp().getDownRight()) {
                    // left rotate
                    this.rotate(node.getUp(), node.getUp().getUp());
                    // color swap
                    node.getUp().blackHeight = 1;
                    node.getUp().getDownLeft().blackHeight = 0;
                } else if (node == node.getUp().getDownLeft() && node.getUp() == node.getUp().getUp().getDownLeft()) {
                    // right rotate
                    this.rotate(node.getUp(), node.getUp().getUp());
                    // color swap
                    node.getUp().blackHeight = 1;
                    node.getUp().getDownRight().blackHeight = 0;
                }
            }
        } else {
            // parent was found to be black, so we do nothing
            return;
        }
    }

    /**
     * This method inserts a node into the Red-Black Tree (RBT) and
     * returns true if the node was inserted successfully. It also 
     * ensures that after a node is inserted the properties of the
     * red-black tree are restored if there is a violation by 
     * calling the enforceRBTreePropertiesAfterInsert method.
     * 
     * @param node
     * @return true if the node was inserted successfully
     */
    @Override
    public boolean insert(T data) throws NullPointerException {
        if (data == null)
            throw new NullPointerException();
        RBTNode<T> node = new RBTNode<T>(data);
        // if the tree is empty, we insert the data as the root
        if (this.insertHelper(node)) {
            this.enforceRBTreePropertiesAfterInsert(node);
            return true;
        }
        return false;
    }

    // writing test methods for the RBT to test the insert and
    // enforceRBTPropertiesAfterInsert methods

    
    /**
     * Test case to verify that a Red-Black Tree (RBT) maintains its properties
     * when inserting elements of different data types (integers) to form a valid
     * RBT structure. This test inserts integers in a specific order and checks if
     * the colors of the root node, its children, grandchildren, and
     * great-grandchildren are correct.
     * 
     * @return true if the colors of the root node, its children, grandchildren,
     *        and great-grandchildren are correct
     */
    @Test
    public void RBTAllNodesTest() {
        // creating a new RBT
        RedBlackTree<Integer> rbt = new RedBlackTree<Integer>();
        // inserting 7, 14, 18, 23, 1, 11, 20, 29, 25, 27
        rbt.insert(7);
        rbt.insert(14);
        rbt.insert(18);
        rbt.insert(23);
        rbt.insert(1);
        rbt.insert(11);
        rbt.insert(20);
        rbt.insert(29);
        rbt.insert(25);
        rbt.insert(27);
        // checking if the root is black
        assertTrue(((RBTNode<Integer>) rbt.root).blackHeight == 1);
        // checking if the root node's children are red
        assertTrue(((RBTNode<Integer>) rbt.root).getDownLeft().blackHeight == 0);
        assertTrue(((RBTNode<Integer>) rbt.root).getDownRight().blackHeight == 0);
        // checking if the root node's grandchildren are black
        assertTrue(((RBTNode<Integer>) rbt.root).getDownLeft().getDownLeft().blackHeight == 1);
        assertTrue(((RBTNode<Integer>) rbt.root).getDownLeft().getDownRight().blackHeight == 1);
        assertTrue(((RBTNode<Integer>) rbt.root).getDownRight().getDownLeft().blackHeight == 1);
        assertTrue(((RBTNode<Integer>) rbt.root).getDownRight().getDownRight().blackHeight == 1);
        // checking if the root node's great-grandchildren are red
        assertTrue(((RBTNode<Integer>) rbt.root).getDownLeft().getDownLeft().getDownLeft().blackHeight == 0);
        assertTrue(((RBTNode<Integer>) rbt.root).getDownLeft().getDownLeft().getDownRight().blackHeight == 0);
        assertTrue(((RBTNode<Integer>) rbt.root).getDownRight().getDownRight().getDownLeft().blackHeight == 0);
    }

    
    /**
     * Test case to verify that a Red-Black Tree (RBT) maintains its properties
     * when inserting elements of different data types (integers) to form a valid
     * RBT structure. This test inserts integers in a specific order and checks if
     * no red nodes have red children.
     * 
     * @return true if no red nodes have red children
     */
    @Test
    public void integeredRedViolationTest() {
        // Create a new RBT
        RedBlackTree<Integer> rbt = new RedBlackTree<Integer>();

        // Insert values to form a valid RBT
        rbt.insert(7);
        rbt.insert(10);
        rbt.insert(5);
        rbt.insert(15);
        rbt.insert(3);
        rbt.insert(12);
        rbt.insert(18);

        // Check if the root is black
        assertTrue(((RBTNode<Integer>) rbt.root).blackHeight == 1);

        // Check if no red nodes have red children
        assertTrue(checkNoRedRedViolation((RBTNode<Integer>) rbt.root));
    }

    
    /**
     * Test case to verify that a Red-Black Tree (RBT) maintains its properties
     * when inserting elements of different data types (strings) to form a valid
     * RBT structure. This test inserts strings in a specific order and checks
     * if the black heights of left and right subtrees are equal.
     * 
     * @return true if the black heights of left and right subtrees are equal
     */
    @Test
    public void stringedBlackHeightTest() {
        // Create a new RBT
        RedBlackTree<String> rbt = new RedBlackTree<String>();

        // Insert values to form a valid RBT
        rbt.insert("F");
        rbt.insert("G");
        rbt.insert("A");
        rbt.insert("E");
        rbt.insert("K");
        rbt.insert("D");
        rbt.insert("L");
        // Cast root to RBTNode
        RBTNode<String> rbtRoot = (RBTNode<String>) rbt.root;

        // Calculate black height for the left and right subtrees
        int leftBlackHeight = calculateBlackHeight(rbtRoot.getDownLeft());
        int rightBlackHeight = calculateBlackHeight(rbtRoot.getDownRight());
        // Check if the black heights are equal
        assertTrue(leftBlackHeight == rightBlackHeight);
    }

    // Helper method to calculate the black height of a subtree
    /**
     * This method calculates the black height of a subtree and
     * returns -1 if the black heights of the children are not equal.
     * @param <T>
     * @param node
     * @return int, black height of the subtree
     */
    private static <T> int calculateBlackHeight(RBTNode<T> node) {
        if (node == null)
            return 1;

        int leftHeight = calculateBlackHeight(node.getDownLeft());
        int rightHeight = calculateBlackHeight(node.getDownRight());

        // Check if the black heights of children are equal
        if (leftHeight != rightHeight)
            return -1; // Black heights are not equal

        // Increment black height if the current node is black
        if (node.blackHeight == 1)
            leftHeight++;

        return leftHeight;
    }

    // Helper method to recursively check red-red violation
    /**
     * This method recursively checks if no red nodes have red children and
     * returns false if a red node has a red child.
     * @param <T>
     * @param node
     * @return  boolean, true if no red nodes have red children
     */
    private static <T> boolean checkNoRedRedViolation(RBTNode<T> node) {
        if (node == null)
            return true;

        // Check if no red nodes have red children
        if (node.blackHeight == 0) {
            if (node.getDownLeft() != null && node.getDownLeft().blackHeight == 0)
                return false;
            if (node.getDownRight() != null && node.getDownRight().blackHeight == 0)
                return false;
        }

        // Recursively check children
        return checkNoRedRedViolation(node.getDownLeft()) && checkNoRedRedViolation(node.getDownRight());
    }
}
