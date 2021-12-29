package com.alientodevida.alientoapp.data.home

import com.alientodevida.alientoapp.domain.home.Notification
import com.alientodevida.alientoapp.domain.common.Image
import com.alientodevida.alientoapp.domain.home.HomeRepository

class HomeRepositoryImpl(
  private val homeApi: HomeApi,
) : HomeRepository {
  override suspend fun getHome() = homeApi.getHome()
  override suspend fun getNotifications(): List<Notification> {
    return listOf(
      Notification(
        0,
        "Reunión de fin de año",
        "Te invitamos a que pases con nosotros esta reunión tan especial",
        Image("aliento_de_vida.jpeg"),
        "28 diciembre 21"
      ),
      Notification(
        0,
        "Feliz Navidad!",
        "Te mandamos un fuerte abrazo!",
        Image("cursos.png"),
        "28 diciembre 21"
      ),
      Notification(
        0,
        "2022 con todo",
        "Preparate con nosotros para empezar el año",
        Image("manos_extendidas.jpg"),
        "28 diciembre 21"
      ),
    )
  }
}