package gdsvn.tringuyen.weatherapp.domain.usecase


import gdsvn.tringuyen.weatherapp.domain.common.BaseFlowableUseCase
import gdsvn.tringuyen.weatherapp.domain.common.FlowableRxTransformer
import gdsvn.tringuyen.weatherapp.data.entity.WeatherEntityData
import gdsvn.tringuyen.weatherapp.domain.repository.WeatherRepository
import io.reactivex.Flowable

class GetLocalWeatherUseCase(private val transformer: FlowableRxTransformer<WeatherEntityData>,
                             private val repositories: WeatherRepository): BaseFlowableUseCase<WeatherEntityData>(transformer){

    companion object {
        private const val PARAM_FILE_NEWS_ENTITY = "param:WeatherStatus"
    }



    fun getWeather(): Flowable<WeatherEntityData>{
        val data = HashMap<String, String>()
        return single(data)
    }

    override fun createFlowable(data: Map<String, Any>?): Flowable<WeatherEntityData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}