package com.alientodevida.alientoapp.data.campus

import com.alientodevida.alientoapp.domain.campus.CampusRepository

class CampusRepositoryImpl(
  private val campusApi: CampusApi,
) : CampusRepository {
  override suspend fun getCampus() = campusApi.getCampus()
}