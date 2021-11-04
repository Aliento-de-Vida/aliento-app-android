package com.alientodevida.alientoapp.app.features.sermons

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.Repository
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SermonsViewModel @Inject constructor(
    private val repository: Repository,
    coroutineDispatchers: CoroutineDispatchers,
    errorParser: ErrorParser,
    logger: Logger,
    savedStateHandle: SavedStateHandle,
    application: Application,
) : BaseViewModel(
    coroutineDispatchers,
    errorParser,
    logger,
    savedStateHandle,
    application,
) { }