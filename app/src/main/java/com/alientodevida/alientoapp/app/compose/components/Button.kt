package com.alientodevida.alientoapp.app.compose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FilledButton(
  text: String,
  modifier: Modifier = Modifier,
  textModifier: Modifier = Modifier,
  textStyle: TextStyle = MaterialTheme.typography.button,
  textColor: Color = MaterialTheme.colors.onPrimary,
  enabled: Boolean = true,
  colors: ButtonColors = ButtonDefaults.buttonColors(),
  shape: Shape = MaterialTheme.shapes.small,
  onClick: () -> Unit,
) {
  Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    colors = colors,
    shape = shape
  ) {
    Button(
      text = text,
      modifier = textModifier.padding(vertical = 8.dp),
      style = textStyle,
      color = textColor,
    )
  }
}

@Composable
fun FlatButton(
  text: String,
  modifier: Modifier = Modifier,
  textModifier: Modifier = Modifier,
  textStyle: TextStyle = MaterialTheme.typography.button,
  textColor: Color = MaterialTheme.colors.primary,
  textAlign: TextAlign = TextAlign.Center,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {
  TextButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
  ) {
    Button(
      text = text,
      modifier = textModifier.padding(vertical = 8.dp),
      style = textStyle,
      textAlign = textAlign,
      color = textColor,
    )
  }
}
