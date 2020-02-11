package gdsvn.tringuyen.weatherapp.data.repository


import gdsvn.tringuyen.weatherapp.data.db.WeatherDao
import gdsvn.tringuyen.weatherapp.data.db.WeatherDatabase
import gdsvn.tringuyen.weatherapp.data.entity.WeatherEntityDataToWeatherEntityMapper
import gdsvn.tringuyen.weatherapp.data.entity.WeatherEntityToWeatherEntityDataMapper
import gdsvn.tringuyen.weatherapp.domain.entity.WeatherEntity
import io.reactivex.Flowable

class WeatherCacheImpl(private val database: WeatherDatabase,
                       private val entityToDataMapper: WeatherEntityToWeatherEntityDataMapper,
                       private val dataToEntityMapper: WeatherEntityDataToWeatherEntityMapper
) : WeatherDataStore {

    private val dao: WeatherDao = database.getWeatherDao()

    fun saveWeather(dataWeatherEntity: WeatherEntity) {
        dao.clear()
        dao.saveItemWeather(entityToDataMapper.mapToEntity(dataWeatherEntity))
    }

    override fun getWeatherCity(city: String, accessKey: String): Flowable<WeatherEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}