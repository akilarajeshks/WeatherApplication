package com.zestworks.weatherapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zestworks.weatherapplication.R
import com.zestworks.weatherapplication.viewmodel.ViewModelFactory
import com.zestworks.weatherapplication.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_weather.*


class WeatherFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onStart() {
        super.onStart()

        val weatherViewModel = ViewModelProviders.of(activity!!, ViewModelFactory)[WeatherViewModel::class.java]

        weatherViewModel.currentStatus.observe(this, Observer {
            when(it){
                WeatherViewModel.Status.None -> {}
                WeatherViewModel.Status.Loading -> {}
                is WeatherViewModel.Status.Success -> {
                    val temperatureString =  it.forecast.current.tempC.toString()
                    temperature.text = "$temperatureString℃"

                }
                is WeatherViewModel.Status.Error -> {}
            }
        })
    }
}
