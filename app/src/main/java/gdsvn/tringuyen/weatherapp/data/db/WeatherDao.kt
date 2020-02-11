package gdsvn.tringuyen.weatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gdsvn.tringuyen.weatherapp.data.entity.WeatherEntityData
import io.reactivex.Flowable

@Dao
interface WeatherDao{

    @Query("SELECT * FROM weather LIMIT :limit OFFSET :offset")
    fun getLimitItemWeather(limit: Int,offset: Int): Flowable<List<WeatherEntityData>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveItemWeather(itemWeather: WeatherEntityData?)

    @Query("DELETE FROM weather")
    fun clear()

}