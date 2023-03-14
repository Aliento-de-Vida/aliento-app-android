package com.alientodevida.alientoapp.designsystem.theme

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources.Theme
import android.content.res.TypedArray
import android.util.TypedValue
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.res.getDimensionOrThrow
import androidx.core.content.res.getStringOrThrow
import androidx.core.content.res.use
import com.alientodevida.alientoapp.designsystem.R

@Composable
fun shapes(
    context: Context = LocalContext.current,
    small: CornerBasedShape = RoundedCornerShape(4.dp),
    medium: CornerBasedShape = RoundedCornerShape(4.dp),
    large: CornerBasedShape = RoundedCornerShape(0.dp),
): Shapes = context.theme.run {
    val smallShapeAppearance = shape(R.attr.shapeAppearanceSmallComponent, small)
    val mediumShapeAppearance = shape(R.attr.shapeAppearanceMediumComponent, medium)
    val largeShapeAppearance = shape(R.attr.shapeAppearanceLargeComponent, large)

    Shapes(
        small = smallShapeAppearance,
        medium = mediumShapeAppearance,
        large = largeShapeAppearance,
    )
}

@Composable
private fun Theme.shape(
    style: Int,
    fallback: CornerBasedShape,
): CornerBasedShape {
    val value = TypedValue()
    return when {
        resolveAttribute(style, value, true) -> {
            style(value.data)?.use { ta: TypedArray ->
                val corners = ta.corner(R.styleable.ShapeAppearance_cornerSize)
                val topStartCorner = ta.corner(R.styleable.ShapeAppearance_cornerSizeTopLeft)
                val topEndCorner = ta.corner(R.styleable.ShapeAppearance_cornerSizeTopRight)
                val bottomEndCorner = ta.corner(R.styleable.ShapeAppearance_cornerSizeBottomRight)
                val bottomStartCorner = ta.corner(R.styleable.ShapeAppearance_cornerSizeBottomLeft)

                ta.cornerFamily(
                    topStart = CornerSize(topStartCorner ?: corners ?: 0.dp),
                    topEnd = CornerSize(topEndCorner ?: corners ?: 0.dp),
                    bottomEnd = CornerSize(bottomEndCorner ?: corners ?: 0.dp),
                    bottomStart = CornerSize(bottomStartCorner ?: corners ?: 0.dp),
                )
            } ?: fallback
        }
        else -> fallback
    }
}

@Composable
private fun TypedArray.corner(corner: Int): Dp? =
    dimension(corner)?.let { size: Float ->
        with(LocalDensity.current) { size.toDp() }
    }

private fun TypedArray.cornerFamily(
    topStart: CornerSize,
    topEnd: CornerSize,
    bottomEnd: CornerSize,
    bottomStart: CornerSize,
): CornerBasedShape? =
    when (string(R.styleable.ShapeAppearance_cornerFamily)) {
        // <enum name="rounded" value="0"/>
        "0", "0x0" -> RoundedCornerShape(topStart, topEnd, bottomEnd, bottomStart)
        // <enum name="cut" value="1"/>
        "1", "0x1" -> CutCornerShape(topStart, topEnd, bottomEnd, bottomStart)
        else -> null
    }

@SuppressLint("Recycle")
private fun Theme.style(style: Int): TypedArray? =
    try {
        obtainStyledAttributes(style, R.styleable.ShapeAppearance)
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
