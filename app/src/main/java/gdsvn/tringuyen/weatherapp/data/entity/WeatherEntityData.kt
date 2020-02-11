package gdsvn.tringuyen.weatherapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import gdsvn.tringuyen.weatherapp.domain.entity.CurrentEntity
import gdsvn.tringuyen.weatherapp.domain.entity.LocationEntity
import gdsvn.tringuyen.weatherapp.domain.entity.RequestEntity
import gdsvn.tringuyen.weatherapp.domain.entity.WeatherEntity

@Entity(tableName = "weather")
data class WeatherEntityData(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("current")
    val currentData: CurrentEntityData,
    @SerializedName("location")
    val locationData: LocationEntityData,
    @SerializedName("request")
    val requestData: RequestEntityData?
)

class WeatherEntityDataToWeatherEntityMapper constructor() {

    fun mapToEntity(data: WeatherEntityData?): WeatherEntity? = WeatherEntity(
        current = mapToCurrentEntity(data?.currentData),
        location = mapToLocationEntity(data?.locationData),
        request =  mapToRequestEntity(data?.requestData)
    )

    fun mapToCurrentEntity(currentData: CurrentEntityData?): CurrentEntity = CurrentEntity (
        cloudcover = currentData?.cloudcover,
        feelslike = currentData?.feelslike,
        humidity = currentData?.humidity,
        is_day = currentData?.is_day,
        observation_time = currentData?.observation_time,
        precip = currentData?.precip,
        pressure = currentData?.pressure,
        temperature = currentData?.temperature,
        uv_index = currentData?.uv_index,
        visibility = currentData?.visibility,
        weather_code = currentData?.weather_code,
        weather_descriptions = currentData?.weather_descriptions,
        weather_icons = currentData?.weather_icons,
        wind_degree = currentData?.wind_degree,
        wind_dir = currentData?.wind_dir,
        wind_speed = currentData?.wind_speed
    )

    fun mapToLocationEntity(locationData: LocationEntityData?): LocationEntity = LocationEntity(
        country = locationData?.country,
        lat = locationData?.lat,
        localtime = locationData?.localtime,
        localtime_epoch = locationData?.localtime_epoch,
        lon = locationData?.lon,
        name = locationData?.name,
        region = locationData?.region,
        timezone_id = locationData?.timezone_id,
        utc_offset = locationData?.utc_offset
    )

    fun mapToRequestEntity(requestData: RequestEntityData?): RequestEntity? = RequestEntity(
        language = requestData?.language,
        query = requestData?.query,
        type = requestData?.type,
        unit = requestData?.unit
    )

}


class WeatherEntityToWeatherEntityDataMapper constructor() {

    fun mapToEntity(data: WeatherEntity?): WeatherEntityData? = WeatherEntityData(
        currentData = mapToCurrentEntityData(data?.current),
        locationData = mapToLocationEntityData(data?.location),
        requestData =  mapToRequestEntityData(data?.request)
    )

    fun mapToCurrentEntityData(currentEntity: CurrentEntity?): CurrentEntityData = CurrentEntityData (
        cloudcover = currentEntity?.cloudcover,
        feelslike = currentEntity?.feelslike,
        humidity = currentEntity?.humidity,
        is_day = currentEntity?.is_day,
        observation_time = currentEntity?.observation_time,
        precip = currentEntity?.precip,
        pressure = currentEntity?.pressure,
        temperature = currentEntity?.temperature,
        uv_index = currentEntity?.uv_index,
        visibility = currentEntity?.visibility,
        weather_code = currentEntity?.weather_code,
        weather_descriptions = currentEntity?.weather_descriptions,
        weather_icons = currentEntity?.weather_icons,
        wind_degree = currentEntity?.wind_degree,
        wind_dir = currentEntity?.wind_dir,
        wind_speed = currentEntity?.wind_speed
    )

    fun mapToLocationEntityData(locationEntity: LocationEntity?): LocationEntityData = LocationEntityData (
        country = locationEntity?.country,
        lat = locationEntity?.lat,
        localtime = locationEntity?.localtime,
        localtime_epoch = locationEntity?.localtime_epoch,
        lon = locationEntity?.lon,
        name = locationEntity?.name,
        region = locationEntity?.region,
        timezone_id = locationEntity?.timezone_id,
        utc_offset = locationEntity?.utc_offset
    )

    fun mapToRequestEntityData(requestEntity: RequestEntity?): RequestEntityData? = RequestEntityData(
        language = requestEntity?.language,
        query = requestEntity?.query,
        type = requestEntity?.type,
        unit = requestEntity?.unit
    )

}