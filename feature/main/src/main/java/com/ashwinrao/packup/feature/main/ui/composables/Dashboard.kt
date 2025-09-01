package com.ashwinrao.packup.feature.main.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashwinrao.packup.domain.model.Item
import com.ashwinrao.packup.domain.model.ItemLocationType
import com.ashwinrao.packup.feature.common.theme.PackupTheme
import com.ashwinrao.packup.feature.common.theme.primaryDark

@Composable
fun Dashboard(
    modifier: Modifier,
    items: List<Item>,
) {
    Column(modifier = modifier) {
        Text(
            text = "ROOMS",
            modifier = Modifier.padding(start = 28.dp, bottom = 16.dp, top = 16.dp),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelMedium

        )
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(items.size) { index ->
                val item = items[index]
                ExpandingItemCard(modifier = Modifier, item)
            }
        }
    }
}

@Composable
fun ExpandingItemCard(modifier: Modifier = Modifier, item: Item) {
    var isExpanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .indication(interactionSource = interactionSource, indication = null)
            .pointerInput(Unit) {
                detectTapGestures(
//                    onTap = { /* open room details */ },
                    onLongPress = { isExpanded = !isExpanded }
                )
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                        .padding(all = 16.dp)
                ) {
                    item.name?.let { name ->
                        Text(
                            text = name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        (item.locationType as? ItemLocationType.Room)?.let { room ->
                            Text(
                                text = room.name,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            if (isExpanded) {
                HorizontalDivider(
                    modifier = Modifier.shadow(elevation = 6.dp),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = primaryDark.copy(alpha = 0.3f))
                        .padding(all = 16.dp),
                ) {
                    item.description?.let { description ->
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Preview(device = PIXEL_7_PRO, showSystemUi = true)
@Composable
fun ExpandingItemCardPreview() {
    PackupTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val items = Item.generated
            ExpandingItemCard(modifier = Modifier, items[0])
        }
    }
}