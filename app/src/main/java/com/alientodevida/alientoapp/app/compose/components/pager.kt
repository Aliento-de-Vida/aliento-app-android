package com.alientodevida.alientoapp.app.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun <T>Pager(
  items: List<T>,
  goToItem: (T) -> Unit,
  page: @Composable (T) -> Unit,
) {
  Box(Modifier.height(250.dp)) {
    val pagerState = rememberPagerState()
    
    LaunchedEffect(pagerState.currentPage) {
      launch {
        delay(3000)
        with(pagerState) {
          val target = if (currentPage < pageCount - 1) currentPage + 1 else 0
          animateScrollToPage(page = target)
        }
      }
    }
    
    HorizontalPager(
      modifier = Modifier
        .fillMaxSize()
        .clickable {
          if (items.isNotEmpty()) goToItem(items[pagerState.currentPage])
        },
      count = items.size,
      state = pagerState,
    ) { page ->
      page(items[page])
    }
    
    Column(Modifier.align(Alignment.Center)) {
      Spacer(modifier = Modifier.weight(1.0f))
      HorizontalPagerIndicator(
        pagerState = pagerState,
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .padding(16.dp),
      )
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}