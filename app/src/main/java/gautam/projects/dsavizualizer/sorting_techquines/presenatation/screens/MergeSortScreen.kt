package gautam.projects.dsavizualizer.sorting_techquines.presenatation.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import gautam.projects.dsavizualizer.R
import gautam.projects.dsavizualizer.sorting_techquines.presenatation.viewmodels.MergeSortViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.roundToInt

// --- IMPORTANT NOTE ---
// To slow down the sorting steps, find your startSort() function
// in your MergeSortViewModel and change the delay.
// A good value is 1500L (1.5 seconds per step).
//
// viewModelScope.launch {
//     for (i in history.indices) {
//         _currentStepIndex.value = i
//         delay(1500L) // <-- SLOW DOWN THE STEPS HERE
//     }
// }

@Composable
fun MergeSortScreen(viewModel: MergeSortViewModel = viewModel()) {
    val isSorting by viewModel.isSorting.collectAsState()

    if (isSorting) {
        ShowMergeSortAnimation(viewModel = viewModel)
    } else {
        ShowDataEntry(viewModel = viewModel)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowMergeSortAnimation(viewModel: MergeSortViewModel) {
    val sortingSteps by viewModel.sortingSteps.collectAsState()
    val currentStepIndex by viewModel.currentStepIndex.collectAsState()

    if (sortingSteps.isEmpty()) return

    val currentList = if (currentStepIndex < sortingSteps.size) sortingSteps[currentStepIndex] else sortingSteps.last()
    val maxVal = currentList.maxOrNull() ?: 1
    val isFinished = currentStepIndex >= sortingSteps.size - 1

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF161118)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text(text = "Merge Sort", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isFinished) "Sorted!" else "Step ${currentStepIndex + 1} of ${sortingSteps.size}",
                color = if (isFinished) Color.Green else Color.LightGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            modifier = Modifier.height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isFinished) {
                // Show the final "at rest" pose (the last frame of the sheet)
                StaticSpriteFrame(frameIndex = 8)
            } else {
                LoopingSpriteAnimation()
            }
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            itemsIndexed(
                items = currentList,
                key = { index, value -> "$value-$index" }
            ) { index, number ->
                SortBar(
                    value = number,
                    maxValue = maxVal,
                    modifier = Modifier.animateItem(
                        placementSpec = tween(500)
                    )
                )
            }
        }

        Button(
            onClick = { viewModel.reset() },
            modifier = Modifier.padding(bottom = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A4458))
        ) {
            Text("Reset")
        }
    }
}

@Composable
fun LoopingSpriteAnimation() {
    val spriteSheet = painterResource(id = R.drawable.ninja_throw_sheet)
    val frameCount = 9

    // This is the new, more robust animation engine.
    // It creates a value that animates from 0f to 9f continuously.
    val infiniteTransition = rememberInfiniteTransition(label = "sprite_loop")
    val currentFrame by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = frameCount.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing), // 1 second per loop
            repeatMode = RepeatMode.Restart
        ), label = "sprite_frame"
    )

    StaticSpriteFrame(frameIndex = currentFrame.roundToInt() % frameCount)
}

@Composable
fun StaticSpriteFrame(frameIndex: Int) {
    val spriteSheet = painterResource(id = R.drawable.ninja_throw_sheet)
    val colCount = 3
    val rowCount = 3
    val frameSize = 120.dp

    val row = frameIndex / colCount
    val col = frameIndex % colCount

    Box(
        modifier = Modifier
            .size(frameSize)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = spriteSheet,
            contentDescription = "Sprite character",
            contentScale = ContentScale.None,
            modifier = Modifier
                .size(width = (frameSize * colCount), height = (frameSize * rowCount))
                .offset {
                    IntOffset(
                        x = -(col * frameSize.roundToPx()),
                        y = -(row * frameSize.roundToPx())
                    )
                }
        )
    }
}


@Composable
fun SortBar(value: Int, maxValue: Int, modifier: Modifier = Modifier) {
    val maxBarHeight = 250.dp
    val minBarHeight = 20.dp
    val targetHeight = if (maxValue > 0) {
        minBarHeight + (maxBarHeight - minBarHeight) * (value.toFloat() / maxValue.toFloat())
    } else {
        minBarHeight
    }
    val animatedHeight by animateDpAsState(
        targetValue = targetHeight.coerceIn(minBarHeight, maxBarHeight),
        animationSpec = tween(durationMillis = 300),
        label = "barHeight"
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(animatedHeight)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(Color(0xFF8765E4))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = value.toString(), color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShowDataEntry(viewModel: MergeSortViewModel) {
    val userList by viewModel.userList.collectAsState()
    var userInput by remember { mutableStateOf("") }
    val context = LocalContext.current



    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF161118)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Prepare for Sorting",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp)
        )

        // Show the static "ready" pose of the character before sorting
        Box(
            modifier = Modifier.height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            // We show the first frame (the ready pose)
            StaticSpriteFrame(frameIndex = 0)
        }

        Box(
            modifier = Modifier.fillMaxWidth().weight(1f).clip(RoundedCornerShape(8.dp)).background(Color(0xFF231C31)).padding(8.dp)
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                userList.forEach { number ->
                    AnimatedVisibility(visible = true, enter = fadeIn(tween(300))) {
                        DataEntryBox(value = number)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Enter a number", color = Color.Gray) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFB392FF),
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color(0xFFB392FF),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = {
                    viewModel.addNumber(userInput.toInt())
                    userInput = ""
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8765E4))
            ) {
                Text("Add", color = Color.White)
            }
            Button(
                onClick = { viewModel.sampleNumber() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A4458))
            ) {
                Text("Sample", color = Color.White)
            }
            Button(
                onClick = { viewModel.startSort() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7))
            ) {
                Text("Sort", color = Color.White)
            }
        }
    }
}

@Composable
fun DataEntryBox(value: Int) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF2A2437))
            .border(1.dp, color = Color(0xFFB392FF), RoundedCornerShape(8.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = value.toString(), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

