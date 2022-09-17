package dev.logickoder.exhibits.presentation.exhibits

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.logickoder.exhibits.core.theme.ExhibitsTheme
import dev.logickoder.exhibits.data.model.Exhibit

@Composable
fun ExhibitItems(
    modifier: Modifier = Modifier,
    items: List<Exhibit>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(24.dp, 16.dp, 0.dp, 16.dp),
        content = {
            items(items) { item ->
                ExhibitItem(
                    exhibit = item,
                    modifier = modifier.padding(bottom = 16.dp),
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun ExhibitsItemsPreview() = ExhibitsTheme {
    ExhibitItems(items = (1..10).map { exhibit ->
        Exhibit(
            "Exhibit $exhibit",
            images = (exhibit..exhibit * 6).map { it.toString() },
        )
    })
}