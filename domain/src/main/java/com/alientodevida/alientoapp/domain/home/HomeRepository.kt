package com.alientodevida.alientoapp.domain.home

interface HomeRepository {
  suspend fun getHome(): Home
  suspend fun updateHome(home: Home): Home
  suspend fun getHomeImages(): HomeImages
}