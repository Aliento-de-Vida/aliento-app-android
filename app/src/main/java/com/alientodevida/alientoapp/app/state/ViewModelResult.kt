package com.alientodevida.alientoapp.app.state

sealed class ViewModelResult<out R> {
  
  data class Success<out T>(val data: T) : ViewModelResult<T>()
  
  data class Error(
    val message: Message,
  ) : ViewModelResult<Nothing>()
  
  object Loading : ViewModelResult<Nothing>()
  
}