package com.alientodevida.alientoapp.campus.domain

import com.alientodevida.alientoapp.domain.common.Campus

interface CampusRepository {
    suspend fun getCampus(): List<Campus>
    suspend fun deleteNotification(id: Int)

    suspend fun createCampus(campusRequest: CampusRequest): Campus
    suspend fun editCampus(campusRequest: CampusRequest): Campus
}
