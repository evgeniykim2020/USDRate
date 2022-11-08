package com.evgeniykim.usdrate.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitModule {
    private val client = OkHttpClient().newBuilder()
        .addNetworkInterceptor(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY })
        .build()

    fun retrofit() : Retrofit {
        val BASE_URL = "http://www.cbr.ru/scripts/"

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }
}