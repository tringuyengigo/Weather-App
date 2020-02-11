package gdsvn.tringuyen.weatherapp.presentation.entity



data class WeatherPresentation(
    val current: CurrentPresentation,
    val location: LocationPresentaion,
    val request: RequestPresentation?
)