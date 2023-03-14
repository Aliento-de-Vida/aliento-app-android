package com.alientodevida.alientoapp.home.presentation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.domain.common.Notification
import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.home.HomeUiState
import com.alientodevida.alientoapp.home.HomeViewModel
import com.alientodevida.alientoapp.home.R
import com.alientodevida.alientoapp.notifications.presentation.detail.NotificationDetail
import com.alientodevida.alientoapp.notifications.presentation.list.NotificationItem
import com.alientodevida.alientoapp.ui.extensions.*
import kotlinx.coroutines.launch

@Composable
fun Home(
    viewModel: HomeViewModel,
    goToHomeAdmin: (Home) -> Unit,
    goToNotifications: () -> Unit,
    goToSettings: () -> Unit,
    goToSermons: () -> Unit,
    goToChurch: () -> Unit,
    goToCampus: () -> Unit,
    goToGallery: () -> Unit,
    goToPrayer: () -> Unit,
    goToDonations: () -> Unit,
    goToAdminLogin: () -> Unit,
) {

    val viewModelState by viewModel.viewModelState.collectAsState()
    val isAdmin by viewModel.isAdmin.collectAsState(false)
    val context = LocalContext.current

    HomeWithDialog(
        uiState = viewModelState,
        isAdmin = isAdmin,
        refresh = viewModel::getHome,
        onMessageDismiss = viewModel::onMessageDismiss,
        goToEditHome = { viewModel.viewModelState.value.home?.let(goToHomeAdmin) },
        goToNotifications = goToNotifications,
        goToSettings = goToSettings,
        goToSermons = goToSermons,
        goToSermon = { }, // TODO
        goToChurch = goToChurch,
        goToCampus = goToCampus,
        goToGallery = goToGallery,
        goToPrayer = goToPrayer,
        goToDonations = goToDonations,
        goToEbook = { goToEbook(viewModel.viewModelState.value, context) },
        goToInstagram = { goToInstagram(viewModel.viewModelState.value, context) },
        goToYoutube = { goToYoutube(viewModel.viewModelState.value, context) },
        goToFacebook = { goToFacebook(viewModel.viewModelState.value, context) },
        goToTwitter = { goToTwitter(viewModel.viewModelState.value, context) },
        goToSpotify = { goToSpotify(viewModel.viewModelState.value, context) },
        goToAdminLogin = goToAdminLogin,
        adminLogout = viewModel::adminLogout,
    )
}

private fun goToInstagram(state: HomeUiState, context: Context) {
    state.home?.socialMedia?.instagramUrl?.let {
        context.openInstagramPage(it)
    }
}

private fun goToYoutube(state: HomeUiState, context: Context) {
    state.home?.socialMedia?.youtubeChannelUrl?.let {
        context.openYoutubeChannel(it)
    }
}

private fun goToFacebook(state: HomeUiState, context: Context) {
    state.home?.socialMedia?.let {
        context.openFacebookPage(it.facebookPageId, it.facebookPageUrl)
    }
}

private fun goToTwitter(state: HomeUiState, context: Context) {
    state.home?.socialMedia?.let {
        context.openTwitterPage(it.twitterUserId, it.twitterUrl)
    }
}

private fun goToSpotify(state: HomeUiState, context: Context) {
    state.home?.socialMedia?.spotifyArtistId?.let {
        context.openSpotifyArtistPage(it)
    }
}

private fun goToEbook(state: HomeUiState, context: Context) {
    state.home?.ebook?.let { context.goToUrl(it) }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeWithDialog(
    uiState: HomeUiState,
    isAdmin: Boolean,
    refresh: () -> Unit,
    onMessageDismiss: (Long) -> Unit,
    goToEditHome: () -> Unit,
    goToNotifications: () -> Unit,
    goToSettings: () -> Unit,
    goToSermons: () -> Unit,
    goToSermon: (String) -> Unit,
    goToChurch: () -> Unit,
    goToCampus: () -> Unit,
    goToGallery: () -> Unit,
    goToPrayer: () -> Unit,
    goToDonations: () -> Unit,
    goToEbook: () -> Unit,
    goToInstagram: () -> Unit,
    goToYoutube: () -> Unit,
    goToFacebook: () -> Unit,
    goToTwitter: () -> Unit,
    goToSpotify: () -> Unit,
    goToAdminLogin: () -> Unit,
    adminLogout: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val notification = remember { mutableStateOf(Notification.empty()) }

    com.alientodevida.alientoapp.designsystem.components.ModalExpandedOnlyBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetContent = { NotificationDetail(notification.value) },
        scrimColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
    ) {
        HomeContent(
            uiState = uiState,
            isAdmin = isAdmin,
            refresh = refresh,
            onMessageDismiss = onMessageDismiss,
            goToEditHome = goToEditHome,
            goToNotifications = goToNotifications,
            goToNotificationDetail = {
                coroutineScope.launch {
                    notification.value = it
                    modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            },
            goToSettings = goToSettings,
            goToSermons = goToSermons,
            goToSermon = goToSermon,
            goToChurch = goToChurch,
            goToCampus = goToCampus,
            goToGallery = goToGallery,
            goToPrayer = goToPrayer,
            goToDonations = goToDonations,
            goToEbook = goToEbook,
            goToInstagram = goToInstagram,
            goToYoutube = goToYoutube,
            goToFacebook = goToFacebook,
            goToTwitter = goToTwitter,
            goToSpotify = goToSpotify,
            goToAdminLogin = goToAdminLogin,
            adminLogout = adminLogout,
        )
    }
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    isAdmin: Boolean,
    refresh: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onMessageDismiss: (Long) -> Unit,
    goToEditHome: () -> Unit,
    goToNotifications: () -> Unit,
    goToNotificationDetail: (Notification) -> Unit,
    goToSettings: () -> Unit,
    goToSermons: () -> Unit,
    goToSermon: (String) -> Unit,
    goToChurch: () -> Unit,
    goToCampus: () -> Unit,
    goToGallery: () -> Unit,
    goToPrayer: () -> Unit,
    goToDonations: () -> Unit,
    goToEbook: () -> Unit,
    goToInstagram: () -> Unit,
    goToYoutube: () -> Unit,
    goToFacebook: () -> Unit,
    goToTwitter: () -> Unit,
    goToSpotify: () -> Unit,
    goToAdminLogin: () -> Unit,
    adminLogout: () -> Unit
) {
    Scaffold(scaffoldState = scaffoldState, topBar = {
        TopAppBar(
            goToNotifications = goToNotifications,
            goToSettings = goToSettings,
        )
    }, floatingActionButton = {
        if (isAdmin) FloatingActionButton(
            onClick = { goToEditHome() },
            contentColor = MaterialTheme.colors.surface,
        ) {
            com.alientodevida.alientoapp.designsystem.components.Icon(
                icon = R.drawable.ic_edit_24,
                contentDescription = "Edit Home",
                tint = MaterialTheme.colors.onSurface
            )
        }
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .background(color = MaterialTheme.colors.background),
        ) {
            HomeBody(
                uiState = uiState,
                isAdmin = isAdmin,
                refresh = refresh,
                goToNotificationDetail = goToNotificationDetail,
                goToSermons = goToSermons,
                goToSermon = goToSermon,
                goToChurch = goToChurch,
                goToCampus = goToCampus,
                goToGallery = goToGallery,
                goToPrayer = goToPrayer,
                goToDonations = goToDonations,
                goToEbook = goToEbook,
                goToInstagram = goToInstagram,
                goToYoutube = goToYoutube,
                goToFacebook = goToFacebook,
                goToTwitter = goToTwitter,
                goToSpotify = goToSpotify,
                goToAdminLogin = goToAdminLogin,
                adminLogout = adminLogout,
            )
            if (uiState.loading) com.alientodevida.alientoapp.designsystem.components.LoadingIndicator()

            uiState.messages.firstOrNull()?.SnackBar(scaffoldState, onMessageDismiss)
        }
    }
}

// TODO can we extract a component ?
@Composable
fun TopAppBar(
    goToNotifications: () -> Unit,
    goToSettings: () -> Unit,
) {
    val modifier = Modifier.size(width = 60.dp, height = 50.dp)

    TopAppBar(
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
                icon = R.drawable.ic_baseline_settings_24,
                contentDescription = "Settings Button",
                tint = MaterialTheme.colors.onBackground,
                onClick = goToSettings,
            )
        },
        actions = {
            com.alientodevida.alientoapp.designsystem.components.ClickableIcon(
                modifier = modifier,
                icon = R.drawable.ic_notifications_black_24dp,
                contentDescription = "Notifications Button",
                tint = MaterialTheme.colors.onBackground,
                onClick = goToNotifications,
            )
        },
        backgroundColor = MaterialTheme.colors.background,
    )
}

@Composable
fun HomeBody(
    uiState: HomeUiState,
    isAdmin: Boolean,
    refresh: () -> Unit,
    goToNotificationDetail: (Notification) -> Unit,
    goToSermons: () -> Unit,
    goToSermon: (String) -> Unit,
    goToChurch: () -> Unit,
    goToCampus: () -> Unit,
    goToGallery: () -> Unit,
    goToPrayer: () -> Unit,
    goToDonations: () -> Unit,
    goToEbook: () -> Unit,
    goToInstagram: () -> Unit,
    goToYoutube: () -> Unit,
    goToFacebook: () -> Unit,
    goToTwitter: () -> Unit,
    goToSpotify: () -> Unit,
    goToAdminLogin: () -> Unit,
    adminLogout: () -> Unit,
) {
    val scrollState = rememberScrollState()

    com.alientodevida.alientoapp.designsystem.components.AlwaysRefreshableSwipeRefresh(
        isRefreshing = uiState.loading,
        items = uiState.categoriesItems,
        onRefresh = refresh,
    ) {
        Column(Modifier.verticalScroll(scrollState)) {
            SermonsPager(
                items = uiState.sermonItems.take(3),
                goToSermons = goToSermons,
                goToSermon = { it.youtubeItem?.youtubeId?.let { goToSermon(it) } },
            )

            Categories(
                items = uiState.categoriesItems,
                title = "Categorías",
                goToChurch = goToChurch,
                goToCampus = goToCampus,
                goToGallery = goToGallery,
                goToDonations = goToDonations,
                goToPrayer = goToPrayer,
                goToEbook = goToEbook,
            )

            Categories(
                items = uiState.quickAccessItems,
                title = "Acceso Rápido",
                goToChurch = goToChurch,
                goToCampus = goToCampus,
                goToGallery = goToGallery,
                goToDonations = goToDonations,
                goToPrayer = goToPrayer,
                goToEbook = goToEbook,
            )

            Notifications(
                uiState.notifications,
                goToNotificationDetail,
            )

            SocialMedia(
                isAdmin = isAdmin,
                goToInstagram = goToInstagram,
                goToYoutube = goToYoutube,
                goToFacebook = goToFacebook,
                goToTwitter = goToTwitter,
                goToSpotify = goToSpotify,
                goToAdminLogin = goToAdminLogin,
                adminLogout = adminLogout,
            )

            Spacer(modifier = Modifier.height(if (isAdmin) 80.dp else 16.dp))
        }
    }
}

@Composable
fun Notifications(
    notifications: List<Notification>,
    goToNotificationDetail: (Notification) -> Unit,
) {
    if (notifications.isNotEmpty()) {
        Column(Modifier.padding(horizontal = 8.dp)) {
            Spacer(modifier = Modifier.height(8.dp))
            com.alientodevida.alientoapp.designsystem.components.H5(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "Notificaciones",
                color = MaterialTheme.colors.onBackground,
            )
            Spacer(modifier = Modifier.height(16.dp))

            notifications.forEach {
                NotificationItem(
                    notification = it,
                    isAdmin = false,
                    height = 220.dp,
                    deleteNotification = {},
                    goToNotificationDetail = goToNotificationDetail,
                    goToNotificationsAdmin = {},
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    com.alientodevida.alientoapp.designsystem.theme.AppTheme {
        HomeContent(
            HomeUiState(
                home = null,
                categoriesItems = emptyList(),
                sermonItems = emptyList(),
                notifications = emptyList(),
                loading = true,
            ),
            refresh = {},
            isAdmin = true,
            onMessageDismiss = {},
            goToEditHome = {},
            goToNotifications = {},
            goToNotificationDetail = {},
            goToSettings = {},
            goToSermons = {},
            goToSermon = {},
            goToChurch = {},
            goToCampus = {},
            goToGallery = {},
            goToPrayer = {},
            goToDonations = {},
            goToEbook = {},
            goToInstagram = {},
            goToYoutube = {},
            goToFacebook = {},
            goToTwitter = {},
            goToSpotify = {},
            goToAdminLogin = {},
            adminLogout = {},
        )
    }
}