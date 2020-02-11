package gdsvn.tringuyen.weatherapp.domain.entity

import gdsvn.tringuyen.weatherapp.domain.entity.CurrentEntity
import gdsvn.tringuyen.weatherapp.domain.entity.LocationEntity
import gdsvn.tringuyen.weatherapp.domain.entity.RequestEntity

data class WeatherEntity(
    val current: CurrentEntity,
    val location: LocationEntity,
    val request: RequestEntity?
)