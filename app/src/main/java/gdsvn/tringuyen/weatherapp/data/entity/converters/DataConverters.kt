package gdsvn.tringuyen.weatherapp.data.entity.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gdsvn.tringuyen.weatherapp.data.entity.CurrentEntityData
import gdsvn.tringuyen.weatherapp.data.entity.LocationEntityData
import gdsvn.tringuyen.weatherapp.data.entity.RequestEntityData
import java.lang.reflect.Type
import java.util.*


class DataConverters {

    @TypeConverter
    fun requestDataToString(data: RequestEntityData): String = Gson().toJson(data)

    @TypeConverter
    fun storeStringToRequestEntityData(value: String): RequestEntityData = Gson().fromJson(value, RequestEntityData::class.java)

    @TypeConverter
    fun locationDataToString(data: LocationEntityData): String = Gson().toJson(data)

    @TypeConverter
    fun storeStringToLocationData(value: String): LocationEntityData = Gson().fromJson(value, LocationEntityData::class.java)


    @TypeConverter
    fun currentDataToString(data: CurrentEntityData): String = Gson().toJson(data)

    @TypeConverter
    fun storeStringToCurrentData(value: String): CurrentEntityData = Gson().fromJson(value, CurrentEntityData::class.java)

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<String?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type =
            object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<String?>?): String? {
        return Gson().toJson(someObjects)
    }
}


