package com.evgeniykim.usdrate.network

import android.util.Log
import com.evgeniykim.usdrate.model.USDModel
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseDataSource {

    private var serverMessage = ""

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ResponseResult<T> {
        try {
            val response = call()

            if (response.isSuccessful) {
                val json = response.toString()
                val body = response.body()

                Log.d("SERVERREQUEST", response.raw().request.url.toString() + " " + json)
//                response.raw().request.headers["Authorization"]?.let { Log.d("TOKEN", it) }

                if (body != null) {
                    return ResponseResult.success(response.body()!!)
                }
                serverMessage = if (response.body() != null) response.body()!!
                    .toString() else "Ошибка обращения к серверу"
                return error(throw Exception(serverMessage))
            } else if (response.errorBody() != null) {

                return try {
                    //ErrorBody from server response
                    val json = response.errorBody()!!.toString()

                    Log.d("SERVERREQUESTERROR", response.raw().request.url.toString() + " " + json)

                    val errorsData =
                        Gson().fromJson(json, ErrorsFullResponse::class.java)

                    if (errorsData.error != null) {
                        errorFields(errorsData)
                    } else {
                        if (errorsData.message.isEmpty()) {
                            errorMessage(response.code().toString() + " " + errorsData.error)//response.code().toString() + " " + response.message() + " " + response.raw().request.url)
                        } else {
                            errorMessage(errorsData.message)
                        }
                    }

                } catch (e: Exception) {
                    Log.d("SERVERREQUESTERROR", response.raw().request.url.toString() + " " + e.message)

                    error(e)
                }
            }
            return error(throw HttpException(response))
        } catch (e: Exception) {
            return error(e)
        }
    }

    private fun <T> errorCode(code: Int): ResponseResult<T> {
        return ResponseResult.errorCode(code)
    }

    private fun <T> errorMessage(message: String): ResponseResult<T> {
        return ResponseResult.errorMessage(message)
    }

    private fun <T> error(response: Throwable): ResponseResult<T> {
        return ResponseResult.error(response, null, null)
    }

    private fun <T> errorFields(response: ErrorsFullResponse): ResponseResult<T> {
        return ResponseResult.fieldError(response)
    }

    private fun getErrorMessage(errors: ErrorsFullResponse): Boolean {
        if (errors.message.isNullOrEmpty()) {
            return false
        } else {
            serverMessage = errors.message
            return true
        }
    }
}