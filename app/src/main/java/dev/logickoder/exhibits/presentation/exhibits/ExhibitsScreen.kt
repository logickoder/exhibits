package dev.logickoder.exhibits.presentation.exhibits

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.logickoder.exhibits.R
import dev.logickoder.exhibits.data.model.Exhibit
import dev.logickoder.exhibits.utils.ResultWrapper

@Preview(showBackground = true)
@Composable
fun ExhibitsScreen(
    modifier: Modifier = Modifier,
    state: ExhibitsState = rememberExhibitsState(rememberCoroutineScope()),
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.phone_exhibits))
                }
            )
        },
        content = { padding ->
            SwipeRefresh(
                modifier = modifier.padding(padding),
                state = rememberSwipeRefreshState(isRefreshing = state.isLoading),
                onRefresh = state::refreshExhibits,
                content = {
                    val exhibits by state.exhibits.collectAsState(initial = ResultWrapper.Loading)

                    LaunchedEffect(key1 = state.fetchError, block = {
                        state.fetchError?.let { error ->
                            scaffoldState.snackbarHostState.showSnackbar(error)
                        }
                    })

                    when {
                        exhibits == ResultWrapper.Loading || state.isLoading -> {
                            ExhibitLoadingItems()
                        }
                        exhibits is ResultWrapper.Success -> {
                            ExhibitItems(
                                items = (exhibits as ResultWrapper.Success<List<Exhibit>>).data
                            )
                        }
                    }
                }
            )
        }
    )
}