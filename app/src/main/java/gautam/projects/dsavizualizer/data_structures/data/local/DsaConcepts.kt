package gautam.projects.dsavizualizer.data_structures.data.local

import java.util.LinkedList
import java.util.Queue

class Stack<T> {
    private val elements: MutableList<T> = mutableListOf()

    // Adds an element to the top of the stack.
    fun push(item: T) {
        elements.add(item)
    }

    // Removes and returns the top element of the stack. Returns null if the stack is empty.
    fun pop(): T? {
        if (isEmpty()) {
            return null
        }
        return elements.removeAt(elements.size - 1)
    }

    // Returns the top element without removing it. Returns null if the stack is empty.
    fun peek(): T? {
        return elements.lastOrNull()
    }

    // Checks if the stack is empty.
    fun isEmpty(): Boolean {
        return elements.isEmpty()
    }

    // Returns the number of elements in the stack.
    val size: Int
        get() = elements.size

    // Provides a list representation of the stack for easy visualization.
    fun getAsList(): List<T> {
        return elements.toList()
    }
}


// --- 2. Queue Implementation ---
// A Queue is a FIFO (First-In, First-Out) data structure.
// Think of a line at a ticket counter. People join (enqueue) at the back and leave (dequeue) from the front.

class Queue<T> {
    private val elements: MutableList<T> = mutableListOf()

    // Adds an element to the back of the queue.
    fun enqueue(item: T) {
        elements.add(item)
    }

    // Removes and returns the front element of the queue. Returns null if the queue is empty.
    fun dequeue(): T? {
        if (isEmpty()) {
            return null
        }
        return elements.removeAt(0)
    }

    // Returns the front element without removing it. Returns null if the queue is empty.
    fun peek(): T? {
        return elements.firstOrNull()
    }

    // Checks if the queue is empty.
    fun isEmpty(): Boolean {
        return elements.isEmpty()
    }

    // Returns the number of elements in the queue.
    val size: Int
        get() = elements.size

    // Provides a list representation of the queue for easy visualization.
    fun getAsList(): List<T> {
        return elements.toList()
    }
}


// --- 3. Linked List Implementation ---
// A Linked List consists of nodes, where each node contains data and a reference (or link) to the next node.

data class Node<T>(var value: T, var next: Node<T>? = null)

class LinkedList<T> {
    var head: Node<T>? = null

    // Adds a new node to the end of the list.
    fun append(value: T) {
        val newNode = Node(value)
        if (head == null) {
            head = newNode
            return
        }
        var current = head
        while (current?.next != null) {
            current = current.next
        }
        current?.next = newNode
    }

    // Deletes the first node with the given value.
    fun delete(value: T) {
        if (head == null) return

        if (head?.value == value) {
            head = head?.next
            return
        }

        var current = head
        while (current?.next != null && current.next?.value != value) {
            current = current.next
        }

        if (current?.next != null) {
            current.next = current.next?.next
        }
    }

    // Provides a list representation of the linked list for easy visualization.
    fun getAsList(): List<T> {
        val list = mutableListOf<T>()
        var current = head
        while (current != null) {
            list.add(current.value)
            current = current.next
        }
        return list
    }
}


// --- 4. Binary Search Tree (BST) Implementation ---
// A tree where each node has at most two children. The left child's value is less than the parent's,
// and the right child's value is greater.

data class BstNode<T : Comparable<T>>(
    var value: T,
    var left: BstNode<T>? = null,
    var right: BstNode<T>? = null,
    // You can add x, y coordinates here for visualization purposes
    var x: Float = 0f,
    var y: Float = 0f
)

class BinarySearchTree<T : Comparable<T>> {
    var root: BstNode<T>? = null

    // Public function to insert a value.
    fun insert(value: T) {
        root = insertRecursive(root, value)
    }

    // Private recursive helper function for insertion.
    private fun insertRecursive(current: BstNode<T>?, value: T): BstNode<T> {
        if (current == null) {
            return BstNode(value)
        }

        when {
            value < current.value -> current.left = insertRecursive(current.left, value)
            value > current.value -> current.right = insertRecursive(current.right, value)
            // value already exists, do nothing
        }
        return current
    }

    // Public function to delete a value.
    fun delete(value: T) {
        root = deleteRecursive(root, value)
    }

    // Private recursive helper function for deletion.
    private fun deleteRecursive(current: BstNode<T>?, value: T): BstNode<T>? {
        if (current == null) {
            return null
        }

        if (value == current.value) {
            // Case 1: No children (leaf node)
            if (current.left == null && current.right == null) {
                return null
            }
            // Case 2: One child
            if (current.right == null) return current.left
            if (current.left == null) return current.right

            // Case 3: Two children
            val smallestValue = findSmallestValue(current.right!!)
            current.value = smallestValue
            current.right = deleteRecursive(current.right, smallestValue)
            return current
        }

        if (value < current.value) {
            current.left = deleteRecursive(current.left, value)
        } else {
            current.right = deleteRecursive(current.right, value)
        }
        return current
    }

    private fun findSmallestValue(root: BstNode<T>): T {
        return if (root.left == null) root.value else findSmallestValue(root.left!!)
    }

    // Function to get all nodes in a list for drawing (e.g., using a level-order traversal).
    fun getNodesAsList(): List<BstNode<T>> {
        if (root == null) return emptyList()
        val nodes = mutableListOf<BstNode<T>>()
        val queue: Queue<BstNode<T>> = LinkedList()
        queue.add(root!!)
        while (queue.isNotEmpty()) {
            val node = queue.poll()
            nodes.add(node)
            node.left?.let { queue.add(it) }
            node.right?.let { queue.add(it) }
        }
        return nodes
    }
}