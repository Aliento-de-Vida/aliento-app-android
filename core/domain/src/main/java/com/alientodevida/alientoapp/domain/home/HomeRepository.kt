package com.alientodevida.alientoapp.domain.home

import com.alientodevida.alientoapp.domain.common.Home

interface HomeRepository {
  suspend fun getHome(): Home
  suspend fun updateHome(home: Home): Home
  suspend fun getHomeImages(): HomeImages
}