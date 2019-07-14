package com.zestworks.weatherapplication.repository

import com.zestworks.weatherapplication.viewmodel.NetworkCallback

interface Repository {
    fun loadWeatherData(lat: Double, long: Double, networkCallback: NetworkCallback)
}