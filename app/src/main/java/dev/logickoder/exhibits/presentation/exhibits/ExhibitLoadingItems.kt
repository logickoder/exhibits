package dev.logickoder.exhibits.presentation.exhibits

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.logickoder.exhibits.data.model.Exhibit

@Preview(showBackground = true)
@Composable
fun ExhibitLoadingItems(
    modifier: Modifier = Modifier,
    items: List<Exhibit> = (1..10).map { exhibit ->
        Exhibit(
            "",
            images = (exhibit..exhibit * 6).map { it.toString() },
        )
    },
) = ExhibitItems(items = items, modifier = modifier)