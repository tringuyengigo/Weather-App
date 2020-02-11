package gdsvn.tringuyen.weatherapp.data.entity

import com.google.gson.annotations.SerializedName

data class RequestEntityData(

    @SerializedName("type")
    val language: String?,
    @SerializedName("query")
    val query: String?,
    @SerializedName("language")
    val type: String?,
    @SerializedName("unit")
    val unit: String?
)
