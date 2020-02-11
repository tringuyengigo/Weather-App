package gdsvn.tringuyen.weatherapp.data.repository

import gdsvn.tringuyen.weatherapp.domain.entity.WeatherEntity
import io.reactivex.Flowable


interface WeatherDataStore{
    fun getWeatherCity(city: String, accessKey: String): Flowable<WeatherEntity>
}