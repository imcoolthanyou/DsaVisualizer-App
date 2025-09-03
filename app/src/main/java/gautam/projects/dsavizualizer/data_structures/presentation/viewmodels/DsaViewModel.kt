package gautam.projects.dsavizualizer.data_structures.presentation.viewmodels

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import gautam.projects.dsavizualizer.data_structures.core.models.BSTUINODE
import gautam.projects.dsavizualizer.data_structures.core.models.LinkedNode
import gautam.projects.dsavizualizer.data_structures.core.models.QueueItem
import gautam.projects.dsavizualizer.data_structures.core.models.StackItem
import gautam.projects.dsavizualizer.data_structures.data.local.BinarySearchTree
import gautam.projects.dsavizualizer.data_structures.data.local.BstNode
import gautam.projects.dsavizualizer.data_structures.data.local.LinkedList
import gautam.projects.dsavizualizer.data_structures.data.local.Queue
import gautam.projects.dsavizualizer.data_structures.data.local.Stack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StackViewModel : ViewModel() {
    private val _stack = Stack<StackItem>()
    private val _stackState = MutableStateFlow<List<StackItem>>(emptyList())
    val stackState = _stackState.asStateFlow()

    fun push(value: Any) {
        _stack.push(StackItem(value))
        _stackState.value = _stack.getAsList()
    }

    fun pop() {
        _stack.pop()
        _stackState.value = _stack.getAsList()
    }

    // Corrected: Now returns the value without updating state.
    fun peek(): Any? {
        return _stack.peek()
    }

    fun isEmpty(): Boolean {
        return _stack.isEmpty()
    }
}

class QueueViewModel : ViewModel() {
    private val _queue = Queue<QueueItem>()
    private val _queueState = MutableStateFlow<List<QueueItem>>(emptyList())
    val queueState = _queueState.asStateFlow()

    fun enqueue(value: Any) {
        _queue.enqueue(QueueItem(value))
        _queueState.value = _queue.getAsList()
    }

    fun dequeue() {
        _queue.dequeue()
        _queueState.value = _queue.getAsList()
    }

    fun peek(): Any? {
        return _queue.peek()
    }

    fun isEmpty(): Boolean {
        return _queue.isEmpty()
    }
}

class LinkedListViewModel : ViewModel() {
    private val _linkedList = LinkedList<Any>()
    private val _linkedListState = MutableStateFlow<List<LinkedNode>>(emptyList())
    val linkedListState = _linkedListState.asStateFlow()

    fun append(value: Any) {
        _linkedList.append(value)
       updateState()
    }

    fun delete(value: Any) {
        _linkedList.delete(value)
        updateState()
    }
    private fun updateState(){
        val uiList=mutableListOf<LinkedNode>()
        var current=_linkedList.head

        while (current!=null){
            val nextAddress=if (current.next!=null){
                val fullHash = current.next.hashCode().toString(16).uppercase()
                val shortAddress=fullHash.takeLast(4)
                "-> 0x${shortAddress}"
            }else {
                "-> null"
            }
            uiList.add(LinkedNode(current.value,nextAddress))
            current=current.next
        }
        _linkedListState.value=uiList
    }
}

class BinarySearchTreeViewModel : ViewModel() {
    // Now uses the generic BinarySearchTree, specialized for Int.
    private val _binarySearchTree = BinarySearchTree<Int>()
    private val _binarySearchTreeState = MutableStateFlow<List<BSTUINODE>>(emptyList())
    val binarySearchTreeState = _binarySearchTreeState.asStateFlow()

    fun insert(value: Int) {
        _binarySearchTree.insert(value)
        updateUiNodes()
    }

    fun delete(value: Int) {
        _binarySearchTree.delete(value)
        updateUiNodes()
    }
    private fun updateUiNodes(){
        val nodes=mutableListOf<BSTUINODE>()

        var xPositionCounter=0

        fun buildUiNodes(node: BstNode<Int>?,depth:Int){
            if (node==null)return

            buildUiNodes(node.left,depth+1)

            val uiNode=BSTUINODE(node.value,depth=depth)

            uiNode.yOffSet=(depth*80).dp
            uiNode.xOffSet=(xPositionCounter*70).dp
            xPositionCounter++
            nodes.add(uiNode)
            buildUiNodes(node.right,depth+1)
        }
        fun linkParents(node: BstNode<Int>?, parentUiNode: BSTUINODE?) {
            if (node == null) return
            val currentUiNode = nodes.firstOrNull { it.value == node.value }
            if (currentUiNode != null && parentUiNode != null) {
                currentUiNode.parentXOffSet = parentUiNode.xOffSet
                currentUiNode.parentYOffSet = parentUiNode.yOffSet
            }
            // Recurse down the tree, passing the current node as the new parent.
            linkParents(node.left, currentUiNode)
            linkParents(node.right, currentUiNode)
        }

        buildUiNodes(_binarySearchTree.root, 0)
        linkParents(_binarySearchTree.root, null)

        _binarySearchTreeState.value = nodes
    }

}
