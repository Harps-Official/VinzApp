package com.example.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush

object MelofyBackgrounds {
    val Morning = Brush.verticalGradient(
        colors = listOf(Color(0xFF81D4FA), Color(0xFFE1F5FE))
    )
    val Afternoon = Brush.verticalGradient(
        colors = listOf(Color(0xFF29B6F6), Color(0xFF81D4FA))
    )
    val Evening = Brush.verticalGradient(
        colors = listOf(Color(0xFFFF9800), Color(0xFF9C27B0))
    )
    val Night = Brush.verticalGradient(
        colors = listOf(Color(0xFF0D47A1), Color(0xFF000000))
    )
    
    fun getBackgroundForTime(): Brush {
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 6..11 -> Morning
            in 12..16 -> Afternoon
            in 17..19 -> Evening
            else -> Night
        }
    }
}
