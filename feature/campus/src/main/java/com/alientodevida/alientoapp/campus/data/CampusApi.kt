package com.alientodevida.alientoapp.campus.data

import com.alientodevida.alientoapp.domain.common.Campus
import retrofit2.http.GET

interface CampusApi {
  
  @GET("/v1/campuses")
  suspend fun getCampus(): List<Campus>
  
}