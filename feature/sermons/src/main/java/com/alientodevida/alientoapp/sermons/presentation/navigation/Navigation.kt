package com.alientodevida.alientoapp.sermons.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alientodevida.alientoapp.sermons.R
import com.alientodevida.alientoapp.sermons.presentation.Sermons
import com.alientodevida.alientoapp.sermons.presentation.audio.AudioSermons
import com.alientodevida.alientoapp.sermons.presentation.video.VideoSermons
import com.alientodevida.alientoapp.ui.navigation.GenericNavigationActions
import com.alientodevida.alientoapp.ui.navigation.MainDestination

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){
  object Video: BottomNavItem("Video",R.drawable.ic_baseline_tv_24,"sermons_video")
  object Audio : BottomNavItem("Audio", R.drawable.ic_baseline_music_note_24,"sermons_audio")
}

@Composable
fun NavigationGraph(
  navController: NavHostController,
) {
  NavHost(navController, startDestination = BottomNavItem.Video.screen_route) {
    composable(BottomNavItem.Video.screen_route) {
      VideoSermons(viewModel = hiltViewModel())
    }
    composable(BottomNavItem.Audio.screen_route) {
      AudioSermons(viewModel = hiltViewModel())
    }
  }
}

@Suppress("UNUSED_PARAMETER")
fun NavGraphBuilder.sermons( // TODO use same navigation design and move everything here
  navController: NavHostController,
  genericActions: GenericNavigationActions,
) {
  composable(MainDestination.Sermons.path) {
    Sermons(
      onBackPressed = genericActions::back
    )
  }
}