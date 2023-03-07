package com.alientodevida.alientoapp.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalExpandedOnlyBottomSheetLayout(
  sheetContent: @Composable ColumnScope.() -> Unit,
  modifier: Modifier = Modifier,
  sheetState: ModalBottomSheetState =
    rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
  sheetShape: Shape = MaterialTheme.shapes.large,
  sheetElevation: Dp = ModalBottomSheetDefaults.Elevation,
  sheetBackgroundColor: Color = MaterialTheme.colors.surface,
  sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
  scrimColor: Color = ModalBottomSheetDefaults.scrimColor,
  content: @Composable () -> Unit
) {
  LaunchedEffect(sheetState) {
    snapshotFlow { sheetState.isAnimationRunning }
      .collect {
        with(sheetState) {
          val isOpening =
            currentValue == ModalBottomSheetValue.Hidden && targetValue == ModalBottomSheetValue.HalfExpanded
          val isClosing =
            currentValue == ModalBottomSheetValue.Expanded && targetValue == ModalBottomSheetValue.HalfExpanded
          when {
            isOpening -> animateTo(ModalBottomSheetValue.Expanded)
            isClosing -> animateTo(ModalBottomSheetValue.Hidden)
          }
        }
      }
  }
  
  ModalBottomSheetLayout(
    sheetContent = sheetContent,
    modifier = modifier,
    sheetState = sheetState,
    sheetShape = sheetShape,
    sheetElevation = sheetElevation,
    sheetBackgroundColor = sheetBackgroundColor,
    sheetContentColor = sheetContentColor,
    scrimColor = scrimColor,
    content = content,
  )
}
