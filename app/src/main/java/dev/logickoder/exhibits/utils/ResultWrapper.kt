package dev.logickoder.exhibits.utils

/**
 * Represents the result of an api call
 */
sealed class ResultWrapper<out T> {

    /**
     * The call was a success
     * @param data The data that is to be returned in a successful call event
     * */
    data class Success<T>(val data: T) : ResultWrapper<T>()

    object Loading: ResultWrapper<Nothing>()

    /**
     * The call failed
     * @param error The exception thrown on a failed call event
     * */
    open class Failure(val error: Throwable) : ResultWrapper<Nothing>() {
        constructor(message: String) : this(Throwable(message))
    }
}