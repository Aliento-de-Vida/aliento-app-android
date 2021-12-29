package com.alientodevida.alientoapp.data.campus

import com.alientodevida.alientoapp.domain.campus.Campus
import retrofit2.http.GET

interface CampusApi {
  
  @GET("/v1/campuses")
  suspend fun getCampus(): List<Campus>
  
}