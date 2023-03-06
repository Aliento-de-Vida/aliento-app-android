package com.alientodevida.alientoapp.app.compose.theme

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.util.TypedValue
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.content.res.*
import androidx.core.content.res.FontResourcesParserCompat.FontFileResourceEntry
import com.alientodevida.alientoapp.app.R

@Composable
fun typography(
  context: Context = LocalContext.current,
  defaultFontFamily: FontFamily = FontFamily.Default,
  h1: TextStyle = TextStyle(
    fontWeight = FontWeight.Light,
    fontSize = 96.sp,
    letterSpacing = (-1.5).sp
  ),
  h2: TextStyle = TextStyle(
    fontWeight = FontWeight.Light,
    fontSize = 60.sp,
    letterSpacing = (-0.5).sp
  ),
  h3: TextStyle = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 48.sp,
    letterSpacing = 0.sp
  ),
  h4: TextStyle = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 34.sp,
    letterSpacing = 0.25.sp
  ),
  h5: TextStyle = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 24.sp,
    letterSpacing = 0.sp
  ),
  h6: TextStyle = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 20.sp,
    letterSpacing = 0.15.sp
  ),
  subtitle1: TextStyle = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    letterSpacing = 0.15.sp
  ),
  subtitle2: TextStyle = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    letterSpacing = 0.1.sp
  ),
  body1: TextStyle = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    letterSpacing = 0.5.sp
  ),
  body2: TextStyle = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    letterSpacing = 0.25.sp
  ),
  button: TextStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    letterSpacing = 1.25.sp
  ),
  caption: TextStyle = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    letterSpacing = 0.4.sp
  ),
  overline: TextStyle = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp,
    letterSpacing = 1.5.sp
  ),
): Typography = context.theme.run {
  val textStyleHeadline1 = textStyle(R.attr.textAppearanceHeadline1, FontWeight.Light, (-1.5).sp, h1)
  val textStyleHeadline2 = textStyle(R.attr.textAppearanceHeadline2, FontWeight.Light, (-0.5).sp, h2)
  val textStyleHeadline3 = textStyle(R.attr.textAppearanceHeadline3, FontWeight.Normal, 0.sp, h3)
  val textStyleHeadline4 = textStyle(R.attr.textAppearanceHeadline4, FontWeight.Normal, 0.25.sp, h4)
  val textStyleHeadline5 = textStyle(R.attr.textAppearanceHeadline5, FontWeight.Normal, 0.sp, h5)
  val textStyleHeadline6 = textStyle(R.attr.textAppearanceHeadline6, FontWeight.Medium, 0.15.sp, h6)
  val textStyleSubtitle1 = textStyle(R.attr.textAppearanceSubtitle1, FontWeight.Normal, 0.15.sp, subtitle1)
  val textStyleSubtitle2 = textStyle(R.attr.textAppearanceSubtitle2, FontWeight.Medium, 0.1.sp, subtitle2)
  val textStyleBody1 = textStyle(R.attr.textAppearanceBody1, FontWeight.Normal, 0.5.sp, body1)
  val textStyleBody2 = textStyle(R.attr.textAppearanceBody2, FontWeight.Normal, 0.25.sp, body2)
  val textStyleButton = textStyle(R.attr.textAppearanceButton, FontWeight.Bold, 1.25.sp, button)
  val textStyleCaption = textStyle(R.attr.textAppearanceCaption, FontWeight.Medium, 0.4.sp, caption)
  val textStyleOverline = textStyle(R.attr.textAppearanceOverline, FontWeight.Normal, 1.5.sp, overline)

  return Typography(
    defaultFontFamily = defaultFontFamily,
    h1 = textStyleHeadline1,
    h2 = textStyleHeadline2,
    h3 = textStyleHeadline3,
    h4 = textStyleHeadline4,
    h5 = textStyleHeadline5,
    h6 = textStyleHeadline6,
    subtitle1 = textStyleSubtitle1,
    subtitle2 = textStyleSubtitle2,
    body1 = textStyleBody1,
    body2 = textStyleBody2,
    button = textStyleButton,
    caption = textStyleCaption,
    overline = textStyleOverline,
  )
}

@Composable
private fun Theme.textStyle(
  style: Int,
  fontWeight: FontWeight,
  letterSpacing: TextUnit,
  fallback: TextStyle,
): TextStyle {
  val value = TypedValue()
  return when {
    resolveAttribute(style, value, true) -> {
      style(value.data)?.use { ta: TypedArray ->
        val textColor = ta.textColor()
        val fontSize = ta.fontSize()
        // TODO: R.styleable.TextAppearance_android_textFontWeight
        val fontStyle = ta.fontStyle()
        val fontFamily = ta.fontFamily()
        // TODO: letterSpacing
        // TODO: R.styleable.TextAppearance_textAllCaps

        TextStyle(
          color = textColor ?: Color.Unspecified,
          fontSize = fontSize ?: TextUnit.Unspecified,
          fontWeight = fontWeight,
          fontStyle = fontStyle,
          fontSynthesis = null,
          fontFamily = fontFamily,
          fontFeatureSettings = null,
          letterSpacing = letterSpacing,
          baselineShift = null,
          textGeometricTransform = null,
          localeList = null,
          background = Color.Unspecified,
          textDecoration = null,
          shadow = null,
          textAlign = null,
          textDirection = null,
          lineHeight = TextUnit.Unspecified,
          textIndent = null,
        )
      } ?: fallback
    }
    else -> fallback
  }
}

private fun TypedArray.textColor(): Color? =
  color(R.styleable.TextAppearance_android_textColor)?.let(::Color)

@Composable
private fun TypedArray.fontSize(): TextUnit? =
  dimension(R.styleable.TextAppearance_android_textSize)?.let { textSize: Float ->
    with(LocalDensity.current) { textSize.toSp() }
  }

private fun TypedArray.fontStyle(): FontStyle? =
  when (string(R.styleable.TextAppearance_android_textStyle)) {
    // <flag name="normal" value="0" />
    "0", "0x0" -> FontStyle.Normal
    // <flag name="bold" value="1" />
    "1", "0x1" -> null
    // <flag name="italic" value="2" />
    "2", "0x2" -> FontStyle.Italic
    else -> null
  }

private fun TypedArray.fontFamily(): FontFamily? {
  when (string(R.styleable.TextAppearance_android_typeface)) {
    // <enum name="normal" value="0" />
    "0", "0x0" -> return FontFamily.Default
    // <enum name="sans" value="1" />
    "1", "0x1" -> return FontFamily.SansSerif
    // <enum name="serif" value="2" />
    "2", "0x2" -> return FontFamily.Serif
    // <enum name="monospace" value="3" />
    "3", "0x3" -> return FontFamily.Monospace
  }
  getFontFamilyOrNull(R.styleable.TextAppearance_android_fontFamily)?.let { return it }
  getFontFamilyOrNull(R.styleable.TextAppearance_fontFamily)?.let { return it }
  return null
}

private fun TypedArray.getFontFamilyOrNull(index: Int): FontFamily? {
  val value = TypedValue()
  if (getValue(index, value) && value.type == TypedValue.TYPE_STRING) {
    return when (value.string) {
      "sans-serif" -> FontFamily.SansSerif
      "sans-serif-thin" -> FontFamily.SansSerif
      "sans-serif-light" -> FontFamily.SansSerif
      "sans-serif-medium" -> FontFamily.SansSerif
      "sans-serif-black" -> FontFamily.SansSerif
      "serif" -> FontFamily.Serif
      "cursive" -> FontFamily.Cursive
      "monospace" -> FontFamily.Monospace
      else -> {
        if (value.resourceId != 0 && value.string.startsWith("res/font")) {
          if (value.string.endsWith(".xml")) {
            resources.parseXmlFontFamily(value.resourceId)
          } else {
            Font(value.resourceId).toFontFamily()
          }
        } else null
      }
    }
  }
  return null
}

@SuppressLint("RestrictedApi")
private fun Resources.parseXmlFontFamily(resourceId: Int): FontFamily? {
  getXml(resourceId).use { parser: XmlResourceParser ->
    @SuppressLint("RestrictedApi")
    val result = FontResourcesParserCompat.parse(parser, this)
    if (result is FontResourcesParserCompat.FontFamilyFilesResourceEntry) {
      val fonts = result.entries.map { font: FontFileResourceEntry ->
        Font(
          resId = font.resourceId,
          weight = fontWeightOf(font.weight),
          style = if (font.isItalic) FontStyle.Italic else FontStyle.Normal,
        )
      }
      return FontFamily(fonts)
    }
  }
  return null
}

private fun fontWeightOf(weight: Int): FontWeight = when (weight) {
  in 0..149 -> FontWeight.W100
  in 150..249 -> FontWeight.W200
  in 250..349 -> FontWeight.W300
  in 350..449 -> FontWeight.W400
  in 450..549 -> FontWeight.W500
  in 550..649 -> FontWeight.W600
  in 650..749 -> FontWeight.W700
  in 750..849 -> FontWeight.W800
  in 850..999 -> FontWeight.W900
  else -> FontWeight.W400
}

@SuppressLint("Recycle")
private fun Theme.style(style: Int): TypedArray? =
  try {
    obtainStyledAttributes(style, R.styleable.TextAppearance)
  } catch (ex: Exception) {
    null
  }

private fun TypedArray.color(color: Int): Int? =
  try {
    getColorOrThrow(color)
  } catch (ex: Exception) {
    null
  }

private fun TypedArray.dimension(dimension: Int): Float? =
  try {
    getDimensionOrThrow(dimension)
  } catch (ex: Exception) {
    null
  }

private fun TypedArray.string(string: Int): String? =
  try {
    getStringOrThrow(string)
  } catch (ex: Exception) {
    null
  }
