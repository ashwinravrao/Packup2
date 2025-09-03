package com.ashwinrao.packup.intake.composable

import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.ashwinrao.packup.intake.R

@Composable
fun ItemImagePreview(
    modifier: Modifier = Modifier,
    uri: Uri? = null,
) {
    val shape = RoundedCornerShape(24.dp)

    var isExpanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val haptic = LocalHapticFeedback.current

    Card(
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = {
                isExpanded = !isExpanded
                haptic.performHapticFeedback(HapticFeedbackType.GestureEnd)
            }
        ),
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = 0.65f,
                        stiffness = 800f
                    )
                )
        ) {
            if (uri != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(uri)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Captured Item Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(if (isExpanded) 600.dp else 200.dp)
                        .fillMaxWidth()
                        .clip(shape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .height(if (isExpanded) 600.dp else 200.dp)
                        .fillMaxWidth()
                        .clip(shape)
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_coat_stand),
                        contentDescription = "Placeholder",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
@Preview(device = PIXEL_7_PRO, showSystemUi = true)
private fun ItemImagePreviewPreview() {
    PackupTheme {
        ItemImagePreview()
    }
}