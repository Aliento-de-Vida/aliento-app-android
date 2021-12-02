package com.alientodevida.alientoapp.domain.campus

interface CampusRepository {
    suspend fun getCampus(): List<Campus>
}