package com.zestworks.weatherapplication.view

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.zestworks.weatherapplication.R
import com.zestworks.weatherapplication.viewmodel.ViewModelFactory
import com.zestworks.weatherapplication.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_weather.*


class WeatherFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var renderObserver: Observer<WeatherViewModel.Status>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onStart() {
        super.onStart()

        weatherViewModel = ViewModelProviders.of(activity!!, ViewModelFactory)[WeatherViewModel::class.java]

        renderObserver = Observer {
            when (it) {
                WeatherViewModel.Status.None -> {
                }
                WeatherViewModel.Status.Loading -> {
                }
                is WeatherViewModel.Status.Success -> {
                    val temperatureString = it.forecast.current.tempC.toInt().toString()
                    temperature.text = getString(R.string.celsius, temperatureString)
                    city_text_view.text = it.forecast.location.name
                    if (forecast_recycler.adapter == null) {
                        forecast_recycler.apply {
                            adapter = ForecastListAdapter(it.forecast.forecast.forecastday)
                            layoutManager = LinearLayoutManager(this.context)
                        }
                    }
                    val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                        forecast_recycler,
                        PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 1000f, 0f)
                    )

                    objectAnimator.start()
                }
                is WeatherViewModel.Status.Error -> {
                }
            }
        }

        weatherViewModel.currentStatus.observe(this, renderObserver)
    }

    override fun onStop() {
        super.onStop()
        /*Observer is removed here because of navigation - back issue. (i.e. Removing this line will not close the app when back button is pressed)*/
        weatherViewModel.currentStatus.removeObserver(renderObserver)
    }
}
