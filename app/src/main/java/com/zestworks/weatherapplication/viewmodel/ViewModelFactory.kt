package com.zestworks.weatherapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zestworks.weatherapplication.repository.WeatherRepository

object ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(WeatherRepository()) as T
    }
}