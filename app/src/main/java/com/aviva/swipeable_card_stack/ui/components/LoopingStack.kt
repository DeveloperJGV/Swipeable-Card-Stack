package com.aviva.swipeable_card_stack.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aviva.swipeable_card_stack.model.ColorDemoModel

@Composable
fun LoopingStack(
    items: List<ColorDemoModel>,
    visibleCardsCount: Int = 2,
    maxTranslationWidth: Dp = 149.dp
) {
    var rotation by remember { mutableStateOf(0) }

    // Rotar lista circularmente según el índice de rotación
    val rotatedItems = remember(rotation, items) {
        val size = items.size
        if (size == 0) items
        else List(size) { i -> items[(i + rotation) % size] }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        rotatedItems.take(visibleCardsCount).reversed().forEachIndexed { index, item ->
            LoopingStackCard(
                item = item,
                index = index,
                count = visibleCardsCount,
                isTop = index == 0,
                maxTranslationWidth = maxTranslationWidth,
                onSwiped = { rotation++ }
            )
        }
    }
}
