package com.alientodevida.alientoapp.ui.theme

import android.content.Context
import android.content.res.Resources.Theme
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.material.R

@Composable
fun colors(
  context: Context = LocalContext.current,

  primary: Color? = null,
  primaryFallback: Color? = null,
  primaryVariant: Color? = null,
  primaryVariantFallback: Color? = null,
  onPrimary: Color? = null,
  onPrimaryFallback: Color? = null,

  secondary: Color? = null,
  secondaryFallback: Color? = null,
  secondaryVariant: Color? = null,
  secondaryVariantFallback: Color? = null,
  onSecondary: Color? = null,
  onSecondaryFallback: Color? = null,

  surface: Color? = null,
  surfaceFallback: Color? = null,
  onSurface: Color? = null,
  onSurfaceFallback: Color? = null,

  background: Color? = null,
  backgroundFallback: Color? = null,
  onBackground: Color? = null,
  onBackgroundFallback: Color? = null,

  error: Color? = null,
  errorFallback: Color? = null,
  onError: Color? = null,
  onErrorFallback: Color? = null,

  isLight: Boolean = false,
): Colors = context.theme.run {
  val value = TypedValue()

  val colorPrimary = color(R.attr.colorPrimary, value, primary, primaryFallback)
  val colorPrimaryVariant = color(R.attr.colorPrimaryVariant, value, primaryVariant, primaryVariantFallback)
  val colorOnPrimary = color(R.attr.colorOnPrimary, value, onPrimary, onPrimaryFallback)

  val colorSecondary = color(R.attr.colorSecondary, value, secondary, secondaryFallback)
  val colorSecondaryVariant = color(R.attr.colorSecondaryVariant, value, secondaryVariant, secondaryVariantFallback)
  val colorOnSecondary = color(R.attr.colorOnSecondary, value, onSecondary, onSecondaryFallback)

  val colorSurface = color(R.attr.colorSurface, value, surface, surfaceFallback)
  val colorOnSurface = color(R.attr.colorOnSurface, value, onSurface, onSurfaceFallback)

  val colorBackground = color(R.attr.backgroundColor, value, background, backgroundFallback)
  val colorOnBackground = color(R.attr.colorOnBackground, value, onBackground, onBackgroundFallback)

  val colorError = color(androidx.appcompat.R.attr.colorError, value, error, errorFallback)
  val colorOnError = color(R.attr.colorOnError, value, onError, onErrorFallback)

  Colors(
    primary = colorPrimary,
    primaryVariant = colorPrimaryVariant,
    onPrimary = colorOnPrimary,

    secondary = colorSecondary,
    secondaryVariant = colorSecondaryVariant,
    onSecondary = colorOnSecondary,

    surface = colorSurface,
    onSurface = colorOnSurface,

    background = colorBackground,
    onBackground = colorOnBackground,

    error = colorError,
    onError = colorOnError,

    isLight = isLight,
  )
}

private fun Theme.color(
  @AttrRes
  attribute: Int,
  value: TypedValue,
  color: Color? = null,
  default: Color? = null,
): Color = when {
  color != null -> color
  resolveAttribute(attribute, value, true) -> Color(value.data)
  default != null -> default
  else -> throw Exception("No Color found for attribute=$attribute")
}
