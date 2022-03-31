package com.alientodevida.alientoapp.app.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun Gradient(
  modifier: Modifier = Modifier,
  startColor: Color = Color(0x00FFFFFF),
  endColor: Color = Color(0xA6000000),
  content: @Composable BoxScope.() -> Unit,
) {
  Box(
    modifier
      .background(
        brush = Brush.verticalGradient(colors = listOf(startColor, endColor))
      ),
    content = content,
  )
}