package dev.logickoder.exhibits.presentation.exhibits

import androidx.compose.runtime.*
import dev.logickoder.exhibits.data.repository.ExhibitsRepository
import dev.logickoder.exhibits.di.ServiceLocator
import dev.logickoder.exhibits.utils.ResultWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ExhibitsState(
    private val scope: CoroutineScope,
) {
    private val repository = ServiceLocator.get<ExhibitsRepository>(
        ExhibitsRepository::class.simpleName!!
    )!!

    val exhibits = repository.exhibits.map { exhibits ->
        if (exhibits.isEmpty()) {
            ResultWrapper.Loading
        } else ResultWrapper.Success(exhibits)
    }

    var fetchError by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(true)

    init {
        refreshExhibits()
    }

    fun refreshExhibits() {
        scope.launch {
            fetchError = null
            isLoading = true
            val result = repository.refreshExhibits()
            if (result is ResultWrapper.Failure) {
                fetchError = result.error.message
            }
            isLoading = false
        }
    }
}

@Composable
fun rememberExhibitsState(scope: CoroutineScope) = remember {
    ExhibitsState(scope = scope)
}