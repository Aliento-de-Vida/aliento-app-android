package com.alientodevida.alientoapp.utils

import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import com.alientodevida.alientoapp.AppController
import com.google.android.youtube.player.YouTubeStandalonePlayer


class Utils {
    companion object {

        fun openFacebookPage(context: Context) {
            val facebookIntent = Intent(Intent.ACTION_VIEW)
            val facebookUrl: String = getFacebookPageURL(context)
            facebookIntent.data = Uri.parse(facebookUrl)
            context.startActivity(facebookIntent)
        }

        private fun getFacebookPageURL(context: Context): String {
            val packageManager = context.packageManager
            return try {
                val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
                if (versionCode >= 3002850) { //newer versions of fb app
                    "fb://page/${Constants.FACEBOOK_PAGE_ID}"
                } else { //older versions of fb app
                    "fb://page/${Constants.FACEBOOK_PAGE_ID}"
                }
            } catch (e: PackageManager.NameNotFoundException) {
                Constants.FACEBOOK_URL
            }
        }

        fun openYoutubeChannel(context: Context, url: String) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }

        fun openInstagramPage(context: Context) {
            val uri = Uri.parse(Constants.INSTAGRAM_URL)
            val likeIng = Intent(Intent.ACTION_VIEW, uri)

            likeIng.setPackage("com.instagram.android")

            try {
                context.startActivity(likeIng)
            } catch (e: ActivityNotFoundException) {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.INSTAGRAM_URL)))
            }
        }

        fun openTwitterPage(context: Context) {
            var intent: Intent
            try {
                context.packageManager.getPackageInfo("com.twitter.android", 0)
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TWITTER_USER_ID))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            } catch (e: Exception) {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TWITTER_URL))
            }
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

        fun copyToClipboard(context: Context? = AppController.context, name: String, value: String) {
            context?.let { it ->
                val myClipboard = it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val myClip = ClipData.newPlainText("clipboard", value)
                myClipboard.setPrimaryClip(myClip)

                Toast.makeText(it, "$name copiado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}