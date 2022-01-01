package com.alientodevida.alientoapp.domain.home

interface HomeRepository {
  suspend fun getHome(): Home
  suspend fun getNotifications(): List<Notification>
}