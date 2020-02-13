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
import timber.log.Timber


class WeatherViewModel(private val getWeatherUseCase: GetWeatherUseCase,
                       private val mapper: Mapper<WeatherEntity, WeatherPresentation>
) : BaseViewModel() {

    companion object {
        private val TAG = "Test"
    }

    var mWeather = MutableLiveData<Data<WeatherPresentation>>()

    fun fetchWeather(city: String) {
        val disposable = getWeatherUseCase.getWeatherByCity(city = city, accessKey = "8c6e5b1cc92648064427729c45a37f5d")
                .flatMap {
                    mapper.FlowableItem(it)
                }
                .subscribe({ response ->
                    mWeather.value = Data(responseType = Status.SUCCESSFUL, data = response)
                }, { error ->
                    mWeather.value = Data(responseType = Status.ERROR, error = Error(error.message))
                }, {
                    Timber.d("On Complete Called")
                })

        addDisposable(disposable)
    }

    fun getWeatherLiveData() = mWeather
}