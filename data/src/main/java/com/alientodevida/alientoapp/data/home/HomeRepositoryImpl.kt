package com.alientodevida.alientoapp.data.home

import com.alientodevida.alientoapp.domain.home.HomeRepository

class HomeRepositoryImpl (
    private val homeApi: HomeApi,
) : HomeRepository {
    override suspend fun getHome() = homeApi.getHome()
}