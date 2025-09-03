package gautam.projects.dsavizualizer.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Routes(val route: String){

    object Home: Routes("/home")
    object Stack:Routes("/stack")
    object Queue:Routes("/queue")
    object LinkedList:Routes("/linkedlist")
    object BinarySearchTree:Routes("/binarysearchtree")

    //sorting techniques routes
    object MergeSort:Routes("/mergesort")
}