package com.zestworks.weatherapplication.view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.zestworks.weatherapplication.R
import com.zestworks.weatherapplication.viewmodel.ViewModelFactory
import com.zestworks.weatherapplication.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_loader.*


class LoaderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loader, container, false)
    }

    override fun onStart() {
        super.onStart()

        val weatherViewModel = ViewModelProviders.of(activity!!, ViewModelFactory)[WeatherViewModel::class.java]

        weatherViewModel.currentStatus.observe(this, Observer {
            when (it) {
                WeatherViewModel.Status.None -> {

                }
                WeatherViewModel.Status.Loading -> {
                    loader.visibility = View.VISIBLE
                    ObjectAnimator.ofFloat(loader, "rotation", 180f, 0f).apply {
                        duration = 2000
                        repeatCount = ObjectAnimator.INFINITE
                        repeatMode = ObjectAnimator.RESTART
                        start()
                    }
                }
                is WeatherViewModel.Status.Success -> {
                    val navOptionsBuilder = NavOptions.Builder()
                    navOptionsBuilder.setPopUpTo(R.id.mainFragment,true)
                    findNavController().navigate(LoaderFragmentDirections.actionLoaderFragmentToWeatherFragment(),navOptionsBuilder.build())
                }
                is WeatherViewModel.Status.Error -> {
                    val navOptionsBuilder = NavOptions.Builder()
                    navOptionsBuilder.setPopUpTo(R.id.mainFragment,true)
                    findNavController().navigate(LoaderFragmentDirections.actionLoaderFragmentToErrorFragment(),navOptionsBuilder.build())
                }
            }
        })
    }
}
