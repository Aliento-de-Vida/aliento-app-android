package com.alientodevida.alientoapp.sermons.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alientodevida.alientoapp.sermons.R
import com.alientodevida.alientoapp.sermons.presentation.navigation.BottomNavItem
import com.alientodevida.alientoapp.sermons.presentation.navigation.NavigationGraph

@Composable
fun Sermons(
    onBackPressed: () -> Unit,
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopAppBar(onBackPressed = { onBackPressed() }) },
        bottomBar = { BottomNavigation(navController = navController) },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            NavigationGraph(navController = navController)
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
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Video,
        BottomNavItem.Audio,
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = Color.Black,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            val isSelected = currentRoute == item.screen_route
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = {
                    com.alientodevida.alientoapp.designsystem.components.Caption(
                        text = item.title,
                        color = if (isSelected) {
                            MaterialTheme.colors.onSurface
                        } else {
                            MaterialTheme.colors.onSurface.copy(
                                0.4f,
                            )
                        },
                    )
                },
                selectedContentColor = MaterialTheme.colors.onSurface,
                unselectedContentColor = MaterialTheme.colors.onSurface.copy(0.4f),
                alwaysShowLabel = true,
                selected = isSelected,
                onClick = {
                    navController.navigate(item.screen_route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}
