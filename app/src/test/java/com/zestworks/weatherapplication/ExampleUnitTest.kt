package com.zestworks.weatherapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zestworks.weatherapplication.model.Forecast
import com.zestworks.weatherapplication.model.Repository
import com.zestworks.weatherapplication.viewmodel.NetworkCallback
import com.zestworks.weatherapplication.viewmodel.WeatherViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var weatherViewModel: WeatherViewModel

    @Mock
    lateinit var repository: Repository

    @Captor
    lateinit var networkCallbackCaptor: ArgumentCaptor<NetworkCallback>

    @Before
    fun setup() {
        weatherViewModel = WeatherViewModel(repository)
    }

    @Test
    fun `Test Failure`() {
        assert(weatherViewModel.currentStatus.value is WeatherViewModel.Status.None)

        weatherViewModel.onFragmentStart()

        assert(weatherViewModel.currentStatus.value is WeatherViewModel.Status.Loading)

        val errorMessage = "Network failed"
        Mockito.verify(repository).loadWeatherData(capture(networkCallbackCaptor))
        networkCallbackCaptor.value.onFailure(errorMessage)

        assert(weatherViewModel.currentStatus.value is WeatherViewModel.Status.Error)
        assert((weatherViewModel.currentStatus.value as WeatherViewModel.Status.Error).reason == errorMessage)
    }

    @Test
    fun `Test network success`() {
        assert(weatherViewModel.currentStatus.value is WeatherViewModel.Status.None)

        weatherViewModel.onFragmentStart()

        assert(weatherViewModel.currentStatus.value is WeatherViewModel.Status.Loading)

        val forecast = Forecast()
        Mockito.verify(repository).loadWeatherData(capture(networkCallbackCaptor))
        networkCallbackCaptor.value.onSuccess(forecast = forecast)

        assert(weatherViewModel.currentStatus.value is WeatherViewModel.Status.Success)
        assert((weatherViewModel.currentStatus.value as WeatherViewModel.Status.Success).forecast == forecast)
    }

}