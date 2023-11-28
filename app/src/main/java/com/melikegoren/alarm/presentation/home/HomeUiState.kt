package com.melikegoren.alarm.presentation.home

sealed class HomeUiState<out T : Any> {
    object Loading : HomeUiState<Nothing>()
    data class Success<T : Any>(val data: T) : HomeUiState<T>()
    data class Error(val message: String) : HomeUiState<Nothing>()
}