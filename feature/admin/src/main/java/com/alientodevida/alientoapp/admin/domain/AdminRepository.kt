package com.alientodevida.alientoapp.admin.domain

import com.alientodevida.alientoapp.domain.common.Token

interface AdminRepository {
  suspend fun login(email: String, password: String): Token
}