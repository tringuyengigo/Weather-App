package gdsvn.tringuyen.weatherapp.presentation.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import android.util.Log
import gdsvn.tringuyen.weatherapp.domain.common.Mapper
import gdsvn.tringuyen.weatherapp.domain.entity.WeatherEntity
import gdsvn.tringuyen.weatherapp.domain.usecase.GetWeatherUseCase
import gdsvn.tringuyen.weatherapp.presentation.common.BaseViewModel
import gdsvn.tringuyen.weatherapp.presentation.entity.Data
import gdsvn.tringuyen.weatherapp.presentation.entity.Status
import gdsvn.tringuyen.weatherapp.presentation.entity.WeatherPresentation


class WeatherViewModel(private val getWeatherUseCase: GetWeatherUseCase,
                       private val mapper: Mapper<WeatherEntity, WeatherPresentation>
) : BaseViewModel() {

    companion object {
        private val TAG = "Test"
    }

    var mWeather = MutableLiveData<Data<WeatherPresentation>>()

    fun fetchWeather() {
        Log.d(TAG, "On fetchWeather =============> ")
        val disposable = getWeatherUseCase.getWeatherByCity(city = "New York", accessKey = "8c6e5b1cc92648064427729c45a37f5d")
                .flatMap {
                    Log.d(TAG, "On fetchWeather =============> $it")

                    mapper.FlowableItem(it)
                }
                .subscribe({ response ->
                    Log.d(TAG, "On Next Called")
                    mWeather.value = Data(responseType = Status.SUCCESSFUL, data = response)
                }, { error ->
                    mWeather.value = Data(responseType = Status.ERROR, error = Error(error.message))
                }, {
                    Log.d(TAG, "On Complete Called")
                })

        addDisposable(disposable)
    }

    fun getWeatherLiveData() = mWeather
}