package com.alientodevida.alientoapp.app.features.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.Body2
import com.alientodevida.alientoapp.app.compose.components.ClickableIcon
import com.alientodevida.alientoapp.app.compose.components.Gradient
import com.alientodevida.alientoapp.app.compose.components.H5
import com.alientodevida.alientoapp.app.compose.components.Icon
import com.alientodevida.alientoapp.app.compose.components.ImageWithShimmering
import com.alientodevida.alientoapp.app.compose.components.LoadingIndicator
import com.alientodevida.alientoapp.app.compose.components.Subtitle1
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.extensions.SnackBar
import com.alientodevida.alientoapp.domain.entities.local.CarouselItem
import com.alientodevida.alientoapp.domain.entities.local.CategoryItemType
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Home(
  viewModel: HomeViewModel,
  goToEditHome: () -> Unit,
  goToNotifications: () -> Unit,
  goToSettings: () -> Unit,
  goToSermons: () -> Unit,
  goToChurch: () -> Unit,
  goToCampus: () -> Unit,
  goToGallery: () -> Unit,
  goToPrayer: () -> Unit,
  goToDonations: () -> Unit,
  goToEbook: () -> Unit,
) {
  
  val viewModelState by viewModel.viewModelState.collectAsState()
  
  HomeContent(
    uiState = viewModelState,
    onMessageDismiss = viewModel::onMessageDismiss,
    goToEditHome = goToEditHome,
    goToNotifications = goToNotifications,
    goToSettings = goToSettings,
    goToSermons = goToSermons,
    goToChurch = goToChurch,
    goToCampus = goToCampus,
    goToGallery = goToGallery,
    goToPrayer = goToPrayer,
    goToDonations = goToDonations,
    goToEbook = goToEbook,
  )
}

@Composable
fun HomeContent(
  uiState: HomeUiState,
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  onMessageDismiss: (Long) -> Unit,
  goToEditHome: () -> Unit,
  goToNotifications: () -> Unit,
  goToSettings: () -> Unit,
  goToSermons: () -> Unit,
  goToChurch: () -> Unit,
  goToCampus: () -> Unit,
  goToGallery: () -> Unit,
  goToPrayer: () -> Unit,
  goToDonations: () -> Unit,
  goToEbook: () -> Unit,
) {
  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(
        goToNotifications = goToNotifications,
        goToSettings = goToSettings,
      )
    },
    floatingActionButton = {
      if (uiState.isAdmin) FloatingActionButton(
        onClick = { goToEditHome() },
        contentColor = MaterialTheme.colors.surface,
      ) {
        Icon(
          icon = R.drawable.ic_edit_24,
          contentDescription = "Edit Home",
          tint = MaterialTheme.colors.onSurface
        )
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .padding(paddingValues = paddingValues)
        .background(color = MaterialTheme.colors.background),
    ) {
      HomeBody(
        uiState = uiState,
        goToSermons = goToSermons,
        goToChurch = goToChurch,
        goToCampus = goToCampus,
        goToGallery = goToGallery,
        goToPrayer = goToPrayer,
        goToDonations = goToDonations,
        goToEbook = goToEbook,
      )
      if (uiState.loading) LoadingIndicator()
      
      uiState.messages.firstOrNull()?.SnackBar(scaffoldState, onMessageDismiss)
    }
  }
}

// TODO can we extract a component ?
@Composable
fun TopAppBar(
  goToNotifications: () -> Unit,
  goToSettings: () -> Unit,
) {
  val modifier = Modifier.size(width = 60.dp, height = 50.dp)
  
  androidx.compose.material.TopAppBar(
    title = {
      Image(
        painter = painterResource(id = R.drawable.logo_negro),
        colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onBackground),
        contentScale = ContentScale.Inside,
        alignment = Alignment.Center,
        modifier = Modifier
          .padding(8.dp)
          .fillMaxWidth(),
        contentDescription = null,
      )
    },
    navigationIcon = {
      ClickableIcon(
        modifier = modifier,
        icon = R.drawable.ic_baseline_settings_24,
        contentDescription = "Settings Button",
        tint = MaterialTheme.colors.onBackground,
        onClick = goToSettings,
      )
    },
    actions = {
      ClickableIcon(
        modifier = modifier,
        icon = R.drawable.ic_notifications_black_24dp,
        contentDescription = "Notifications Button",
        tint = MaterialTheme.colors.onBackground,
        onClick = goToNotifications,
      )
    },
    backgroundColor = MaterialTheme.colors.background,
  )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun HomeBody(
  uiState: HomeUiState,
  goToSermons: () -> Unit,
  goToChurch: () -> Unit,
  goToCampus: () -> Unit,
  goToGallery: () -> Unit,
  goToPrayer: () -> Unit,
  goToDonations: () -> Unit,
  goToEbook: () -> Unit,
) {
  Column(Modifier.padding(horizontal = 8.dp)) {
    Pager(uiState.sermonItems.take(3))
    
    Spacer(modifier = Modifier.height(16.dp))
    H5(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = "Categorías",
      color = MaterialTheme.colors.onBackground,
    )
  
    Spacer(modifier = Modifier.height(16.dp))
    LazyRow(
      contentPadding = PaddingValues(bottom = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      items(uiState.carouselItems, key = { it.title }) { item ->
        CategoryItem(
          modifier = Modifier.animateItemPlacement(),
          item = item,
          goToChurch = goToChurch,
          goToCampus = goToCampus,
          goToGallery = goToGallery,
        )
      }
    }
  }
}

@Composable
fun CategoryItem(
  modifier: Modifier,
  item: CarouselItem,
  goToChurch: () -> Unit,
  goToCampus: () -> Unit,
  goToGallery: () -> Unit,
) {
  Card(
    modifier
      .width(230.dp)
      .height(160.dp)
      .clickable {
        when (item.categoryItem?.type) {
          CategoryItemType.CHURCH -> {
            goToChurch()
          }
          CategoryItemType.CAMPUSES -> {
            goToCampus()
          }
          CategoryItemType.GALLERY -> {
            goToGallery()
          }
          else -> {}
        }
      }
  ) {
    Box {
      item.imageUrl?.let { imageUrl ->
        ImageWithShimmering(url = imageUrl, description = item.title)
      }
    
      Column {
        Spacer(Modifier.weight(0.38f))
        Gradient(
          modifier = Modifier
            .fillMaxWidth()
            .weight(0.62f),
        ) {
          Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
            Spacer(Modifier.weight(1.0f))
            Body2(
              text = item.title,
              color = colorResource(R.color.pantone_white_c),
            )
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Pager(carouselItems: List<CarouselItem>) {
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
      modifier = Modifier.fillMaxSize(),
      count = carouselItems.size,
      state = pagerState,
    ) { page ->
      PagerItem(item = carouselItems[page])
    }
    
    Column(Modifier.align(Alignment.Center)) {
      Spacer(modifier = Modifier.weight(1.0f))
      HorizontalPagerIndicator(
        pagerState = pagerState,
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .padding(16.dp),
      )
    }
  }
}

@Composable
private fun PagerItem(item: CarouselItem) {
  Box {
    item.imageUrl?.let { ImageWithShimmering(url = it, item.title) }
    
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


@Preview
@Composable
fun NotificationsPreview() {
  AppTheme {
    HomeContent(
      HomeUiState(
        home = null,
        homeImages = null,
        carouselItems = emptyList(),
        sermonItems = emptyList(),
        loading = true,
        emptyList(),
        true,
      ),
      onMessageDismiss = {},
      goToEditHome = {},
      goToNotifications = {},
      goToSettings = {},
      goToSermons = {},
      goToChurch = {},
      goToCampus = {},
      goToGallery = {},
      goToPrayer = {},
      goToDonations = {},
      goToEbook = {},
    )
  }
}