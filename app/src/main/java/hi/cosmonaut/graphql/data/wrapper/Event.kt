package hi.cosmonaut.graphql.data.wrapper

sealed class Event<T : Any> {
    var isHandled: Boolean = false
    data class Success<T>(val data: T) : Event<Any>()
    data class Error<T>(val data: T?, val message: String, val error: Throwable?) : Event<Any>()
    object Loading : Event<Any>()
}