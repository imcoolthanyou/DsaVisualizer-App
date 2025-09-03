package gautam.projects.dsavizualizer.data_structures.presentation.screens.DSAScreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gautam.projects.dsavizualizer.data_structures.presentation.viewmodels.StackViewModel

@Composable
fun StackScreen(stackViewModel: StackViewModel) {

    val stackItems by stackViewModel.stackState.collectAsState()
    var inputValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF161118))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Visualization Area ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Takes up all available vertical space
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF23202C)),
            contentAlignment = Alignment.BottomCenter // Aligns stack items to the bottom
        ) {
            // Draw each item in the stack
            stackItems.forEachIndexed { index, item ->
                StackNode(
                    value = item.value.toString(),
                    indexFromBottom = index
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Control Panel ---
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = inputValue,
                onValueChange = { inputValue = it },
                label = { Text("Value to Push") },
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
                            stackViewModel.push(inputValue)
                            inputValue = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8765E4))
                ) {
                    Text("Push")
                }
                Button(
                    onClick = {stackViewModel.pop() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A4458))
                ) {
                    Text("Pop")
                }
            }
        }
    }
}

@Composable
fun StackNode(value: String, indexFromBottom: Int) {
    val nodeHeight=60.dp
    val spacing=8.dp

    val targetOffSet: Dp =(nodeHeight+spacing)*indexFromBottom

    val animatedOffSet by animateDpAsState(
        targetValue = targetOffSet,
        animationSpec = tween(durationMillis = 500),
        label = "stackNodeOffSet"
    )
    Box(
        modifier = Modifier
            .offset(y = -animatedOffSet)
            .width(120.dp)
            .height(nodeHeight)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF8765E4))
            .border(2.dp, color = Color(0xFFB392FF),RoundedCornerShape(8.dp))
            .padding(8.dp),

        contentAlignment = Alignment.Center
    ){
        Text(
            text = value,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

    
