package com.evgeniykim.usdrate.network

data class ResponseResult<out T>(
    val status: Status,
    val message: String?,
    val data: T?,
    val error: Throwable?,
    val errors: ErrorsFullResponse?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        FIELD_ERROR
    }

    companion object {
        fun <T> success(data: T?): ResponseResult<T> {
            return ResponseResult(Status.SUCCESS, null, data, null, null)
        }

        fun <T> error(
            error: Throwable?,
            data: T? = null,
            errorsResponse: ErrorsFullResponse?
        ): ResponseResult<T> {
            return ResponseResult(Status.ERROR, null, data, error, errorsResponse)
        }

        fun <T> errorMessage(
            message: String,
        ): ResponseResult<T> {
            return ResponseResult(Status.ERROR, message, null, null, null)
        }

        fun <T> errorCode(
            code: Int,
        ): ResponseResult<T> {
            return ResponseResult(Status.ERROR, null, null, null, null)
        }

        fun <T> fieldError(
            errorsResponse: ErrorsFullResponse?
        ): ResponseResult<T> {
            return ResponseResult(Status.FIELD_ERROR, null, null, null, errorsResponse)
        }
    }
}
