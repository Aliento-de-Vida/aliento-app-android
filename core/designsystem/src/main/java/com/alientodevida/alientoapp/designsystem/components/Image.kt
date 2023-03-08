package com.alientodevida.alientoapp.designsystem.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.imageLoader

@Composable
fun ImageWithShimmering(
  modifier: Modifier = Modifier,
  url: String?,
  description: String? = null,
  alignment: Alignment = Alignment.Center,
  contentScale: ContentScale = ContentScale.Crop,
  alpha: Float = DefaultAlpha,
  colorFilter: ColorFilter? = null,
) {
  SubcomposeAsyncImage(
    modifier = modifier,
    model = url,
    imageLoader = LocalContext.current.imageLoader,
    loading = { ShimmerAnimation() },
    error = { ShimmerAnimation() },
    contentDescription = description,
    alignment = alignment,
    contentScale = contentScale,
    alpha = alpha,
    colorFilter = colorFilter,
  )
}

private val ShimmerColorShades = listOf(
  Color.LightGray.copy(0.9f),
  Color.LightGray.copy(0.2f),
  Color.LightGray.copy(0.9f),
)

@Composable
private fun ShimmerAnimation() {
  val transition = rememberInfiniteTransition()
  val translateAnim by transition.animateFloat(
    initialValue = 0f,
    targetValue = 1000f,
    animationSpec = infiniteRepeatable(
      tween(durationMillis = 1200, easing = FastOutSlowInEasing),
      RepeatMode.Reverse
    )
  )
  
  val brush = Brush.linearGradient(
    colors = ShimmerColorShades,
    start = Offset(10f, 10f),
    end = Offset(translateAnim, translateAnim)
  )
  
  Spacer(
    modifier = Modifier
      .fillMaxSize()
      .background(brush = brush)
  )
}