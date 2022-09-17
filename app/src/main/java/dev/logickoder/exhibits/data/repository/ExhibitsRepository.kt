package dev.logickoder.exhibits.data.repository

import android.content.Context
import dev.logickoder.exhibits.data.local.ExhibitsDataStore
import dev.logickoder.exhibits.data.remote.ExhibitsLoader
import dev.logickoder.exhibits.data.remote.RestExhibitsLoader
import dev.logickoder.exhibits.utils.ResultWrapper

class ExhibitsRepository internal constructor(
    private val loader: ExhibitsLoader,
    private val context: Context,
) {

    val exhibits = ExhibitsDataStore.get(context)

    suspend fun refreshExhibits(): ResultWrapper<Unit> {
        return when(val data = loader.getExhibitsList()) {
            is ResultWrapper.Failure -> {
                ResultWrapper.Failure(data.error)
            }
            ResultWrapper.Loading -> ResultWrapper.Loading
            is ResultWrapper.Success -> {
                ExhibitsDataStore.save(data.data, context)
                ResultWrapper.Success(Unit)
            }
        }
    }
}