package com.alientodevida.alientoapp.campus.presentation.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.alientodevida.alientoapp.campus.R
import com.alientodevida.alientoapp.domain.extensions.toImageUrl
import com.alientodevida.alientoapp.domain.common.Campus
import com.bumptech.glide.Glide
import com.stfalcon.imageviewer.StfalconImageViewer

private fun openMap(context: Context, latitude: String, longitude: String, name: String) {
  val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($name)")
  val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
  mapIntent.setPackage("com.google.android.apps.maps")
  mapIntent.resolveActivity(context.packageManager)?.let {
    startActivity(context, mapIntent, null)
  }
}

private fun openGallery(context: Context, images: List<String>) {
  StfalconImageViewer.Builder(context, images) { view, imageUrl ->
    Glide.with(context).load(imageUrl).into(view)
  }.show()
}

@Composable
fun CampusDetail(campus: Campus) {
  val context = LocalContext.current
  
  CampusDetailContent(
    campus = campus,
    openMap = {
      openMap(
        context,
        campus.location.latitude,
        campus.location.longitude,
        campus.name,
      )
    },
    openGallery = {
      openGallery(context, campus.images.map { it.toImageUrl() })
    },
    goToImage = { openGallery(context, listOf(it)) },
  )
}

@Composable
fun CampusDetailContent(
    campus: Campus,
    openMap: () -> Unit,
    openGallery: () -> Unit,
    goToImage: (String) -> Unit,
) {
  val scrollState = rememberScrollState()
  
  Column(
    Modifier
      .verticalScroll(scrollState)
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  ) {
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.H5(
          modifier = Modifier.align(Alignment.CenterHorizontally),
          text = campus.name,
          color = MaterialTheme.colors.onBackground,
      )
  
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.Pager(
          items = campus.images.map { it.toImageUrl() },
          goToItem = { goToImage(it) },
          page = { com.alientodevida.alientoapp.designsystem.components.ImageWithShimmering(url = it) }
      )
  
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.Subtitle2(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = campus.shortDescription,
          color = MaterialTheme.colors.onBackground,
      )
  
    Spacer(modifier = Modifier.height(8.dp))
      com.alientodevida.alientoapp.designsystem.components.Body1(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = campus.description,
          color = MaterialTheme.colors.onBackground,
      )
  
    Spacer(modifier = Modifier.height(8.dp))
      com.alientodevida.alientoapp.designsystem.components.Body1(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Contacto ${campus.contact}",
          color = MaterialTheme.colors.onBackground,
      )
  
    Spacer(modifier = Modifier.height(200.dp))
    Row {
      Row(Modifier.clickable { openGallery() }) {
        Icon(
          modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
          painter = painterResource(R.drawable.ic_image_24),
          contentDescription = "gallería",
          tint = MaterialTheme.colors.onBackground,
        )
        TextButton(
          modifier = Modifier.align(Alignment.CenterVertically),
          onClick = openGallery,
        ) {
            com.alientodevida.alientoapp.designsystem.components.Button(
                text = "VER GALERÍA",
                color = MaterialTheme.colors.onBackground,
            )
        }
      }
      
      Spacer(modifier = Modifier.weight(1.0f))
      
      Row(Modifier.clickable { openMap() }) {
        Icon(
          modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
          painter = painterResource(R.drawable.ic_map_24),
          contentDescription = "Mapa",
          tint = MaterialTheme.colors.onBackground,
        )
        TextButton(
          modifier = Modifier.align(Alignment.CenterVertically),
          onClick = openMap,
        ) {
            com.alientodevida.alientoapp.designsystem.components.Button(
                text = "ABRIR MAPS",
                color = MaterialTheme.colors.onBackground,
            )
        }
      }
    }
  }
}