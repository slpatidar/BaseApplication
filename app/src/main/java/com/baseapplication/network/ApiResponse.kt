package com.baseapplication.network

import com.google.gson.JsonElement

class ApiResponse {
    val status: Status
    val data: JsonElement?
    val error: Throwable?
    var accountAlias: String? = null

    private constructor(status: Status, data: JsonElement?, error: Throwable?) {
        this.status = status
        this.data = data
        this.error = error
    }

    private constructor(
        status: Status,
        data: JsonElement?,
        error: Throwable?,
        accountAlias: String
    ) {
        this.status = status
        this.data = data
        this.error = error
        this.accountAlias = accountAlias
    }

    companion object {
        fun loading(): ApiResponse {
            return ApiResponse(Status.LOADING, null, null)
        }

        fun success(data: JsonElement): ApiResponse {
            return ApiResponse(Status.SUCCESS, data, null)
        }

        fun error(error: Throwable): ApiResponse {
            return ApiResponse(Status.ERROR, null, error)
        }

        fun successBalanceEnquiry(data: JsonElement, accountAlias: String): ApiResponse {
            return ApiResponse(Status.SUCCESS, data, null, accountAlias)
        }

        fun errorForBalanceEnquiry(error: Throwable, accountAlias: String): ApiResponse {
            return ApiResponse(Status.ERROR, null, error, accountAlias)
        }
    }
}
