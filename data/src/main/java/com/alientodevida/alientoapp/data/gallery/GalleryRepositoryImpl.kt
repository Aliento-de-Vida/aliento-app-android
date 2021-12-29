package com.alientodevida.alientoapp.data.gallery

import com.alientodevida.alientoapp.domain.common.Image
import com.alientodevida.alientoapp.domain.gallery.Gallery
import com.alientodevida.alientoapp.domain.gallery.GalleryRepository

class GalleryRepositoryImpl(
  private val galleryApi: GalleryApi
) : GalleryRepository {
  
  override suspend fun getGalleries() = listOf(
    Gallery(
      "Manos Extendidas",
      "manos_extendidas.jpg",
      listOf(
        Image("aliento_de_vida.jpeg"),
        Image("cursos.png"),
        Image("manos_extendidas.jpg"),
        Image("predicas.jpeg"),
        Image("aliento_de_vida.jpeg"),
        Image("cursos.png"),
        Image("aliento_de_vida.jpeg"),
        Image("manos_extendidas.jpg"),
        Image("predicas.jpeg"),
        Image("cursos.png"),
        Image("aliento_de_vida.jpeg"),
        Image("cursos.png"),
        Image("predicas.jpeg"),
      )
    ),
    Gallery(
      "Iglesia",
      "cursos.png",
      listOf(
        Image("aliento_de_vida.jpeg"),
        Image("cursos.png"),
        Image("manos_extendidas.jpg"),
        Image("predicas.jpeg"),
        Image("aliento_de_vida.jpeg"),
        Image("cursos.png"),
        Image("aliento_de_vida.jpeg"),
        Image("manos_extendidas.jpg"),
        Image("predicas.jpeg"),
        Image("cursos.png"),
        Image("aliento_de_vida.jpeg"),
        Image("cursos.png"),
        Image("predicas.jpeg"),
      )
    ),
    Gallery(
      "Eventos",
      "aliento_de_vida.jpeg",
      listOf(
        Image("aliento_de_vida.jpeg"),
        Image("cursos.png"),
        Image("manos_extendidas.jpg"),
        Image("predicas.jpeg"),
        Image("aliento_de_vida.jpeg"),
        Image("cursos.png"),
        Image("aliento_de_vida.jpeg"),
        Image("manos_extendidas.jpg"),
        Image("predicas.jpeg"),
        Image("cursos.png"),
        Image("aliento_de_vida.jpeg"),
        Image("cursos.png"),
        Image("predicas.jpeg"),
      )
    ),
    Gallery(
      "Niños de Aliento",
      "predicas.jpeg",
      listOf(
        Image("aliento_de_vida.jpeg"),
        Image("cursos.png"),
        Image("manos_extendidas.jpg"),
        Image("predicas.jpeg"),
        Image("aliento_de_vida.jpeg"),
        Image("cursos.png"),
        Image("aliento_de_vida.jpeg"),
        Image("manos_extendidas.jpg"),
        Image("predicas.jpeg"),
        Image("cursos.png"),
        Image("aliento_de_vida.jpeg"),
        Image("cursos.png"),
        Image("predicas.jpeg"),
      )
    )
  )
  
}