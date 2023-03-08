package com.alientodevida.alientoapp.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Outlined(
  modifier: Modifier = Modifier,
  backgroundColor: Color = MaterialTheme.colors.surface,
  borderWidth: Dp = 1.dp,
  borderColor: Color = MaterialTheme.colors.primary,
  shape: Shape = MaterialTheme.shapes.small,
  content: @Composable () -> Unit,
) {
  Surface(
    modifier = modifier,
    color = backgroundColor,
    border = BorderStroke(
      width = borderWidth,
      brush = SolidColor(borderColor)
    ),
    shape = shape,
    content = content,
  )
}
