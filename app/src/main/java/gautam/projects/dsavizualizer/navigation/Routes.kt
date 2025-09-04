package gautam.projects.dsavizualizer.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Routes(val route: String){

    object Home: Routes("/home")

    object ConceptLibrary:Routes("/concept_library")
    object Dashboard:Routes("/dashboard")
    object Concepts:Routes("/concepts")
    object Problems:Routes("/problems")
    object Profile:Routes("/profile")

    //data structures routes
    object Stack:Routes("/stack")
    object Queue:Routes("/queue")
    object LinkedList:Routes("/linkedlist")
    object BinarySearchTree:Routes("/binarysearchtree")

    //sorting techniques routes
    object MergeSort:Routes("/mergesort")
}