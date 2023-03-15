package com.alientodevida.alientoapp.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.designsystem.R
import com.alientodevida.alientoapp.domain.common.CarouselItem
import com.alientodevida.alientoapp.domain.common.CategoryItemType

@Composable
fun SermonsPager(
    items: List<CarouselItem>,
    goToSermons: () -> Unit,
    goToSermon: (CarouselItem) -> Unit,
) {
    com.alientodevida.alientoapp.designsystem.components.Pager(
        items = items,
        goToItem = {
            when {
                it.youtubeItem != null -> goToSermon(it)
                it.categoryItem != null -> goToSermons()
            }
        },
    ) { item -> SermonsPageItem(item) }
}

@Composable
private fun SermonsPageItem(item: CarouselItem) {
    Box {
        com.alientodevida.alientoapp.designsystem.components.ImageWithShimmering(
            url = item.imageUrl,
            description = item.title,
        )

        Column {
            Spacer(Modifier.weight(0.38f))
            com.alientodevida.alientoapp.designsystem.components.Gradient(
                Modifier
                    .fillMaxWidth()
                    .weight(0.62f),
                startColor = Color.Transparent,
                endColor = MaterialTheme.colors.background,
            ) {
                Column {
                    if (item.categoryItem?.type == CategoryItemType.SERMONS) {
                        Spacer(Modifier.weight(1.0f))
                        SeeSermonsCard(Modifier.align(Alignment.Start))
                    }
                }
            }
        }
    }
}

@Composable
private fun SeeSermonsCard(modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(
            topStart = 0.dp,
            bottomStart = 0.dp,
            topEnd = 8.dp,
            bottomEnd = 8.dp,
        ),
        backgroundColor = Color(0xffff6f00),
    ) {
        Box(
            Modifier
                .padding(horizontal = 40.dp, vertical = 8.dp),
        ) {
            com.alientodevida.alientoapp.designsystem.components.Body2(
                text = "Ver prédicas",
                color = colorResource(R.color.pantone_white_c),
            )
        }
    }

    Spacer(Modifier.height(40.dp))
}
