package com.alientodevida.alientoapp.church

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.domain.video.YoutubeVideo
import com.alientodevida.alientoapp.ui.extensions.goToYoutubeVideo
import com.alientodevida.alientoapp.ui.utils.LocalUtils

private const val US_VIDEO = "ieOwvnk2B4I"
private const val usImageUrl: String = "https://img.youtube.com/vi/$US_VIDEO/hqdefault.jpg"

@Composable
fun Church(
    viewModel: ChurchViewModel,
    onBackPressed: () -> Unit,
) {
    val context = LocalContext.current
    val utils = LocalUtils.current

    EditCreateCampusContent(
        latestVideo = viewModel.latestVideo,
        usImageUrl = usImageUrl,
        openVideo = { context.goToYoutubeVideo(US_VIDEO, utils.youtubeKey) },
        goToVideo = { id -> context.goToYoutubeVideo(id, utils.youtubeKey) },
        onBackPressed = onBackPressed,
    )
}

@Composable
fun EditCreateCampusContent(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    latestVideo: YoutubeVideo?,
    usImageUrl: String,
    openVideo: () -> Unit,
    goToVideo: (String) -> Unit,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(onBackPressed = onBackPressed)
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .background(color = MaterialTheme.colors.background),
        ) {
            EditCreateCampusBody(
                latestVideo = latestVideo,
                usImageUrl = usImageUrl,
                openVideo = openVideo,
                goToVideo = goToVideo,
            )
        }
    }
}

// TODO can we extract a component ?
@Composable
fun TopAppBar(
    onBackPressed: () -> Unit,
) {
    val modifier = Modifier.size(width = 60.dp, height = 50.dp)

    androidx.compose.material.TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.logo_negro),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onBackground),
                contentScale = ContentScale.Inside,
                alignment = Alignment.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                contentDescription = null,
            )
        },
        navigationIcon = {
            com.alientodevida.alientoapp.designsystem.components.ClickableIcon(
                modifier = modifier,
                icon = R.drawable.ic_back_24,
                contentDescription = "Back Button",
                tint = MaterialTheme.colors.onBackground,
                onClick = onBackPressed,
            )
        },
        actions = {
            Box(modifier = modifier, contentAlignment = Alignment.Center) { }
        },
        backgroundColor = MaterialTheme.colors.background,
    )
}

@Composable
fun EditCreateCampusBody(
    latestVideo: YoutubeVideo?,
    usImageUrl: String,
    openVideo: () -> Unit,
    goToVideo: (String) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .verticalScroll(scrollState)
            .fillMaxWidth()
    ) {
        com.alientodevida.alientoapp.designsystem.components.ImageWithShimmering(
            modifier = Modifier
                .height(200.dp)
                .clickable {
                    openVideo()
                },
            url = usImageUrl,
            description = "Aliento de Vida",
        )

        Column(Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            com.alientodevida.alientoapp.designsystem.components.H5(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "Aliento de Vida",
                color = MaterialTheme.colors.onBackground,
            )

            Spacer(modifier = Modifier.height(16.dp))
            com.alientodevida.alientoapp.designsystem.components.Body1(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "  Aliento de Vida es una congregación creada en el corazón de Dios, para que las personas y familias en Yucatán, México y el mundo de habla hispana, tengan un lugar donde puedan adorar a Jesucristo con libertad, recibir una palabra que transforme su vida y celebrar con nosotros la presencia de Dios. \n\n   Nos sentimos honrados con tu visita, la familia de Aliento te da la bienvenida.",
                color = MaterialTheme.colors.onBackground,
            )

            Spacer(modifier = Modifier.height(16.dp))
            com.alientodevida.alientoapp.designsystem.components.H5(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "Nosotros",
                color = MaterialTheme.colors.onBackground,
            )

            Spacer(modifier = Modifier.height(16.dp))
            com.alientodevida.alientoapp.designsystem.components.Subtitle1(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "Misión",
                color = MaterialTheme.colors.onBackground,
            )

            Spacer(modifier = Modifier.height(16.dp))
            com.alientodevida.alientoapp.designsystem.components.Body1(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "• Ganar almas para Jesús. \n• Formar líderes.\n• Provocar un avivamiento.",
                color = MaterialTheme.colors.onBackground,
            )

            Spacer(modifier = Modifier.height(16.dp))
            com.alientodevida.alientoapp.designsystem.components.Subtitle1(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "Visión",
                color = MaterialTheme.colors.onBackground,
            )

            Spacer(modifier = Modifier.height(16.dp))
            com.alientodevida.alientoapp.designsystem.components.Body1(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "  Ser una iglesia con rostro a la sociedad, que ayude a la comunidad y bendiga a la gente, buscando una transformación día a día a través de principios bíblicos (la palabra de Dios) y el poder del Espíritu Santo.",
                color = MaterialTheme.colors.onBackground,
            )

            Spacer(modifier = Modifier.height(48.dp))
            com.alientodevida.alientoapp.designsystem.components.Subtitle1(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                textAlign = TextAlign.Center,
                text = "Mira nuestra última reunión",
                color = MaterialTheme.colors.onBackground,
            )

            Spacer(modifier = Modifier.height(8.dp))
            com.alientodevida.alientoapp.designsystem.components.ImageWithShimmering(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .aspectRatio(1.77f)
                    .padding(16.dp)
                    .clickable { latestVideo?.id?.let(goToVideo) },
                url = latestVideo?.thumbnailsUrl,
                description = "Aliento de Vida",
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}