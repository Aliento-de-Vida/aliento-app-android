package com.alientodevida.alientoapp.data.home

import com.alientodevida.alientoapp.domain.home.HomeRepository

class HomeRepositoryImpl(
  private val api: HomeApi,
) : HomeRepository {
  override suspend fun getHome() = api.getHome()
}