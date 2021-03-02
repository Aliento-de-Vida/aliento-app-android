package com.alientodevida.alientoapp.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.google.android.youtube.player.YouTubeStandalonePlayer


class Utils {
    companion object {
        fun openYoutubeChannel(context: Context, url: String) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }

        fun openSpotifyArtistPage(context: Context, artistId: String) {
            openSpotifyWith(context, Uri.parse("spotify:artist:$artistId"))
        }

        fun openSpotifyWith(context: Context, uri: Uri) {
            if (appInstalledOrNot(context, "com.spotify.music")) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = uri
                intent.putExtra(Intent.EXTRA_REFERRER, Uri.parse("android-app://${context.packageName}"))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)

            } else {
                try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.spotify.music")))
                } catch (ex: ActivityNotFoundException) {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.spotify.music")))
                }
            }
        }

        private fun appInstalledOrNot(context: Context, uri: String): Boolean {
            val pm = context.packageManager
            try {
                pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
                return true
            } catch (e: PackageManager.NameNotFoundException) {
            }
            return false
        }

        fun goToUrl(context: Context, webPageUrl: String) {
            val openBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webPageUrl))
            context.startActivity(openBrowserIntent)
        }

        fun handleOnClick(activity: Activity, videoId: String) {
            val intent: Intent = YouTubeStandalonePlayer.createVideoIntent(activity, Constants.YOUTUBE_DEVELOPER_KEY, videoId)
            activity.startActivity(intent)
        }
    }
}