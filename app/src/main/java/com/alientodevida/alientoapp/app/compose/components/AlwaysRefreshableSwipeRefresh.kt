package com.alientodevida.alientoapp.app.compose.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

/*
SwipeRefresh needs a scrollable content in order to be able to swipe to refresh
*/
@Composable
fun AlwaysRefreshableSwipeRefresh(
  items: List<Any>,
  isRefreshing: Boolean,
  onRefresh: () -> Unit,
  content: @Composable () -> Unit,
) {
  SwipeRefresh(
    state = rememberSwipeRefreshState(isRefreshing),
    onRefresh = onRefresh,
  ) {
    if (items.isEmpty()) {
      Spacer(
        Modifier
          .fillMaxSize()
          .verticalScroll(
            rememberScrollState()
          )
      )
    } else {
      content()
    }
  }
}