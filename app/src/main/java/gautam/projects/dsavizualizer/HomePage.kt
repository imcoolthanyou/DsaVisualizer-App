package gautam.projects.dsavizualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Queue
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import gautam.projects.dsavizualizer.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun HomePage(
    navController: NavHostController
){
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Black,
    ){innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF161118), Color(0xFF23202C))
                    )
                )
                .padding(horizontal = 16.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "DSA Visualizer",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Text(
                text = "Welcome!",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 16.dp)
            )
            Text(
                text = "Visualize algorithms. Understand concepts.",
                color = Color(0xFFBBBBBB),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 12.dp, bottom = 16.dp)
            )
            // Search Bar
            CompositionLocalProvider(LocalTextStyle provides TextStyle(color = Color.White)) {
                SearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    onSearch = { /* handle search, e.g., perform a search or navigate */ },
                    active = expanded,
                    onActiveChange = { expanded = it },
                    modifier = Modifier
                        .size(600.dp, 100.dp)
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 30.dp)
                        .background(
                            Color(0xFF2A2437),
                            RoundedCornerShape(16.dp)
                        ),
                    placeholder = {
                        Text(
                            "Search for a data structure (e.g., Stack).",
                            color = Color(0xFFCCCCCC),
                            fontSize = 15.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color(0xFFB392FF)
                        )
                    },
                    colors = SearchBarDefaults.colors(
                        containerColor = Color(0xFF2A2437),
                        dividerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    enabled = true
                ) {}
            }
            val cardBg = Color(0xFF231C31)
            val iconBg = Color(0xFF8765E4)
            val iconColor = Color.White
            val cardShape = RoundedCornerShape(18.dp)
            val cardHeight = 113.dp
            // Data structure cards list
            val structures: List<Triple<ImageVector, String, String>> = listOf(
                Triple(
                    Icons.Filled.Layers ,
                    "Stack",
                    "A linear data structure that follows the Last In First Out (LIFO) principle."
                ),
                Triple(
                    Icons.Filled.AccountTree ,
                    "Binary Search Tree",
                    "A hierarchical data structure where each node has at most two children."
                ),
                Triple(
                    Icons.Filled.TableChart,
                    "Linked List",
                    "A linear data structure where each element points to the next element."
                ),
                Triple(
                    first = Icons.Filled.TableChart,
                    second = "Merge Sort",
                    third = "Merge Sort is a divide and conquer algorithm."
                ),
                Triple(
                    Icons.Filled.Queue ,
                    "Queue",
                    "A linear data structure where each element points to the next element."
                ),

            )
            structures.forEach { (icon, title, desc) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                        .height(cardHeight)
                        .background(Color.Transparent)
                        .clickable {
                            when(title){
                                "Stack" -> navController.navigate(Routes.Stack.route)
                                "Queue"-> navController.navigate(Routes.Queue.route)
                                "Linked List"-> navController.navigate(Routes.LinkedList.route)
                                "Binary Search Tree"-> navController.navigate(Routes.BinarySearchTree.route)
                                "Merge Sort"-> navController.navigate(Routes.MergeSort.route)

                            }
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF231C31)
                    ),
                    shape = cardShape,
                    elevation = CardDefaults.cardElevation(5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp), verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icon in purple circle background
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .background(iconBg, shape = RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                icon, contentDescription = null,
                                tint = iconColor, modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Column {
                            Text(
                                text = title,
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = desc,
                                fontSize = 14.sp,
                                color = Color(0xFFB2B2C7),
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
