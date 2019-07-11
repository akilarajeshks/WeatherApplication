package com.zestworks.weatherapplication.model

import com.zestworks.weatherapplication.viewmodel.NetworkCallback

interface Repository {
    fun loadWeatherData(networkCallback: NetworkCallback)
}