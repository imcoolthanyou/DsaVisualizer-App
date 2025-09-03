package gautam.projects.dsavizualizer.data_structures.presentation.screens.DSAScreen

import android.graphics.Paint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import gautam.projects.dsavizualizer.data_structures.presentation.viewmodels.BinarySearchTreeViewModel

@Composable
fun BinarySearchTreeScreen(
    viewModel: BinarySearchTreeViewModel = viewModel()
) {

    val bstState by viewModel.binarySearchTreeState.collectAsState()
    var inputValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF161118))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Visualization Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF23202C))
                .padding(16.dp), // Add padding for better spacing from edges
            contentAlignment = Alignment.TopCenter // Align tree to the top-center
        ) {
            val nodeRadius = 24.dp

            // Animate each node's position for smooth transitions
            val animatedNodes = bstState.map { node ->
                val animatedX by animateDpAsState(targetValue = node.xOffSet, animationSpec = tween(500), label = "xOffSet")
                val animatedY by animateDpAsState(targetValue = node.yOffSet, animationSpec = tween(500), label = "yOffSet")
                // Animate parent positions to make lines move smoothly
                val animatedParentX by animateDpAsState(targetValue = node.parentXOffSet ?: animatedX, animationSpec = tween(500), label = "parentX")
                val animatedParentY by animateDpAsState(targetValue = node.parentYOffSet ?: animatedY, animationSpec = tween(500), label = "parentY")

                node.copy(
                    xOffSet = animatedX,
                    yOffSet = animatedY,
                    parentXOffSet = if (node.parentXOffSet != null) animatedParentX else null,
                    parentYOffSet = if (node.parentYOffSet != null) animatedParentY else null
                )
            }

            Canvas(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Allow scrolling for deep trees
            ) {
                val radiusPx = nodeRadius.toPx()

                // 1. Draw all the lines first so they appear behind the nodes
                animatedNodes.forEach { node ->
                    if (node.parentXOffSet != null && node.parentYOffSet != null) {
                        drawLine(
                            color = Color(0xFFB392FF).copy(alpha = 0.7f),
                            start = Offset(node.parentXOffSet!!.toPx() + radiusPx, node.parentYOffSet!!.toPx() + radiusPx),
                            end = Offset(node.xOffSet.toPx() + radiusPx, node.yOffSet.toPx() + radiusPx),
                            strokeWidth = 4f
                        )
                    }
                }

                // 2. Draw all the nodes and their text values on top of the lines
                animatedNodes.forEach { node ->
                    val centerOffset = Offset(node.xOffSet.toPx() + radiusPx, node.yOffSet.toPx() + radiusPx)

                    drawCircle(
                        color = Color(0xFF8765E4),
                        radius = radiusPx,
                        center = centerOffset
                    )

                    drawIntoCanvas {
                        val paint = Paint().apply {
                            color = android.graphics.Color.WHITE
                            textAlign = Paint.Align.CENTER
                            textSize = 16.sp.toPx()
                            isAntiAlias = true
                        }
                        it.nativeCanvas.drawText(
                            node.value.toString(),
                            centerOffset.x,
                            centerOffset.y - ((paint.descent() + paint.ascent()) / 2), // Centers text vertically
                            paint
                        )
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
                label = { Text("Value to Insert/Delete") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFB392FF),
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFFB392FF),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color(0xFFB392FF),
                    // ... other colors
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { inputValue.toIntOrNull()?.let { viewModel.insert(it) }; inputValue = "" }) {
                    Text("Insert")
                }
                Button(onClick = { inputValue.toIntOrNull()?.let { viewModel.delete(it) }; inputValue = "" }) {
                    Text("Delete")
                }
            }
        }
    }
}



