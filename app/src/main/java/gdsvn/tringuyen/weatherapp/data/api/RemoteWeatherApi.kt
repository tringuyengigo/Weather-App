package gdsvn.tringuyen.weatherapp.data.api


import gdsvn.tringuyen.weatherapp.data.entity.WeatherEntityData
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteWeatherApi {

    //http://api.weatherstack.com/current?access_key=8c6e5b1cc92648064427729c45a37f5d&query=New%20York
    @GET("/current?")
    fun getWeatherByCity(@Query("access_key") access_key: String,
                         @Query("query") city: String) : Flowable<WeatherEntityData>


}