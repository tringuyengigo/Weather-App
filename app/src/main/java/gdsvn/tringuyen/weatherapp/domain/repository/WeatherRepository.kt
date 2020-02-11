package gdsvn.tringuyen.weatherapp.domain.repository

import gdsvn.tringuyen.weatherapp.data.entity.WeatherEntityData
import gdsvn.tringuyen.weatherapp.domain.entity.WeatherEntity
import io.reactivex.Flowable

interface WeatherRepository {

    fun getWeatherByCity(city: String, accessKey: String): Flowable<WeatherEntity>
    fun getLocalWeatherByCity(): Flowable<WeatherEntityData>
    fun getRemoteWeatherByCity(city: String, accessKey: String): Flowable<WeatherEntity>

}