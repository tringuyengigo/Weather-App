package gdsvn.tringuyen.weatherapp.domain.usecase

import android.util.Log
import gdsvn.tringuyen.weatherapp.domain.common.BaseFlowableUseCase
import gdsvn.tringuyen.weatherapp.domain.common.FlowableRxTransformer
import gdsvn.tringuyen.weatherapp.domain.entity.WeatherEntity
import gdsvn.tringuyen.weatherapp.domain.repository.WeatherRepository
import io.reactivex.Flowable


class GetWeatherUseCase(private val transformer: FlowableRxTransformer<WeatherEntity>,
                        private val repositories: WeatherRepository) : BaseFlowableUseCase<WeatherEntity>(transformer){


    companion object {
        private val TAG = "Test"
    }
    override fun createFlowable(data: Map<String, Any>?): Flowable<WeatherEntity> {
        Log.d(TAG, "On getWeatherByCity =============> city ${data?.get("city") as String} accessKey ${data?.get("access_key") as String}")
        return repositories.getRemoteWeatherByCity(data?.get("city") as String, data?.get("access_key") as String)
    }

//    fun getWeather(): Flowable<WeatherEntity>{
//        val data = HashMap<String, String>()
//        return single(data)
//    }

    fun getWeatherByCity(city: String, accessKey: String) : Flowable<WeatherEntity> {
        val data = HashMap<String, String>()
        data["city"] = city
        data["access_key"] = accessKey
        return single(data)
    }

}