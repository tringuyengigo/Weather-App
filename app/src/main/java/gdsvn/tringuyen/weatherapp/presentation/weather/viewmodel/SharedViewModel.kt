package gdsvn.tringuyen.weatherapp.presentation.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gdsvn.tringuyen.weatherapp.presentation.entity.WeatherPresentation

class SharedViewModel : ViewModel() {

    val dataToShare = MutableLiveData<WeatherPresentation>()

    fun updateData(data: WeatherPresentation) {
        dataToShare.value = data
    }

    val dataStringToShare = MutableLiveData<String>()

    fun updateData(data: String) {
        dataStringToShare.value = data
    }
}