package com.example.studentportal.common.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.studentportal.common.usecase.BaseUseCaseProvider
import kotlinx.coroutines.CoroutineDispatcher

abstract class BaseViewModel(
    protected val dispatcher: CoroutineDispatcher
) : ViewModel()