package com.alientodevida.alientoapp.data.admin

import com.alientodevida.alientoapp.domain.admin.AdminRepository
import com.alientodevida.alientoapp.domain.admin.Token

class AdminRepositoryImpl(
  private val api: AdminApi,
) : AdminRepository {

  override suspend fun login(email: String, password: String) = api.login(email, password)
  
}