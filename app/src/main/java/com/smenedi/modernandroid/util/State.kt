package com.smenedi.modernandroid.util

/**
 * Represents the state of data
 */
sealed class State<out T> {
    data class Success<out T>(val data: T) : State<T>()
    object Loading : State<Nothing>()
    class Error(val exception: Exception? = null, val message: String? = null) : State<Nothing>()
}
