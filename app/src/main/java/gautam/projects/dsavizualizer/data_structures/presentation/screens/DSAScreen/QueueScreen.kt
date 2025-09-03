package gautam.projects.dsavizualizer.data_structures.presentation.screens.DSAScreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import gautam.projects.dsavizualizer.data_structures.presentation.viewmodels.QueueViewModel

@Composable
fun QueueScreen(viewModel: QueueViewModel = viewModel()) {
    val queueState by viewModel.queueState.collectAsState()
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
            contentAlignment = Alignment.CenterStart
        ) {
            queueState.forEachIndexed { index, item ->
                QueueNode(
                    value = item.value.toString(),
                    index = index
                )
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
                label = { Text("Value to Enqueue") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFB392FF),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFFB392FF),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFFB392FF),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = {
                        if (inputValue.isNotBlank()) {
                            viewModel.enqueue(inputValue)
                            inputValue = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8765E4))
                ) {
                    Text("Enqueue")
                }
                Button(
                    onClick = { viewModel.dequeue() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A4458))
                ) {
                    Text("Dequeue")
                }
            }
        }
    }
}

@Composable
fun QueueNode(value: String, index: Int) {
    val nodeWidth = 90.dp
    val nodeHeight = 60.dp
    val spacing = 8.dp

    val targetOffset: Dp = (nodeWidth + spacing) * index

    val animatedOffset by animateDpAsState(
        targetValue = targetOffset,
        animationSpec = tween(durationMillis = 500),
        label = "queueNodeOffset"
    )

    Box(
        modifier = Modifier
            .offset(x = animatedOffset)
            .width(nodeWidth)
            .height(nodeHeight)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF8765E4))
            .border(2.dp, color = Color(0xFFB392FF), RoundedCornerShape(8.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
