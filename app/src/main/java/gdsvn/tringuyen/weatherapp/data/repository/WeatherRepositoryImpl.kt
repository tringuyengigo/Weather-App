package gdsvn.tringuyen.weatherapp.data.repository



import android.util.Log
import gdsvn.tringuyen.weatherapp.data.entity.WeatherEntityData
import gdsvn.tringuyen.weatherapp.domain.entity.WeatherEntity
import gdsvn.tringuyen.weatherapp.domain.repository.WeatherRepository
import gdsvn.tringuyen.weatherapp.domain.usecase.GetWeatherUseCase
import io.reactivex.Flowable

class WeatherRepositoryImpl(private val remote: WeatherRemoteImpl,
                            private val cache: WeatherCacheImpl
) : WeatherRepository {

    override fun getWeatherByCity(city: String, accessKey: String): Flowable<WeatherEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocalWeatherByCity(): Flowable<WeatherEntityData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRemoteWeatherByCity(city: String, accessKey: String): Flowable<WeatherEntity> {
        Log.d("Test", "On getRemoteWeatherByCity =============> city ${city} accessKey ${accessKey}")
        return remote.getWeatherCity(city = city, accessKey = accessKey)
    }


}