package com.ashwinrao.packup.feature.main.ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun GridGestureBackground() {
    var scale by remember { mutableFloatStateOf(1f) }
    var rotation by remember { mutableFloatStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, gestureZoom, gestureRotation ->
                    scale *= gestureZoom
                    rotation += gestureRotation
                    offset += pan
                }
            }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y
                )
        ) {
            // Draw a repeating grid as a background
            val gridSize = 100f * scale
            if (gridSize > 5f) { // Hide grid lines when too small
                val startX = (offset.x % gridSize) - gridSize
                val startY = (offset.y % gridSize) - gridSize

                // Draw vertical lines
                for (x in 0..(size.width / gridSize).toInt() + 2) {
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(startX + x * gridSize, 0f),
                        end = Offset(startX + x * gridSize, size.height)
                    )
                }

                // Draw horizontal lines
                for (y in 0..(size.height / gridSize).toInt() + 2) {
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(0f, startY + y * gridSize),
                        end = Offset(size.width, startY + y * gridSize)
                    )
                }
            }
        }
    }
}
