package gdsvn.tringuyen.weatherapp.data.repository


import android.util.Log
import gdsvn.tringuyen.weatherapp.data.api.RemoteWeatherApi
import gdsvn.tringuyen.weatherapp.data.entity.WeatherEntityDataToWeatherEntityMapper
import gdsvn.tringuyen.weatherapp.domain.entity.WeatherEntity
import io.reactivex.Flowable

class WeatherRemoteImpl constructor(private val api: RemoteWeatherApi,
                                    private val dataToEntityMapper: WeatherEntityDataToWeatherEntityMapper): WeatherDataStore {

    override fun getWeatherCity(city: String, accessKey: String): Flowable<WeatherEntity> {
        Log.d("Test", "On WeatherRemoteImpl getRemoteWeatherByCity =============> ¢¢¢¢¢ city ${api.getWeatherByCity("8c6e5b1cc92648064427729c45a37f5d", "London").map {
            Log.d("Test", "On WeatherRemoteImpl getRemoteWeatherByCity =============> xxxxx city ${it.locationData}")

        }}")

        return api.getWeatherByCity(access_key = accessKey, city = city).map {
            Log.d("Test", "On WeatherRemoteImpl getRemoteWeatherByCity =============> city ${it} accessKey ${accessKey}")

            dataToEntityMapper.mapToEntity(it)
        }
    }


}