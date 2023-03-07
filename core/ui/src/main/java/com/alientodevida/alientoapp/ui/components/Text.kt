package com.alientodevida.alientoapp.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun H1(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.typography.h1.color,
  fontSize: TextUnit = MaterialTheme.typography.h1.fontSize,
  fontStyle: FontStyle? = MaterialTheme.typography.h1.fontStyle,
  fontWeight: FontWeight? = MaterialTheme.typography.h1.fontWeight,
  fontFamily: FontFamily? = MaterialTheme.typography.h1.fontFamily,
  letterSpacing: TextUnit = MaterialTheme.typography.h1.letterSpacing,
  textDecoration: TextDecoration? = MaterialTheme.typography.h1.textDecoration,
  textAlign: TextAlign? = MaterialTheme.typography.h1.textAlign,
  lineHeight: TextUnit = MaterialTheme.typography.h1.lineHeight,
  style: TextStyle = MaterialTheme.typography.h1,
  overflow: TextOverflow = TextOverflow.Ellipsis,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  allCaps: Boolean = false,
) {
  Text(
    text = if (allCaps) text.uppercase() else text,
    modifier = modifier,
    color = color,
    fontSize = fontSize,
    fontStyle = fontStyle,
    fontWeight = fontWeight,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}

@Composable
fun H2(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.typography.h2.color,
  fontSize: TextUnit = MaterialTheme.typography.h2.fontSize,
  fontStyle: FontStyle? = MaterialTheme.typography.h2.fontStyle,
  fontWeight: FontWeight? = MaterialTheme.typography.h2.fontWeight,
  fontFamily: FontFamily? = MaterialTheme.typography.h2.fontFamily,
  letterSpacing: TextUnit = MaterialTheme.typography.h2.letterSpacing,
  textDecoration: TextDecoration? = MaterialTheme.typography.h2.textDecoration,
  textAlign: TextAlign? = MaterialTheme.typography.h2.textAlign,
  lineHeight: TextUnit = MaterialTheme.typography.h2.lineHeight,
  style: TextStyle = MaterialTheme.typography.h2,
  overflow: TextOverflow = TextOverflow.Ellipsis,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  allCaps: Boolean = false,
) {
  Text(
    text = if (allCaps) text.uppercase() else text,
    modifier = modifier,
    color = color,
    fontSize = fontSize,
    fontStyle = fontStyle,
    fontWeight = fontWeight,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}

@Composable
fun H3(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.typography.h3.color,
  fontSize: TextUnit = MaterialTheme.typography.h3.fontSize,
  fontStyle: FontStyle? = MaterialTheme.typography.h3.fontStyle,
  fontWeight: FontWeight? = MaterialTheme.typography.h3.fontWeight,
  fontFamily: FontFamily? = MaterialTheme.typography.h3.fontFamily,
  letterSpacing: TextUnit = MaterialTheme.typography.h3.letterSpacing,
  textDecoration: TextDecoration? = MaterialTheme.typography.h3.textDecoration,
  textAlign: TextAlign? = MaterialTheme.typography.h3.textAlign,
  lineHeight: TextUnit = MaterialTheme.typography.h3.lineHeight,
  style: TextStyle = MaterialTheme.typography.h3,
  overflow: TextOverflow = TextOverflow.Ellipsis,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  allCaps: Boolean = false,
) {
  Text(
    text = if (allCaps) text.uppercase() else text,
    modifier = modifier,
    color = color,
    fontSize = fontSize,
    fontStyle = fontStyle,
    fontWeight = fontWeight,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}

@Composable
fun H4(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.typography.h4.color,
  fontSize: TextUnit = MaterialTheme.typography.h4.fontSize,
  fontStyle: FontStyle? = MaterialTheme.typography.h4.fontStyle,
  fontWeight: FontWeight? = MaterialTheme.typography.h4.fontWeight,
  fontFamily: FontFamily? = MaterialTheme.typography.h4.fontFamily,
  letterSpacing: TextUnit = MaterialTheme.typography.h4.letterSpacing,
  textDecoration: TextDecoration? = MaterialTheme.typography.h4.textDecoration,
  textAlign: TextAlign? = MaterialTheme.typography.h4.textAlign,
  lineHeight: TextUnit = MaterialTheme.typography.h4.lineHeight,
  style: TextStyle = MaterialTheme.typography.h4,
  overflow: TextOverflow = TextOverflow.Ellipsis,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  allCaps: Boolean = false,
) {
  Text(
    text = if (allCaps) text.uppercase() else text,
    modifier = modifier,
    color = color,
    fontSize = fontSize,
    fontStyle = fontStyle,
    fontWeight = fontWeight,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}

@Composable
fun H5(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.typography.h5.color,
  fontSize: TextUnit = MaterialTheme.typography.h5.fontSize,
  fontStyle: FontStyle? = MaterialTheme.typography.h5.fontStyle,
  fontWeight: FontWeight? = MaterialTheme.typography.h5.fontWeight,
  fontFamily: FontFamily? = MaterialTheme.typography.h5.fontFamily,
  letterSpacing: TextUnit = MaterialTheme.typography.h5.letterSpacing,
  textDecoration: TextDecoration? = MaterialTheme.typography.h5.textDecoration,
  textAlign: TextAlign? = MaterialTheme.typography.h5.textAlign,
  lineHeight: TextUnit = MaterialTheme.typography.h5.lineHeight,
  style: TextStyle = MaterialTheme.typography.h5,
  overflow: TextOverflow = TextOverflow.Ellipsis,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  allCaps: Boolean = false,
) {
  Text(
    text = if (allCaps) text.uppercase() else text,
    modifier = modifier,
    color = color,
    fontSize = fontSize,
    fontStyle = fontStyle,
    fontWeight = fontWeight,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}

@Composable
fun H6(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.typography.h6.color,
  fontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
  fontStyle: FontStyle? = MaterialTheme.typography.h6.fontStyle,
  fontWeight: FontWeight? = MaterialTheme.typography.h6.fontWeight,
  fontFamily: FontFamily? = MaterialTheme.typography.h6.fontFamily,
  letterSpacing: TextUnit = MaterialTheme.typography.h6.letterSpacing,
  textDecoration: TextDecoration? = MaterialTheme.typography.h6.textDecoration,
  textAlign: TextAlign? = MaterialTheme.typography.h6.textAlign,
  lineHeight: TextUnit = MaterialTheme.typography.h6.lineHeight,
  style: TextStyle = MaterialTheme.typography.h6,
  overflow: TextOverflow = TextOverflow.Ellipsis,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  allCaps: Boolean = false,
) {
  Text(
    text = if (allCaps) text.uppercase() else text,
    modifier = modifier,
    color = color,
    fontSize = fontSize,
    fontStyle = fontStyle,
    fontWeight = fontWeight,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}

@Composable
fun Subtitle1(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.typography.subtitle1.color,
  fontSize: TextUnit = MaterialTheme.typography.subtitle1.fontSize,
  fontStyle: FontStyle? = MaterialTheme.typography.subtitle1.fontStyle,
  fontWeight: FontWeight? = MaterialTheme.typography.subtitle1.fontWeight,
  fontFamily: FontFamily? = MaterialTheme.typography.subtitle1.fontFamily,
  letterSpacing: TextUnit = MaterialTheme.typography.subtitle1.letterSpacing,
  textDecoration: TextDecoration? = MaterialTheme.typography.subtitle1.textDecoration,
  textAlign: TextAlign? = MaterialTheme.typography.subtitle1.textAlign,
  lineHeight: TextUnit = MaterialTheme.typography.subtitle1.lineHeight,
  style: TextStyle = MaterialTheme.typography.subtitle1,
  overflow: TextOverflow = TextOverflow.Ellipsis,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  allCaps: Boolean = false,
) {
  Text(
    text = if (allCaps) text.uppercase() else text,
    modifier = modifier,
    color = color,
    fontSize = fontSize,
    fontStyle = fontStyle,
    fontWeight = fontWeight,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}

@Composable
fun Subtitle2(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.typography.subtitle2.color,
  fontSize: TextUnit = MaterialTheme.typography.subtitle2.fontSize,
  fontStyle: FontStyle? = MaterialTheme.typography.subtitle2.fontStyle,
  fontWeight: FontWeight? = MaterialTheme.typography.subtitle2.fontWeight,
  fontFamily: FontFamily? = MaterialTheme.typography.subtitle2.fontFamily,
  letterSpacing: TextUnit = MaterialTheme.typography.subtitle2.letterSpacing,
  textDecoration: TextDecoration? = MaterialTheme.typography.subtitle2.textDecoration,
  textAlign: TextAlign? = MaterialTheme.typography.subtitle2.textAlign,
  lineHeight: TextUnit = MaterialTheme.typography.subtitle2.lineHeight,
  style: TextStyle = MaterialTheme.typography.subtitle2,
  overflow: TextOverflow = TextOverflow.Ellipsis,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  allCaps: Boolean = false,
) {
  Text(
    text = if (allCaps) text.uppercase() else text,
    modifier = modifier,
    color = color,
    fontSize = fontSize,
    fontStyle = fontStyle,
    fontWeight = fontWeight,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}

@Composable
fun Body1(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.typography.body1.color,
  fontSize: TextUnit = MaterialTheme.typography.body1.fontSize,
  fontStyle: FontStyle? = MaterialTheme.typography.body1.fontStyle,
  fontWeight: FontWeight? = MaterialTheme.typography.body1.fontWeight,
  fontFamily: FontFamily? = MaterialTheme.typography.body1.fontFamily,
  letterSpacing: TextUnit = MaterialTheme.typography.body1.letterSpacing,
  textDecoration: TextDecoration? = MaterialTheme.typography.body1.textDecoration,
  textAlign: TextAlign? = MaterialTheme.typography.body1.textAlign,
  lineHeight: TextUnit = MaterialTheme.typography.body1.lineHeight,
  style: TextStyle = MaterialTheme.typography.body1,
  overflow: TextOverflow = TextOverflow.Ellipsis,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  allCaps: Boolean = false,
) {
  Text(
    text = if (allCaps) text.uppercase() else text,
    modifier = modifier,
    color = color,
    fontSize = fontSize,
    fontStyle = fontStyle,
    fontWeight = fontWeight,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}

@Composable
fun Body2(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.typography.body2.color,
  fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
  fontStyle: FontStyle? = MaterialTheme.typography.body2.fontStyle,
  fontWeight: FontWeight? = MaterialTheme.typography.body2.fontWeight,
  fontFamily: FontFamily? = MaterialTheme.typography.body2.fontFamily,
  letterSpacing: TextUnit = MaterialTheme.typography.body2.letterSpacing,
  textDecoration: TextDecoration? = MaterialTheme.typography.body2.textDecoration,
  textAlign: TextAlign? = MaterialTheme.typography.body2.textAlign,
  lineHeight: TextUnit = MaterialTheme.typography.body2.lineHeight,
  style: TextStyle = MaterialTheme.typography.body2,
  overflow: TextOverflow = TextOverflow.Ellipsis,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  allCaps: Boolean = false,
) {
  Text(
    text = if (allCaps) text.uppercase() else text,
    modifier = modifier,
    color = color,
    fontSize = fontSize,
    fontStyle = fontStyle,
    fontWeight = fontWeight,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}

@Composable
fun Button(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.typography.button.color,
  fontSize: TextUnit = MaterialTheme.typography.button.fontSize,
  fontStyle: FontStyle? = MaterialTheme.typography.button.fontStyle,
  fontWeight: FontWeight? = MaterialTheme.typography.button.fontWeight,
  fontFamily: FontFamily? = MaterialTheme.typography.button.fontFamily,
  letterSpacing: TextUnit = MaterialTheme.typography.button.letterSpacing,
  textDecoration: TextDecoration? = MaterialTheme.typography.button.textDecoration,
  textAlign: TextAlign? = MaterialTheme.typography.button.textAlign,
  lineHeight: TextUnit = MaterialTheme.typography.button.lineHeight,
  style: TextStyle = MaterialTheme.typography.button,
  overflow: TextOverflow = TextOverflow.Ellipsis,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  allCaps: Boolean = true,
) {
  Text(
    text = if (allCaps) text.uppercase() else text,
    modifier = modifier,
    color = color,
    fontSize = fontSize,
    fontStyle = fontStyle,
    fontWeight = fontWeight,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}

@Composable
fun Caption(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.typography.caption.color,
  fontSize: TextUnit = MaterialTheme.typography.caption.fontSize,
  fontStyle: FontStyle? = MaterialTheme.typography.caption.fontStyle,
  fontWeight: FontWeight? = MaterialTheme.typography.caption.fontWeight,
  fontFamily: FontFamily? = MaterialTheme.typography.caption.fontFamily,
  letterSpacing: TextUnit = MaterialTheme.typography.caption.letterSpacing,
  textDecoration: TextDecoration? = MaterialTheme.typography.caption.textDecoration,
  textAlign: TextAlign? = MaterialTheme.typography.caption.textAlign,
  lineHeight: TextUnit = MaterialTheme.typography.caption.lineHeight,
  style: TextStyle = MaterialTheme.typography.caption,
  overflow: TextOverflow = TextOverflow.Ellipsis,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  allCaps: Boolean = false,
) {
  Text(
    text = if (allCaps) text.uppercase() else text,
    modifier = modifier,
    color = color,
    fontSize = fontSize,
    fontStyle = fontStyle,
    fontWeight = fontWeight,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}

@Composable
fun Overline(
  text: String,
  modifier: Modifier = Modifier,
  color: Color = MaterialTheme.typography.overline.color,
  fontSize: TextUnit = MaterialTheme.typography.overline.fontSize,
  fontStyle: FontStyle? = MaterialTheme.typography.overline.fontStyle,
  fontWeight: FontWeight? = MaterialTheme.typography.overline.fontWeight,
  fontFamily: FontFamily? = MaterialTheme.typography.overline.fontFamily,
  letterSpacing: TextUnit = MaterialTheme.typography.overline.letterSpacing,
  textDecoration: TextDecoration? = MaterialTheme.typography.overline.textDecoration,
  textAlign: TextAlign? = MaterialTheme.typography.overline.textAlign,
  lineHeight: TextUnit = MaterialTheme.typography.overline.lineHeight,
  style: TextStyle = MaterialTheme.typography.overline,
  overflow: TextOverflow = TextOverflow.Ellipsis,
  softWrap: Boolean = true,
  maxLines: Int = Int.MAX_VALUE,
  onTextLayout: (TextLayoutResult) -> Unit = {},
  allCaps: Boolean = true,
) {
  Text(
    text = if (allCaps) text.uppercase() else text,
    modifier = modifier,
    color = color,
    fontSize = fontSize,
    fontStyle = fontStyle,
    fontWeight = fontWeight,
    fontFamily = fontFamily,
    letterSpacing = letterSpacing,
    textDecoration = textDecoration,
    textAlign = textAlign,
    lineHeight = lineHeight,
    overflow = overflow,
    softWrap = softWrap,
    maxLines = maxLines,
    onTextLayout = onTextLayout,
    style = style,
  )
}
