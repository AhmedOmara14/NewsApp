package com.omaradev.newsapp.data.repository


import com.google.gson.GsonBuilder
import com.omaradev.newsapp.domain.model.AppApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

open class AppBaseRemoteRepository @Inject constructor() {
    protected inline fun <reified T> makeApiRequest(
        noinline request: suspend () -> Response<T>,
        noinline doOnSuccessResponse: suspend (responseBody: T) -> Unit? = {}
    ): Flow<RemoteRequestStatus<T>> = flow {
        emit(RemoteRequestStatus.ToggleLoading(true))

        try {
            val response = request()
            emit(RemoteRequestStatus.ToggleLoading(false))

            if (response.isSuccessful) {
                try {
                    val genericResponse = response.body()!! as AppApiResponse<*>
                    if (genericResponse.status.not()) {
                        emit(
                            RemoteRequestStatus.OnFailedRequest(
                                response.body()!! as T, genericResponse.message
                            )
                        )
                    } else {
                        doOnSuccessResponse(response.body()!!).also {
                            emit(RemoteRequestStatus.OnSuccessRequest(response.body()!! as T))
                        }
                    }
                } catch (e: Exception) {
                    doOnSuccessResponse(response.body()!!).also {
                        emit(RemoteRequestStatus.OnSuccessRequest(response.body()!! as T))
                    }
                }
            } else {
                val errorBody = response.errorBody()!!.string()

                val gson = GsonBuilder()
                val errorResponse: T = gson.create().fromJson(errorBody, T::class.java)

                val message = (errorResponse as AppApiResponse<String>).message
                val errorMessage = (message
                    ?: "") + (if (message?.isNotBlank() == true) "\n" else "")

                emit(RemoteRequestStatus.OnFailedRequest(errorResponse, errorMessage))
            }
        } catch (e: Exception) {
            emit(RemoteRequestStatus.ToggleLoading(false))
            emit(RemoteRequestStatus.OnFailedRequest(null, null))
        }
    }
}
