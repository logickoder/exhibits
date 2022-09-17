package dev.logickoder.exhibits.presentation.exhibits

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import dev.logickoder.exhibits.core.theme.ExhibitsTheme
import dev.logickoder.exhibits.data.model.Exhibit

@Composable
fun ExhibitItem(
    exhibit: Exhibit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            Text(
                exhibit.title,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h6
            )
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                content = {
                    exhibit.images.forEach { image ->
                        ExhibitImageItem(
                            image = image,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                }
            )
        }
    )
}

@Composable
private fun ExhibitImage(
    image: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    var isLoading by remember {
        mutableStateOf(true)
    }
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .memoryCacheKey(image)
            .diskCacheKey(image)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build(),
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
        },
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .placeholder(
                visible = isLoading,
                highlight = PlaceholderHighlight.shimmer(),
                color = Color.LightGray,
            )
    )
}

@Composable
private fun ExhibitImageItem(
    image: String,
    modifier: Modifier = Modifier,
) {
    var showDialog by remember { mutableStateOf(false) }
    ExhibitImage(image = image, modifier = modifier
        .size(150.dp)
        .clickable {
            showDialog = true
        })
    if (showDialog) {
        ExhibitImageDialog(image = image, dismiss = { showDialog = false })
    }
}

@Composable
fun ExhibitImageDialog(
    image: String,
    modifier: Modifier = Modifier,
    dismiss: () -> Unit,
) = AlertDialog(
    modifier = modifier,
    onDismissRequest = dismiss,
    text = {
        ExhibitImage(
            image = image,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
    },
    confirmButton = {
        TextButton(
            onClick = dismiss,
            content = {
                Text(stringResource(id = android.R.string.yes))
            }
        )
    }
)

@Preview(showBackground = true)
@Composable
private fun ExhibitItemPreview() = ExhibitsTheme {
    ExhibitItem(
        exhibit = Exhibit(
            "Exhibit",
            (1..6).map { it.toString() })
    )
}