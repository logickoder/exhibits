package dev.logickoder.exhibits.data.remote

import dev.logickoder.exhibits.data.model.Exhibit
import dev.logickoder.exhibits.utils.ResultWrapper

interface ExhibitsLoader {

    suspend fun getExhibitsList(): ResultWrapper<List<Exhibit>>
}