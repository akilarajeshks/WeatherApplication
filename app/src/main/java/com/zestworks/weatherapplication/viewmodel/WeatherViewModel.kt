package com.zestworks.weatherapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zestworks.weatherapplication.model.Forecast
import com.zestworks.weatherapplication.repository.Repository

class WeatherViewModel(private val repository: Repository) : ViewModel() {

    val currentStatus: MutableLiveData<Status> = MutableLiveData<Status>().apply { postValue(Status.None) }

    enum class ErrorType {
        NETWORK_FAILURE,
        LOCATION_PERMISSION_DENIED
    }

    sealed class Status {
        object None : Status()
        object Loading : Status()
        data class Success(val forecast: Forecast) : Status()
        data class Error(val errorType: ErrorType) : Status()
    }

    fun onLocationFetched(lat: Double, long: Double) {
        currentStatus.postValue(Status.Loading)
        repository.loadWeatherData(lat, long, object : NetworkCallback {
            override fun onSuccess(forecast: Forecast) {
                currentStatus.postValue(Status.Success(forecast))
            }

            override fun onFailure(reason: String) {
                currentStatus.postValue(Status.Error(ErrorType.NETWORK_FAILURE))
            }

        })
    }

    fun onLocationPermissionDenied(){
        currentStatus.postValue(Status.Error(ErrorType.LOCATION_PERMISSION_DENIED))
    }

    fun onRetryButtonClicked() {
        currentStatus.postValue(Status.None)
    }

}

interface NetworkCallback {
    fun onSuccess(forecast: Forecast)
    fun onFailure(reason: String)
}