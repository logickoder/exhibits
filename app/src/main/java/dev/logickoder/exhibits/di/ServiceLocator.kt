package dev.logickoder.exhibits.di

object ServiceLocator {
    private val store = mutableMapOf<String, Any?>()

    fun <T : Any> save(key: String, data: T) {
        store[key] = data
    }

    fun dispose(key: String) {
        store[key] = null
    }

    fun <T> get(key: String) = store[key] as T?
}