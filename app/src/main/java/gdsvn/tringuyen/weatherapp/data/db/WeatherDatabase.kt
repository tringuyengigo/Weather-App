package gdsvn.tringuyen.weatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gdsvn.tringuyen.weatherapp.data.entity.WeatherEntityData
import gdsvn.tringuyen.weatherapp.data.entity.converters.DataConverters


@Database(entities = [WeatherEntityData::class], version = 1)
@TypeConverters(DataConverters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
}