package com.omaradev.newsapp.data.repository

sealed class RemoteRequestStatus<T> {
    data class ToggleLoading<T>(val showLoading: Boolean) : RemoteRequestStatus<T>()

    data class OnSuccessRequest<T>(val responseBody: T) : RemoteRequestStatus<T>()

    data class OnFailedRequest<T>(val responseBody: T?, val errorMessage: String?) :
        RemoteRequestStatus<T>()

    data class OnNetworkError<T>(val exception: Exception? = null, val message: String? = null) :
        RemoteRequestStatus<T>()
}
