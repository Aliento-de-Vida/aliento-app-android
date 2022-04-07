package com.alientodevida.alientoapp.app.compose.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
  colors: Colors = colors(),
  typography: Typography = typography(),
  shapes: Shapes = shapes(),
  content: @Composable () -> Unit,
) = MaterialTheme(
  colors = colors,
  typography = typography,
  shapes = shapes,
  content = content,
)