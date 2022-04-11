package com.alientodevida.alientoapp.app.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.Body2
import com.alientodevida.alientoapp.app.compose.components.Gradient
import com.alientodevida.alientoapp.app.compose.components.ImageWithShimmering
import com.alientodevida.alientoapp.domain.entities.local.CarouselItem
import com.alientodevida.alientoapp.domain.entities.local.CategoryItemType
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Pager(
  items: List<CarouselItem>,
  goToSermons: () -> Unit,
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
          goToSermons() // TODO open specific video
        },
      count = items.size,
      state = pagerState,
    ) { page ->
      PagerItem(item = items[page])
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

@Composable
private fun PagerItem(item: CarouselItem) {
  Box {
    ImageWithShimmering(url = item.imageUrl, description = item.title)
    
    Column {
      Spacer(Modifier.weight(0.38f))
      Gradient(
        Modifier
          .fillMaxWidth()
          .weight(0.62f),
        startColor = Color.Transparent,
        endColor = MaterialTheme.colors.background,
      ) {
        Column {
          if (item.categoryItem?.type == CategoryItemType.SERMONS) {
            Spacer(Modifier.weight(1.0f))
            SeeSermonsCard(Modifier.align(Alignment.Start))
          }
        }
      }
    }
  }
}

@Composable
private fun SeeSermonsCard(modifier: Modifier) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(
      topStart = 0.dp,
      bottomStart = 0.dp,
      topEnd = 8.dp,
      bottomEnd = 8.dp,
    ),
    backgroundColor = Color(0xffff6f00),
  ) {
    Box(
      Modifier
        .padding(horizontal = 40.dp, vertical = 8.dp)
    ) {
      Body2(
        text = "Ver prédicas",
        color = colorResource(R.color.pantone_white_c),
      )
    }
  }
  
  Spacer(Modifier.height(40.dp))
}
