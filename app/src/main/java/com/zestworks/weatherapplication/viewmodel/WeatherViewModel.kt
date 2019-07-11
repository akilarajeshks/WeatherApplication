package com.zestworks.weatherapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zestworks.weatherapplication.model.Forecast
import com.zestworks.weatherapplication.model.Repository

class WeatherViewModel(private val repository: Repository) : ViewModel() {

    val currentStatus: MutableLiveData<Status> = MutableLiveData<Status>().apply { postValue(Status.None) }

    sealed class Status {
        object None : Status()
        object Loading : Status()
        data class Success(val forecast: Forecast) : Status()
        data class Error(val reason: String) : Status()
    }

    fun onFragmentStart(){
        currentStatus.postValue(Status.Loading)
        repository.loadWeatherData(object : NetworkCallback{
            override fun onSuccess(forecast: Forecast) {
                currentStatus.postValue(Status.Success(forecast))
            }

            override fun onFailure(reason: String) {
                currentStatus.postValue(Status.Error(reason))
            }

        })
    }

}

interface NetworkCallback{
    fun onSuccess(forecast: Forecast)
    fun onFailure(reason: String)
}