package com.zestworks.weatherapplication.view


import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.zestworks.weatherapplication.R
import com.zestworks.weatherapplication.viewmodel.ViewModelFactory
import com.zestworks.weatherapplication.viewmodel.WeatherViewModel


class MainFragment : Fragment() {

    private val PERMISSION_CONSTANT = 1

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onStart() {
        super.onStart()

        weatherViewModel = ViewModelProviders.of(activity!!, ViewModelFactory)[WeatherViewModel::class.java]

        weatherViewModel.currentStatus.observe(this, Observer {
            when(it){
                WeatherViewModel.Status.None -> {
                    requestLocationPermission()
                }
                WeatherViewModel.Status.Loading -> {
                    findNavController().navigate(R.id.action_mainFragment_to_loaderFragment)
                }
                is WeatherViewModel.Status.Success -> {}
                is WeatherViewModel.Status.Error -> {
                    findNavController().navigate(R.id.action_mainFragment_to_errorFragment)
                }
            }
        })
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                activity!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_CONSTANT
            )
        }else{
            val locationManager = (context!!.getSystemService(Context.LOCATION_SERVICE)) as LocationManager
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            weatherViewModel.onLocationFetched(lastKnownLocation.latitude,lastKnownLocation.longitude)
        }
    }


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CONSTANT -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    val locationManager = (context!!.getSystemService(Context.LOCATION_SERVICE)) as LocationManager
                    val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                    weatherViewModel.onLocationFetched(lastKnownLocation.latitude,lastKnownLocation.longitude)
                } else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    weatherViewModel.onLocationPermissionDenied()
                }
                return
            }
        }
    }
}
