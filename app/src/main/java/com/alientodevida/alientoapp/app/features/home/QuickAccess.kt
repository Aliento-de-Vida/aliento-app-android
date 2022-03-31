package com.alientodevida.alientoapp.app.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.Body2
import com.alientodevida.alientoapp.app.compose.components.Gradient
import com.alientodevida.alientoapp.app.compose.components.H5
import com.alientodevida.alientoapp.app.compose.components.ImageWithShimmering
import com.alientodevida.alientoapp.domain.home.HomeImages

@Composable
fun QuickAccess(
  homeImages: HomeImages,
  goToDonations: () -> Unit,
  goToPrayer: () -> Unit,
  goToEbook: () -> Unit
) {
  Column(Modifier.padding(horizontal = 8.dp)) {
    H5(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = "Acceso Rápido",
      color = MaterialTheme.colors.onBackground,
    )
    Spacer(modifier = Modifier.height(16.dp))
    
    CardWithTitle(
      title = "Oración",
      image = homeImages.prayerImage,
      onClick = goToPrayer,
    )
    Spacer(Modifier.height(16.dp))
  
    CardWithTitle(
      title = "Donaciones",
      image = homeImages.donationsImage,
      onClick = goToDonations,
    )
    Spacer(Modifier.height(16.dp))
  
    CardWithTitle(
      title = "Ebook",
      image = homeImages.ebookImage,
      onClick = goToEbook,
    )
    Spacer(Modifier.height(16.dp))
  }
}

@Composable
fun CardWithTitle(
  title: String,
  image: String?,
  onClick: () -> Unit,
) {
  Card {
    Box(
      Modifier
        .height(120.dp)
        .clickable { onClick() }
    ) {
      image?.let { imageUrl ->
        ImageWithShimmering(url = imageUrl, description = title)
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
              text = title,
              color = colorResource(R.color.pantone_white_c),
            )
          }
        }
      }
    }
  }
}