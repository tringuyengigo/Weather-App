package gdsvn.tringuyen.weatherapp.domain.entity

data class RequestEntity(
    val language: String?,
    val query: String?,
    val type: String?,
    val unit: String?
)