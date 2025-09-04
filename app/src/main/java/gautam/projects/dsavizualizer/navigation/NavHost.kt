package gautam.projects.dsavizualizer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import gautam.projects.dsavizualizer.HomePage
import gautam.projects.dsavizualizer.ProblemPage
import gautam.projects.dsavizualizer.ProfilePage
import gautam.projects.dsavizualizer.data_structures.presentation.screens.ConceptLibrary
import gautam.projects.dsavizualizer.data_structures.presentation.screens.DSAScreen.BinarySearchTreeScreen
import gautam.projects.dsavizualizer.data_structures.presentation.screens.DSAScreen.LinkedListScreen
import gautam.projects.dsavizualizer.data_structures.presentation.screens.DSAScreen.StackScreen

import gautam.projects.dsavizualizer.data_structures.presentation.screens.DSAScreen.QueueScreen
import gautam.projects.dsavizualizer.data_structures.presentation.viewmodels.StackViewModel
import gautam.projects.dsavizualizer.sorting_techquines.presenatation.screens.MergeSortScreen

@Composable
fun Navhost(modifier: Modifier = Modifier) {

    val stackViewModel: StackViewModel= viewModel()

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home.route){

        composable(route = Routes.Home.route){
            HomePage(navController)
        }
        composable(route = Routes.ConceptLibrary.route){
            ConceptLibrary(navController)
        }
        composable(route = Routes.Stack.route){
            StackScreen(stackViewModel)
        }
        composable(route = Routes.Queue.route) {
            QueueScreen()
        }
        composable(route = Routes.LinkedList.route) {
            LinkedListScreen()
        }
        composable(route = Routes.BinarySearchTree.route) {
            BinarySearchTreeScreen()
        }
        composable(route = Routes.MergeSort.route) {
            MergeSortScreen()
        }

        //Home Page Rotes

        composable(route=Routes.Dashboard.route){
            HomePage(navController)
        }
        composable(route=Routes.Concepts.route){
            ConceptLibrary(navController)
        }
        composable(route=Routes.Problems.route){
            ProblemPage(navController)
        }
        composable(route=Routes.Profile.route){
            ProfilePage()
        }
    }

}