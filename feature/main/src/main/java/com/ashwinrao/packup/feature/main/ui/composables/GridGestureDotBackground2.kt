package com.ashwinrao.packup.feature.main.ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.ashwinrao.packup.feature.main.R

@Composable
fun GridGestureDotBackground2() {
    var scale by remember { mutableFloatStateOf(1f) }
    var rotation by remember { mutableFloatStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, gestureZoom, gestureRotation ->
                scale *= gestureZoom
                rotation += gestureRotation
                offset += pan
            }
        }
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale,
            rotationZ = rotation,
            translationX = offset.x,
            translationY = offset.y
        )

    // Load your small PNG/WebP (e.g., a 16x16 tile with a 2px dot)
//    val tile = imageResource(R.drawable.small_dot_tile) // PNG/WEBP
    val tile = ImageBitmap.imageResource(R.drawable.small_dot_tile)

    // Create a repeating brush
    val brush = remember(tile) {
        ShaderBrush(
            ImageShader(
                tile,
                TileMode.Repeated,
                TileMode.Repeated
            )
        )
    }

    Canvas(modifier) {
        drawRect(brush = brush) // fills with the repeating tile
        // Optional: draw a border if you want one:
         drawRect(color = Color(0xFF999999), style = Stroke(width = 2.dp.toPx()))
    }
}