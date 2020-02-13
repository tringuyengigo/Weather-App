package gdsvn.tringuyen.weatherapp.data.repository


import android.util.Log
import gdsvn.tringuyen.weatherapp.data.api.RemoteWeatherApi
import gdsvn.tringuyen.weatherapp.data.entity.WeatherEntityDataToWeatherEntityMapper
import gdsvn.tringuyen.weatherapp.domain.entity.WeatherEntity
import io.reactivex.Flowable

class WeatherRemoteImpl constructor(private val api: RemoteWeatherApi,
                                    private val dataToEntityMapper: WeatherEntityDataToWeatherEntityMapper): WeatherDataStore {

    override fun getWeatherCity(city: String, accessKey: String): Flowable<WeatherEntity> {

        return api.getWeatherByCity(city = city, access_key = accessKey).map {
            dataToEntityMapper.mapToEntity(it)
        }
    }


}