package com.aviva.swipeable_card_stack.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.aviva.swipeable_card_stack.model.ColorDemoModel
import com.aviva.swipeable_card_stack.ui.components.LoopingStack

@Composable
fun CardStackScreen() {
    val demoItems = listOf(
        ColorDemoModel(1, Color(0xFFE57373), "Rojo"),
        ColorDemoModel(2, Color(0xFF81C784), "Verde"),
        ColorDemoModel(3, Color(0xFF64B5F6), "Azul"),
        ColorDemoModel(4, Color(0xFFBA68C8), "Magenta"),
    )

    LoopingStack(items = demoItems)
}

@Preview(showBackground = true)
@Composable
fun PreviewCardStack() {
    CardStackScreen()
}
