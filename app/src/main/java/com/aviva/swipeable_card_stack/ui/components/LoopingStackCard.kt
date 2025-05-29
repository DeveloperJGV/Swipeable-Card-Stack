package com.aviva.swipeable_card_stack.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import com.aviva.swipeable_card_stack.model.ColorDemoModel
import kotlinx.coroutines.launch

@Composable
fun LoopingStackCard(
    item: ColorDemoModel,
    index: Int,
    count: Int,
    isTop: Boolean,
    maxTranslationWidth: Dp,
    onSwiped: () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    val scale = 1f - (index * 0.07f).coerceAtMost(count * 0.07f)
    val extraOffset = (index * 20).dp

    val cardWidth = 149.dp
    val cardHeight = 149.dp

    val density = LocalDensity.current
    val rotationY = (-30f * (offsetX / with(density) { cardWidth.toPx() })).coerceIn(-30f, 0f)
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .offset(x = extraOffset + offsetX.dp)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationY = rotationY,
                cameraDistance = 12 * density.density
            )
            .size(cardWidth, cardHeight)
            .zIndex((count - index).toFloat())
            .background(item.color, RoundedCornerShape(20.dp))
            .border(4.dp, Color.White, RoundedCornerShape(20.dp))
            .pointerInput(isTop) {
                if (isTop) {
                    detectDragGestures(
                        onDragEnd = {
                            val widthPx = with(density) { cardWidth.toPx() }
                            coroutineScope.launch {
                                if (offsetX < -widthPx * 0.65f) {
                                    val anim = Animatable(offsetX)
                                    anim.animateTo(-widthPx)
                                    onSwiped()
                                    offsetX = 0f
                                } else {
                                    val anim = Animatable(offsetX)
                                    anim.animateTo(0f)
                                    offsetX = 0f
                                }
                            }
                        }
                    ) { change, dragAmount ->
                        offsetX = (offsetX + dragAmount.x)
                            .coerceIn(-with(density) { maxTranslationWidth.toPx() }, 0f)
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
