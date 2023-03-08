package com.alientodevida.alientoapp.ui.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.common.logger.Logger
import com.alientodevida.alientoapp.ui.state.ViewModelResult
import com.alientodevida.alientoapp.ui.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.preferences.Preferences
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel(
    protected val coroutineDispatchers: CoroutineDispatchers,
    protected val errorParser: ErrorParser,
    protected val logger: Logger,
    protected val preferences: Preferences,
    protected val savedStateHandle: SavedStateHandle,
    application: Application,
) : ViewModel() {
  
  protected fun <T> stateFlowResult(
      stateFlow: MutableStateFlow<ViewModelResult<T>>,
      scope: CoroutineScope = viewModelScope,
      dispatcher: CoroutineDispatcher = coroutineDispatchers.main,
      onSuccess: (suspend (T) -> Unit)? = null,
      onError: (suspend (Exception) -> Unit)? = null,
      onDone: (suspend () -> Unit)? = null,
      block: suspend CoroutineScope.() -> T,
  ): Job {
    stateFlow.value = ViewModelResult.Loading
    return scope.launch(dispatcher) {
      val result = try {
        val value = block()
        ViewModelResult.Success(value).apply {
          onSuccess?.safeInvoke(value, logger)
        }
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        logger.d("ViewModel.stateFlowResult", tr = ex)
        val message = errorParser(ex)
        ViewModelResult.Error(message).apply {
          onError?.safeInvoke(ex, logger)
        }
      }
      stateFlow.value = result
      onDone?.safeInvoke(logger)
    }
  }
  
  protected fun <T> stateFlowNullableResult(
      stateFlow: MutableStateFlow<ViewModelResult<T>?>,
      scope: CoroutineScope = viewModelScope,
      dispatcher: CoroutineDispatcher = coroutineDispatchers.main,
      onSuccess: (suspend (T) -> Unit)? = null,
      onError: (suspend (Exception) -> Unit)? = null,
      onDone: (suspend () -> Unit)? = null,
      block: suspend CoroutineScope.() -> T,
  ): Job {
    stateFlow.value = ViewModelResult.Loading
    return scope.launch(dispatcher) {
      val result = try {
        val value = block()
        ViewModelResult.Success(value).apply {
          onSuccess?.safeInvoke(value, logger)
        }
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        logger.d("ViewModel.stateFlowNullableResult", tr = ex)
        val message = errorParser(ex)
        ViewModelResult.Error(message).apply {
          onError?.safeInvoke(ex, logger)
        }
      }
      stateFlow.value = result
      onDone?.safeInvoke(logger)
    }
  }
  
  protected fun <T> liveDataResult(
      liveData: MutableLiveData<ViewModelResult<T>>,
      scope: CoroutineScope = viewModelScope,
      dispatcher: CoroutineDispatcher = coroutineDispatchers.main,
      onSuccess: (suspend (T) -> Unit)? = null,
      onError: (suspend (Exception) -> Unit)? = null,
      onDone: (suspend () -> Unit)? = null,
      block: suspend CoroutineScope.() -> T,
  ): Job {
    liveData.value = ViewModelResult.Loading
    return scope.launch(dispatcher) {
      val result = try {
        val value = block()
        ViewModelResult.Success(value).apply {
          onSuccess?.safeInvoke(value, logger)
        }
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        logger.d("ViewModel.liveDataResult", tr = ex)
        val message = errorParser(ex)
        ViewModelResult.Error(message).apply {
          onError?.safeInvoke(ex, logger)
        }
      }
      liveData.postValue(result)
      onDone?.safeInvoke(logger)
    }
  }
  
  protected fun <T> liveDataNullableResult(
      liveData: MutableLiveData<ViewModelResult<T>?>,
      scope: CoroutineScope = viewModelScope,
      dispatcher: CoroutineDispatcher = coroutineDispatchers.main,
      onSuccess: (suspend (T) -> Unit)? = null,
      onError: (suspend (Exception) -> Unit)? = null,
      onDone: (suspend () -> Unit)? = null,
      block: suspend CoroutineScope.() -> T,
  ): Job {
    liveData.value = ViewModelResult.Loading
    return scope.launch(dispatcher) {
      val result = try {
        val value = block()
        ViewModelResult.Success(value).apply {
          onSuccess?.safeInvoke(value, logger)
        }
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        logger.d("ViewModel.liveDataNullableResult", tr = ex)
        val message = errorParser(ex)
        ViewModelResult.Error(message).apply {
          onError?.safeInvoke(ex, logger)
        }
      }
      liveData.postValue(result)
      onDone?.safeInvoke(logger)
    }
  }
}

private suspend fun <T> (suspend (T) -> Unit).safeInvoke(value: T, logger: Logger) {
  try {
    invoke(value)
  } catch (ex: CancellationException) {
    return
  } catch (ex: Exception) {
    logger.d("(suspend () -> Unit).safeInvoke", tr = ex)
  }
}

private suspend fun (suspend (Exception) -> Unit).safeInvoke(ex: Exception, logger: Logger) {
  try {
    invoke(ex)
  } catch (ex: CancellationException) {
    return
  } catch (ex: Exception) {
    logger.d("(suspend () -> Unit).safeInvoke", tr = ex)
  }
}

private suspend fun (suspend () -> Unit).safeInvoke(logger: Logger) {
  try {
    invoke()
  } catch (ex: CancellationException) {
    return
  } catch (ex: Exception) {
    logger.d("(suspend () -> Unit).safeInvoke", tr = ex)
  }
}