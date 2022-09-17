package dev.logickoder.exhibits.data.remote

import dev.logickoder.exhibits.utils.ResultWrapper
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal val RemoteClient = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json(Json {
            encodeDefaults = true
            prettyPrint = true
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Napier.v(message)
            }
        }
        level = LogLevel.BODY
    }
    install(HttpTimeout) {
        val timeout = 5_000L
        socketTimeoutMillis = timeout
        requestTimeoutMillis = timeout
        connectTimeoutMillis = timeout
    }
    install(DefaultRequest) {
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
    }
}

suspend fun <T> HttpClient.call(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend HttpClient.() -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(this@call.apiCall())
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            Napier.e(e.response.status.description)
            ResultWrapper.Failure(e)
        } catch (e: ClientRequestException) {
            // 4xx - responses
            Napier.e(e.response.status.description)
            ResultWrapper.Failure(e)
        } catch (e: ServerResponseException) {
            // 5xx - responses
            Napier.e(e.response.status.description)
            ResultWrapper.Failure(e)
        } catch (throwable: Throwable) {
            Napier.e(throwable.message.toString())
            ResultWrapper.Failure(throwable)
        }
    }
}