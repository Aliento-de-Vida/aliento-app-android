package com.alientodevida.alientoapp.domain.campus

interface CampusRepository {
  suspend fun getCampus(): List<Campus>
  suspend fun deleteNotification(id: Int)
  
  suspend fun createCampus(campusRequest: CampusRequest): Campus
  suspend fun editCampus(campusRequest: CampusRequest): Campus
}