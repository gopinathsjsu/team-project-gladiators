package com.example.studentportal.common.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher

abstract class BaseViewModel(
    protected val dispatcher: CoroutineDispatcher
) : ViewModel()
