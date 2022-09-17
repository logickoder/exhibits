package dev.logickoder.exhibits.data.remote

import dev.logickoder.exhibits.data.model.Exhibit
import dev.logickoder.exhibits.utils.ResultWrapper
import io.ktor.client.call.*
import io.ktor.client.request.*

internal object RestExhibitsLoader: ExhibitsLoader {

    private const val ROUTE = "https://my-json-server.typicode.com/Reyst/exhibit_db/list"

    override suspend fun getExhibitsList(): ResultWrapper<List<Exhibit>> {
        return RemoteClient.call {
            get(ROUTE) {}.body()
        }
    }
}