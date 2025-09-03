package gautam.projects.dsavizualizer.data_structures.presentation.screens.DSAScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import gautam.projects.dsavizualizer.data_structures.presentation.viewmodels.LinkedListViewModel

// 1. Add the OptIn for the Experimental FlowRow API
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LinkedListScreen(viewModel: LinkedListViewModel = viewModel()) {
    val listState by viewModel.linkedListState.collectAsState()
    var inputValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF161118))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Visualization Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF23202C)),
            contentAlignment = Alignment.TopStart // Align to the top-start for FlowRow
        ) {
            // 2. Replace LazyRow with FlowRow
            FlowRow(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()), // Make the area scrollable if it overflows
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 3. Use a standard forEach loop instead of `items`
                listState.forEach { item ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(animationSpec = tween(500)) + slideInHorizontally(),
                        exit = fadeOut(animationSpec = tween(500)) + slideOutHorizontally()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            LinkedListNode(
                                value = item.nodeValue.toString(),
                                nextAddress = item.nextAddress
                            )
                            if (item.nextAddress != "-> null") {
                                ConnectingArrow()
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Control Panel
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = inputValue,
                onValueChange = { inputValue = it },
                label = { Text("Value to Append/Delete") },
                singleLine = true,
                // ... your colors
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF23202C),
                    focusedIndicatorColor = Color(0xFFB392FF),
                    unfocusedContainerColor = Color(0xFF23202C),
                    unfocusedIndicatorColor = Color(0xFFB392FF),
                    disabledContainerColor = Color(0xFF23202C),
                    disabledIndicatorColor = Color(0xFFB392FF),
                    focusedTextColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { if (inputValue.isNotBlank()) viewModel.append(inputValue); inputValue = "" }) {
                    Text("Append")
                }
                Button(onClick = { if (inputValue.isNotBlank()) viewModel.delete(inputValue); inputValue = "" }) {
                    Text("Delete")
                }
            }
        }
    }
}

@Composable
fun LinkedListNode(value: String, nextAddress: String) {
    val nodeWidth = 120.dp
    val nodeHeight = 60.dp

    Box(
        modifier = Modifier
            .width(nodeWidth)
            .height(nodeHeight)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF8765E4))
            .border(2.dp, Color(0xFFB392FF), RoundedCornerShape(8.dp))
    ) {
        Row(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Spacer(
                modifier = Modifier
                    .width(2.dp)
                    .fillMaxHeight()
                    .background(Color(0xFFB392FF))
            )
            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = nextAddress.removePrefix("->"),

                    color = Color.White,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
fun ConnectingArrow() {
    val arrowColor = Color(0xFFB392FF)
    Canvas(
        modifier = Modifier
            .width(40.dp)
            .height(60.dp)
    ) {
        val canvasHeight = size.height
        val canvasWidth = size.width
        val strokeWidth = 6f

        drawLine(
            color = arrowColor,
            start = Offset(x = 0f, y = canvasHeight / 2),
            end = Offset(x = canvasWidth, y = canvasHeight / 2),
            strokeWidth = strokeWidth,
            pathEffect = PathEffect.cornerPathEffect(4f)
        )

        drawLine(
            color = arrowColor,
            start = Offset(x = canvasWidth - strokeWidth * 2, y = canvasHeight / 2 - strokeWidth * 2),
            end = Offset(x = canvasWidth, y = canvasHeight / 2),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = arrowColor,
            start = Offset(x = canvasWidth - strokeWidth * 2, y = canvasHeight / 2 + strokeWidth * 2),
            end = Offset(x = canvasWidth, y = canvasHeight / 2),
            strokeWidth = strokeWidth
        )
    }
}
