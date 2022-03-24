package com.alientodevida.alientoapp.app.compose.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.Icon as ComposeIcon

@Composable
fun Icon(
  @DrawableRes
  icon: Int,
  @StringRes
  contentDescription: Int,
  tint: Color,
  modifier: Modifier = Modifier,
) = Icon(
  icon = icon,
  contentDescription = stringResource(contentDescription),
  tint = tint,
  modifier = modifier,
)

@Composable
fun Icon(
  @DrawableRes
  icon: Int,
  contentDescription: String,
  tint: Color,
  modifier: Modifier = Modifier,
) = ComposeIcon(
  painter = painterResource(icon),
  contentDescription = contentDescription,
  tint = tint,
  modifier = modifier.size(24.dp),
)

@Composable
fun Icon(
  icon: ImageVector,
  @StringRes
  contentDescription: Int,
  tint: Color,
  modifier: Modifier = Modifier,
) = Icon(
  icon = icon,
  contentDescription = stringResource(contentDescription),
  tint = tint,
  modifier = modifier,
)

@Composable
fun Icon(
  icon: ImageVector,
  contentDescription: String,
  tint: Color,
  modifier: Modifier = Modifier,
) = ComposeIcon(
  imageVector = icon,
  contentDescription = contentDescription,
  tint = tint,
  modifier = modifier.size(24.dp),
)

@Composable
fun ClickableIcon(
  @DrawableRes
  icon: Int,
  @StringRes
  contentDescription: Int,
  tint: Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) = ClickableIcon(
  icon = icon,
  contentDescription = stringResource(contentDescription),
  tint = tint,
  modifier = modifier,
  onClick = onClick,
)

@Composable
fun ClickableIcon(
  @DrawableRes
  icon: Int,
  contentDescription: String,
  tint: Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) {
  IconButton(
    onClick = onClick,
    modifier = modifier
      .size(48.dp)
      .padding(12.dp),
  ) {
    ComposeIcon(
      painter = painterResource(icon),
      contentDescription = contentDescription,
      tint = tint,
      modifier = Modifier.size(24.dp),
    )
  }
}

@Composable
fun ClickableIcon(
  icon: ImageVector,
  @StringRes
  contentDescription: Int,
  tint: Color,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  onClick: () -> Unit,
) = ClickableIcon(
  icon = icon,
  contentDescription = stringResource(contentDescription),
  tint = tint,
  modifier = modifier,
  enabled = enabled,
  onClick = onClick,
)

@Composable
fun ClickableIcon(
  icon: ImageVector,
  contentDescription: String,
  tint: Color,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {
  IconButton(
    onClick = onClick,
    enabled = enabled,
    modifier = modifier
      .size(48.dp)
      .padding(12.dp),
  ) {
    ComposeIcon(
      imageVector = icon,
      contentDescription = contentDescription,
      tint = tint,
      modifier = Modifier.size(24.dp),
    )
  }
}
