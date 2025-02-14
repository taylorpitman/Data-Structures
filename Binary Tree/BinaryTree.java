import java.util.LinkedList;
import java.util.Queue;

/*
 * Binary Tree Implementation
 * - A binary tree is a tree data structure where each node has at most two children
 * - This implementation supports generic types that are Comparable
 */


public class BinaryTree<T extends Comparable<T>> {

    // Root node of the tree
    private Node<T> root;

    /* Constructor: Creates an empty binary tree */
    public BinaryTree() {
        this.root = null;

    }

    /* addNode: Adds a new node to the tree
     * - Uses level-order insertion (fills tree from left to right, level by level)
     * - Time Complexity: O(n) where n is number of nodes */
    public void addNode(T data) {
        if (root == null) {
            root = new Node<T>(data);
            return;
        }
        
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();
            
            if (current.getLeft() == null) {
                current.setLeft(new Node<T>(data));
                return;
            }
            else {
                queue.add(current.getLeft());
            }
            
            if (current.getRight() == null) {
                current.setRight(new Node<T>(data));
                return;
            }
            else {
                queue.add(current.getRight());
            }
        }
    }

     /* getHeight: Returns the height of the tree
     * - Height is the longest path from root to leaf
     * - Time Complexity: O(n)*/
    private int getHeight(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
    }

    /* levelOrderSearch: Prints tree level by level
     * - Useful for visualizing tree structure
     * - Time Complexity: O(n)*/
    public void levelOrderSearch() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }

        int height = getHeight(root);
        for (int level = 0; level < height; level++) {
            printLevel(root, level);
        }
        System.out.println(); // new line after printing all levels
    }

    private void printLevel(Node<T> node, int level) {
        if (node == null) {
            return;
        }
        
        if (level == 0) {
            System.out.print(node.getData() + " ");
        }
        else {
            printLevel(node.getLeft(), level - 1);
            printLevel(node.getRight(), level - 1);
        }
    }


    //SEARCH ALGOS + METHODS

    /* inOrderTraversal: Left -> Root -> Right
     * - Visits nodes in ascending order for BST
     * - Time Complexity: O(n) */
    public void inOrderTraversal() {

        //start with root
        inOrderTraversal(root);

        System.out.println();
    }

    private void inOrderTraversal(Node<T> node) {

        //IF (root is null) return
        if (node == null) {
            return;
        }

        //otherwise traverse left, print data, traverse right
        inOrderTraversal(node.getLeft()); //L
        System.out.print(node.getData() + " ");
        inOrderTraversal(node.getRight()); //R
    }

    //DFS (depth first search) / preorder traversal 
    //(NOTE: this is only the same as eachother in trees not in graphs)

    /* dfsTraversal: Depth-First Search (Pre-order: Root -> Left -> Right)
     * - Explores as far as possible along each branch
     * - Time Complexity: O(n) */
    public void dfsTraversal() {
        //start with root
        dfsTraversal(root);

        System.out.println();
    }

    public void dfsTraversal(Node<T> node) {

        //IF (root is null) return
        if (node == null) {
            return;
        }

        //otherwise print data, traverse left, traverse right
        System.out.print(node.getData() + " ");
        dfsTraversal(node.getLeft());
        dfsTraversal(node.getRight());
    }

    //breadth first search

    /* bfsTraversal: Breadth-First Search
     * - Explores all nodes at the present depth before moving to the next level
     * - Time Complexity: O(n) */
    public void bfsTraversal() {
        //start with root
        bfsTraversal(root);

        System.out.println();
    }

    private void bfsTraversal(Node<T> node) {
        //IF (root is null) return
        if (node == null) {
            return;
        }
        System.out.println(node.getData() + " ");

        //create a queue
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(node);

        //while queue is not empty
        while (!queue.isEmpty()) {

            //dequeue the node
            Node<T> current = queue.poll();

            //print the data
            System.out.print(current.getData() + " ");

            //if left is not null, enqueue left
            if (current.getLeft() != null) {
                queue.add(current.getLeft());
            }

            //if right is not null, enqueue right
            if (current.getRight() != null) {
                queue.add(current.getRight());
            }
        }
    }

    /* isEmpty: Checks if tree is empty
     * - Time Complexity: O(1) */
    public boolean isEmpty() {
        return root == null;
    }

    /* search: Finds if element exists in tree
     * - Returns true if found, false otherwise
     * - Time Complexity: O(n) */
    public boolean search(T data) {
        return search(root, data);
    }

    private boolean search(Node<T> node, T data) {
        if (node == null) {
            return false;
        }
        if (node.getData().compareTo(data) == 0) {
            return true;
        }
        return search(node.getLeft(), data) || search(node.getRight(), data);
    }

    /* size: Returns number of nodes in tree
     * - Time Complexity: O(n) */
    public int size() {
        return size(root);
    }

    private int size(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.getLeft()) + size(node.getRight());
    }

    
    /* delete: Removes node with given value
     * Handles 3 cases:
     * 1. Leaf node: Simply remove
     * 2. One child: Replace with child
     * 3. Two children: Replace with minimum value in right subtree
     * - Time Complexity: O(h) where h is height of tree */
    public void delete(T data) {
        root = deleteRec(root, data);
    }

    private Node<T> deleteRec(Node<T> node, T data) {
        if (node == null) {
            return null;
        }
        
        if (node.getData().compareTo(data) == 0) {
            // Case 1: Leaf node
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }
            // Case 2: One child
            if (node.getLeft() == null) {
                return node.getRight();
            }
            if (node.getRight() == null) {
                return node.getLeft();
            }
            // Case 3: Two children
            Node<T> successor = findMin(node.getRight());
            node.setData(successor.getData());
            node.setRight(deleteRec(node.getRight(), successor.getData()));
            return node;
        }
        
        node.setLeft(deleteRec(node.getLeft(), data));
        node.setRight(deleteRec(node.getRight(), data));
        return node;
    }

    private Node<T> findMin(Node<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }
}
