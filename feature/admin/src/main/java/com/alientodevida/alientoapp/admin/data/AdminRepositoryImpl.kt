package com.alientodevida.alientoapp.admin.data

class AdminRepositoryImpl(
  private val api: AdminApi,
) : com.alientodevida.alientoapp.admin.domain.AdminRepository {

  override suspend fun login(email: String, password: String) = api.login(email, password)
  
}