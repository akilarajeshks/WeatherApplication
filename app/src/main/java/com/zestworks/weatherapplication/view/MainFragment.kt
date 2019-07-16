package com.zestworks.weatherapplication.view


import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.zestworks.weatherapplication.R
import com.zestworks.weatherapplication.viewmodel.ViewModelFactory
import com.zestworks.weatherapplication.viewmodel.WeatherViewModel
import com.zestworks.weatherapplication.viewmodel.WeatherViewModel.Status


class MainFragment : Fragment() {

    private val PERMISSION_CONSTANT = 1

    private lateinit var weatherViewModel: WeatherViewModel

    private lateinit var renderObserver: Observer<Status>


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

        renderObserver = Observer {
            when (it) {
                Status.None -> {
                    requestLocationPermission()
                }
                Status.Loading -> {
                    findNavController().navigate(R.id.action_mainFragment_to_loaderFragment)
                }
                is Status.Success -> {
                }
                is Status.Error -> {
                    val navOptionsBuilder = NavOptions.Builder()
                    navOptionsBuilder.setPopUpTo(R.id.mainFragment, true)
                    findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToErrorFragment(),
                        navOptionsBuilder.build()
                    )
                }
            }
        }

        weatherViewModel.currentStatus.observe(this, renderObserver)
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
        } else {
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                if (it == null){
                    weatherViewModel.onLocationPermissionDenied()
                }else{
                    weatherViewModel.onLocationFetched(it.latitude,it.longitude)
                }
            }
        }
    }


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CONSTANT -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
                    fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                        if (it == null){
                            weatherViewModel.onLocationPermissionDenied()
                        }else{
                            weatherViewModel.onLocationFetched(it.latitude,it.longitude)
                        }
                    }
                } else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    weatherViewModel.onLocationPermissionDenied()
                }
                return
            }
        }
    }

    override fun onStop() {
        super.onStop()
        /*Observer is removed here because of navigation - back issue. (i.e. Removing this line will not close the app when back button is pressed)*/
        weatherViewModel.currentStatus.removeObserver(renderObserver)
    }
}
