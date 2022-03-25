package com.alientodevida.alientoapp.domain.admin

interface AdminRepository {
  suspend fun login(email: String, password: String): Token
}