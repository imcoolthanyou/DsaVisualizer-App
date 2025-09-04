package gautam.projects.dsavizualizer

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke // The single, correct import for Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import gautam.projects.dsavizualizer.navigation.Routes

// Main Screen Composable
@Composable
fun HomePage(navController: NavHostController) {
    // This Box is the key to the layout. It allows us to layer UI elements.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF161118), Color(0xFF23202C))
                )
            )
    ) {
        // All of your screen content now goes inside a LazyColumn.
        // This makes it scrollable and performant.
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp), // Add padding to the bottom to avoid overlap with the nav bar
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            item {
                Spacer(modifier = Modifier.height(56.dp))
                Text(text = "DSA Visualizer", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 26.sp)
                Text(
                    text = "Learn the concepts. Master the problems.",
                    color = Color(0xFFBBBBBB),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
                )
            }

            // Main Feature Cards
            item {
                FeatureCard(title = "Concept Library", subtitle = "Explore interactive visualizations.")
                Spacer(modifier = Modifier.height(16.dp))
                FeatureCard(title = "Problem Solver", subtitle = "Practice coding and test your skills.")
            }

            // Continue Learning Section
            item {
                SectionHeader("Continue Learning")

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp), // Added content padding
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        ContinueLearningCard(icon = Icons.Default.AccountTree, title = "Binary Trees")
                    }
                    item {
                        ContinueLearningCard(icon = Icons.Default.Quiz, title = "Merge Sort")
                    }
                    item {
                        ContinueLearningCard(icon = Icons.Default.Person, title = "Your Profile")
                    }
                }
            }

            // Today's Focus Section
            item {
                SectionHeader("Today's Focus")

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable(onClick = {}),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF231C31))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp), // Single padding inside
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Concept Of the Day",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                        )
                        Text(
                            text = "Hash Tables",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                    }
                }
            }

            // Your Progress Section
            item {
                SectionHeader("Your Progress")

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    CircularProgressBar(
                        percentage = 0.65f,
                        title = "65%",
                        subtitle = "Concepts",
                        details = "13/20 completed"
                    )
                    CircularProgressBar(
                        percentage = 0.42f,
                        title = "42%",
                        subtitle = "Problems",
                        details = "21/50 solved"
                    )
                }
            }
        }


        CustomBottomNavBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            navController = navController
        )
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    title: String,
    subtitle: String,
    details: String,
    size: Dp = 150.dp, // Slightly larger for better text fit
    strokeWidth: Dp = 12.dp,
    progressColor: Color = Color(0xFF8765E4),
    trackColor: Color = Color(0xFF2A2437)
) {
    val sweepAngle by animateFloatAsState(
        targetValue = 360 * percentage,
        animationSpec = tween(durationMillis = 1000, delayMillis = 200), // Added a small delay
        label = "progressAnimation"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(size)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(text = subtitle, color = Color.White, fontSize = 16.sp)
            Text(text = details, color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Composable
fun CustomBottomNavBar(modifier: Modifier = Modifier,navController: NavHostController) {
    var selectedIndex by remember { mutableStateOf(0) }
    val items = listOf("Dashboard", "Concepts", "Problems", "Profile")
    val icons = listOf(Icons.Default.Dashboard, Icons.Default.AccountTree, Icons.Default.Quiz, Icons.Default.Person)
    val destinations = listOf(
        Routes.Home,      // Corresponds to "Dashboard"
        Routes.ConceptLibrary,     // Assuming "Concepts" leads to Stack for now
        Routes.Problems,   // Assuming "Problems" leads to Sorting for now
        Routes.Profile       // Assuming "Profile" leads back home for now
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
            .height(70.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF2A2437).copy(alpha = 0.9f))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(24.dp))
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, title ->
                val isSelected = selectedIndex == index
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable {
                            selectedIndex = index
                            // Navigate using the type-safe route from your list
                            navController.navigate(route = destinations[index].route)
                        }
                        .padding(vertical = 8.dp)
                        .weight(1f) // Give each item equal weight
                ) {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = title,
                        tint = if (isSelected) Color.White else Color.Gray,
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = title,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}


@Composable
fun FeatureCard(title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF231C31))
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Using a Box for the icon background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF8765E4).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (title == "Concept Library") Icons.Default.AccountTree else Icons.Default.Quiz,
                    contentDescription = title,
                    tint = Color(0xFFB392FF),
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = subtitle, color = Color(0xFFB2B2C7), fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 24.dp, bottom = 12.dp)
    )
}

// Added a new reusable composable for the small "Continue Learning" cards
@Composable
fun ContinueLearningCard(icon: ImageVector, title: String) {
    Card(
        modifier = Modifier.size(width = 150.dp, height = 100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF231C31))
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFFB392FF),
                modifier = Modifier.size(28.dp)
            )
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

