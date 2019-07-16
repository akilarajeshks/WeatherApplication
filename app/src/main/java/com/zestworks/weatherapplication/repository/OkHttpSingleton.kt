package com.zestworks.weatherapplication.repository

import okhttp3.OkHttpClient

object OkHttpSingleton {
    val okHttpClient = OkHttpClient()
}