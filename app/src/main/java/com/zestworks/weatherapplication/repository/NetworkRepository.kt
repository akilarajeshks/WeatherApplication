package com.zestworks.weatherapplication.repository

import com.zestworks.weatherapplication.model.Forecast
import com.zestworks.weatherapplication.viewmodel.NetworkCallback
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class NetworkRepository : Repository {

    private val retrofit = Retrofit.Builder().baseUrl("https://api.apixu.com/v1/")
        .client(OkHttpSingleton.okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val forecastService = retrofit.create(GetForecast::class.java)

    override fun loadWeatherData(lat: Double, long: Double, networkCallback: NetworkCallback) {

        val coordinates = "$lat,$long"

        val forecastCall =
            forecastService.getForecast(coordinates)


        forecastCall.enqueue(object : retrofit2.Callback<Forecast> {
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                if (t.message != null) {
                    networkCallback.onFailure(t.message!!)
                } else {
                    networkCallback.onFailure("Network failed due to unknown reasons")
                }
            }

            override fun onResponse(call: Call<Forecast>, response: retrofit2.Response<Forecast>) {
                networkCallback.onSuccess(response.body()!!)
            }

        })
    }
}

interface GetForecast {
    @GET("forecast.json?key=71b888d55c704fefa6f180510192303&days=5")
    fun getForecast(@Query("q") coordinates: String): Call<Forecast>
}