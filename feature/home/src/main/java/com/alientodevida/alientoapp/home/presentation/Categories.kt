package com.alientodevida.alientoapp.home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.domain.common.CarouselItem
import com.alientodevida.alientoapp.domain.common.CategoryItemType
import com.alientodevida.alientoapp.designsystem.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Categories(
    items: List<CarouselItem>,
    title: String,
    goToChurch: () -> Unit,
    goToCampus: () -> Unit,
    goToGallery: () -> Unit,
    goToDonations: () -> Unit,
    goToPrayer: () -> Unit,
    goToEbook: () -> Unit
) {
  Column(Modifier.padding(8.dp)) {
    com.alientodevida.alientoapp.designsystem.components.H5(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = title,
        color = MaterialTheme.colors.onBackground,
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    LazyRow(
      contentPadding = PaddingValues(bottom = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      items(items, key = { it.title }) { item ->
        CategoryItem(
          modifier = Modifier.animateItemPlacement(),
          item = item,
          goToChurch = goToChurch,
          goToCampus = goToCampus,
          goToGallery = goToGallery,
          goToDonations = goToDonations,
          goToPrayer = goToPrayer,
          goToEbook = goToEbook,
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
    goToDonations: () -> Unit,
    goToPrayer: () -> Unit,
    goToEbook: () -> Unit
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
          CategoryItemType.DONATIONS -> {
            goToDonations()
          }
          CategoryItemType.PRAYER -> {
            goToPrayer()
          }
          CategoryItemType.EBOOK -> {
            goToEbook()
          }
          else -> {}
        }
      }
  ) {
    Box {
      com.alientodevida.alientoapp.designsystem.components.ImageWithShimmering(
          url = item.imageUrl,
          description = item.title
      )
      
      Column {
        Spacer(Modifier.weight(0.38f))
          com.alientodevida.alientoapp.designsystem.components.Gradient(
              modifier = Modifier
                  .fillMaxWidth()
                  .weight(0.62f),
          ) {
              Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                  Spacer(Modifier.weight(1.0f))
                  com.alientodevida.alientoapp.designsystem.components.Body2(
                      text = item.title,
                      color = colorResource(R.color.pantone_white_c),
                  )
              }
          }
      }
    }
  }
}
