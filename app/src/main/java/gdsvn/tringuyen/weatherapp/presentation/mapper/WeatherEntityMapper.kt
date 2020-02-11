package gdsvn.tringuyen.weatherapp.presentation.mapper

import gdsvn.tringuyen.weatherapp.domain.common.Mapper
import gdsvn.tringuyen.weatherapp.domain.entity.CurrentEntity
import gdsvn.tringuyen.weatherapp.domain.entity.LocationEntity
import gdsvn.tringuyen.weatherapp.domain.entity.RequestEntity
import gdsvn.tringuyen.weatherapp.domain.entity.WeatherEntity
import gdsvn.tringuyen.weatherapp.presentation.entity.CurrentPresentation
import gdsvn.tringuyen.weatherapp.presentation.entity.LocationPresentaion
import gdsvn.tringuyen.weatherapp.presentation.entity.RequestPresentation
import gdsvn.tringuyen.weatherapp.presentation.entity.WeatherPresentation


class WeatherEntityMapper : Mapper<WeatherEntity, WeatherPresentation>() {
    override fun mapFrom(from: WeatherEntity): WeatherPresentation = WeatherPresentation (
        current = mapCurrentEntityToCurrentPresentation(from.current),
        location = mapLocationEntityToLocationPresentation(from.location),
        request = mapRequestEntityToRequestPresentation(from.request)
    )

    private fun mapRequestEntityToRequestPresentation(requestData: RequestEntity?): RequestPresentation = RequestPresentation (
        language = requestData?.language,
        query = requestData?.query,
        type = requestData?.type,
        unit = requestData?.unit
    )

    private fun mapLocationEntityToLocationPresentation(locationData: LocationEntity): LocationPresentaion = LocationPresentaion (
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

    private fun mapCurrentEntityToCurrentPresentation(currentData: CurrentEntity): CurrentPresentation = CurrentPresentation (
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

}