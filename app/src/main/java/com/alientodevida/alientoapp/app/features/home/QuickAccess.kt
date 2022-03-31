package com.alientodevida.alientoapp.app.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    
    val modifier = Modifier.height(120.dp)
    
    Card(modifier.clickable { goToPrayer() }) { ImageWithShimmering(url = homeImages.prayerImage) }
    Spacer(Modifier.height(16.dp))
    
    Card(modifier.clickable { goToDonations() }) { ImageWithShimmering(url = homeImages.donationsImage) }
    Spacer(Modifier.height(16.dp))
    
    Card(modifier.clickable { goToEbook() }) { ImageWithShimmering(url = homeImages.ebookImage) }
    Spacer(Modifier.height(16.dp))
  }
}
