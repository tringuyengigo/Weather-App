package gdsvn.tringuyen.weatherapp.presentation.di

import androidx.room.Room
import gdsvn.tringuyen.weatherapp.data.api.RemoteWeatherApi
import gdsvn.tringuyen.weatherapp.data.db.WeatherDatabase
import gdsvn.tringuyen.weatherapp.data.entity.WeatherEntityDataToWeatherEntityMapper
import gdsvn.tringuyen.weatherapp.data.entity.WeatherEntityToWeatherEntityDataMapper
import gdsvn.tringuyen.weatherapp.data.repository.WeatherCacheImpl
import gdsvn.tringuyen.weatherapp.data.repository.WeatherRemoteImpl
import gdsvn.tringuyen.weatherapp.data.repository.WeatherRepositoryImpl
import gdsvn.tringuyen.weatherapp.domain.repository.WeatherRepository
import gdsvn.tringuyen.weatherapp.domain.usecase.GetWeatherUseCase
import gdsvn.tringuyen.weatherapp.presentation.common.AsyncFlowableTransformer
import gdsvn.tringuyen.weatherapp.presentation.mapper.WeatherEntityMapper
import gdsvn.tringuyen.weatherapp.presentation.weather.viewmodel.SharedViewModel
import gdsvn.tringuyen.weatherapp.presentation.weather.viewmodel.WeatherViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import org.koin.android.viewmodel.ext.koin.viewModel
import retrofit2.Retrofit

val mRepositoryModules = module {
    single(name = "remote") { WeatherRemoteImpl(api = get(API), dataToEntityMapper = WeatherEntityDataToWeatherEntityMapper()) }
    single(name = "local") {
        WeatherCacheImpl(database = get(DATABASE), entityToDataMapper = WeatherEntityToWeatherEntityDataMapper(),
                dataToEntityMapper = WeatherEntityDataToWeatherEntityMapper()
        )
    }
    single { WeatherRepositoryImpl(remote = get("remote"), cache = get("local")) as WeatherRepository }
}

val mUseCaseModules = module {
    factory(name = "getWeatherUseCase") { GetWeatherUseCase(transformer = AsyncFlowableTransformer(), repositories = get()) }
}

val mNetworkModules = module {
    single(name = RETROFIT_INSTANCE) { createNetworkClient(BASE_URL) }
    single(name = API) { (get(RETROFIT_INSTANCE) as Retrofit).create(RemoteWeatherApi::class.java) }
}

val mLocalModules = module {
    single(name = DATABASE) { Room.databaseBuilder(androidApplication(), WeatherDatabase::class.java, "news_articles").build() }
}

val mViewModels = module {
    viewModel {
        WeatherViewModel(
            getWeatherUseCase = get(GET_NEWS_USECASE),
            mapper = WeatherEntityMapper()
        )
    }
}

private const val BASE_URL = "http://api.weatherstack.com"
private const val RETROFIT_INSTANCE = "Retrofit"
private const val API = "Api"
private const val GET_NEWS_USECASE = "getWeatherUseCase"
private const val REMOTE = "remote response"
private const val DATABASE = "database"