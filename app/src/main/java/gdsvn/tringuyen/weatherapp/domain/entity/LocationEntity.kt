package gdsvn.tringuyen.weatherapp.domain.entity

data class LocationEntity(
    val country: String?,
    val lat: String?,
    val localtime: String?,
    val localtime_epoch: Int?,
    val lon: String?,
    val name: String?,
    val region: String?,
    val timezone_id: String?,
    val utc_offset: String?
)