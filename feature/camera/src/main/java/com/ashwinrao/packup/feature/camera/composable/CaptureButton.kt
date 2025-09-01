package com.ashwinrao.packup.feature.camera.composable

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp

@Composable
fun CaptureButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    var isPressed by remember { mutableStateOf(false) }
    val haptics = LocalHapticFeedback.current

    val animatedButtonScale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = tween(
            durationMillis = 150,
            easing = EaseInOut
        ),
        label = "animated capture button scale"
    )

    Box(
        modifier = modifier
            .size(84.dp)
            .scale(animatedButtonScale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        haptics.performHapticFeedback(HapticFeedbackType.Confirm)
                        isPressed = true
                        val released = tryAwaitRelease()
                        isPressed = false
                        // only trigger onClick if the press was properly released
                        // mimics Pixel camera app behavior on click + drag
                        if (released && enabled) onClick()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // canvas for finer control of the outer ring shape
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.White.copy(alpha = 0.9f),
                radius = size.minDimension / 2,
                style = Stroke(width = 4.dp.toPx())
            )
        }

        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
        )
    }
}