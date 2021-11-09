package com.alientodevida.alientoapp.data.home

import com.alientodevida.alientoapp.domain.home.Home
import retrofit2.http.GET

interface HomeApi {

    @GET("/v1/home")
    suspend fun getHome(): Home

}