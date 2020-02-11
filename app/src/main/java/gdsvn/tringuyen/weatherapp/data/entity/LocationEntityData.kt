package gdsvn.tringuyen.weatherapp.data.entity

import com.google.gson.annotations.SerializedName

data class LocationEntityData(

    @SerializedName("country")
    val country: String?,
    @SerializedName("lat")
    val lat: String?,
    @SerializedName("localtime")
    val localtime: String?,
    @SerializedName("localtime_epoch")
    val localtime_epoch: Int?,
    @SerializedName("lon")
    val lon: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("region")
    val region: String?,
    @SerializedName("timezone_id")
    val timezone_id: String?,
    @SerializedName("utc_offset")
    val utc_offset: String?
)