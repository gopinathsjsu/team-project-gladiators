package com.example.studentportal.common.usecase

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher

abstract class BaseViewModel<UseCaseProvider : BaseUseCaseProvider>(
    protected val provider: UseCaseProvider,
    protected val dispatcher: CoroutineDispatcher
) : ViewModel()