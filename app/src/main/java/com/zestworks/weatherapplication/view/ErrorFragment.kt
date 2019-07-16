package com.zestworks.weatherapplication.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.zestworks.weatherapplication.R
import com.zestworks.weatherapplication.viewmodel.ViewModelFactory
import com.zestworks.weatherapplication.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_error.*


class ErrorFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var renderObserver:Observer<WeatherViewModel.Status>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error, container, false)
    }


    override fun onStart() {
        super.onStart()

        weatherViewModel = ViewModelProviders.of(activity!!, ViewModelFactory)[WeatherViewModel::class.java]

        renderObserver = Observer {
            when(it){
                WeatherViewModel.Status.None -> {
                    val navOptionsBuilder = NavOptions.Builder()
                    navOptionsBuilder.setPopUpTo(R.id.mainFragment,true)
                    findNavController().navigate(ErrorFragmentDirections.actionErrorFragmentToMainFragment(),navOptionsBuilder.build())
                }
                WeatherViewModel.Status.Loading -> {}
                is WeatherViewModel.Status.Success -> {}
                is WeatherViewModel.Status.Error -> {
                    if ((weatherViewModel.currentStatus.value as WeatherViewModel.Status.Error).errorType == WeatherViewModel.ErrorType.LOCATION_PERMISSION_DENIED){
                        error_text.text = getString(R.string.enable_location_error_message)
                    }
                }
            }
        }

        weatherViewModel.currentStatus.observe(this, renderObserver)

        btn_retry.setOnClickListener {
            weatherViewModel.onRetryButtonClicked()
        }
    }

    override fun onStop() {
        super.onStop()
        /*Observer is removed here because of navigation - back issue. (i.e. Removing this line will not close the app when back button is pressed)*/
        weatherViewModel.currentStatus.removeObserver(renderObserver)
    }
}
