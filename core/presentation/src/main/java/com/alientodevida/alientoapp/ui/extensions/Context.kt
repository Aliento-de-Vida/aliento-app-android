package com.alientodevida.alientoapp.ui.extensions

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.alientodevida.alientoapp.ui.R
import com.google.android.youtube.player.YouTubeStandalonePlayer

fun Context.getActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

fun Context.goToYoutubeVideo(
    videoId: String,
    youtubeKey: String,
) {
    val intent = YouTubeStandalonePlayer.createVideoIntent(
        getActivity(),
        youtubeKey,
        videoId,
    )
    startActivity(intent)
}

fun Context.goToUrl(webPageUrl: String) {
    val openBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webPageUrl))
    startActivity(openBrowserIntent)
}

fun Context.showDialog(title: String, message: String) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.ok) { _, _ -> }
    builder.create().show()
}

fun Context.copyToClipboard(
    name: String,
    value: String
) {
    val myClipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val myClip = ClipData.newPlainText("clipboard", value)
    myClipboard.setPrimaryClip(myClip)
    Toast.makeText(this, "$name copiado", Toast.LENGTH_SHORT).show()
}

fun Context.openInstagramPage(instagramUrl: String) {
    val uri = Uri.parse(instagramUrl)
    val likeIng = Intent(Intent.ACTION_VIEW, uri)

    likeIng.setPackage("com.instagram.android")

    try {
        startActivity(likeIng)
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(instagramUrl)
            )
        )
    }
}

fun Context.openYoutubeChannel(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}

fun Context.openFacebookPage(pageId: String, pageUrl: String) {
    val facebookIntent = Intent(Intent.ACTION_VIEW)
    val facebookUrl: String = getFacebookPageURL(pageId, pageUrl)
    facebookIntent.data = Uri.parse(facebookUrl)
    startActivity(facebookIntent)
}

private fun Context.getFacebookPageURL(pageId: String, pageUrl: String): String {
    val packageManager = packageManager
    return try {
        val versionCode =
            packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
        if (versionCode >= 3002850) { //newer versions of fb app
            "fb://page/$pageId"
        } else { //older versions of fb app
            "fb://page/$pageId"
        }
    } catch (e: PackageManager.NameNotFoundException) {
        pageUrl
    }
}

fun Context.openTwitterPage(twitterUserId: String, twitterUrl: String) {
    var intent: Intent
    try {
        packageManager.getPackageInfo("com.twitter.android", 0)
        intent = Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=$twitterUserId"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    } catch (e: Exception) {
        intent = Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl))
    }
    startActivity(intent)
}

fun Context.openSpotifyArtistPage(artistId: String) {
    openSpotifyWith(Uri.parse("spotify:artist:$artistId"))
}

fun Context.openSpotifyWith(uri: Uri) {
    if (appInstalledOrNot("com.spotify.music")) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        intent.putExtra(
            Intent.EXTRA_REFERRER,
            Uri.parse("android-app://${packageName}")
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    } else {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.spotify.music")
                )
            )
        } catch (ex: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.spotify.music")
                )
            )
        }
    }
}

private fun Context.appInstalledOrNot(uri: String): Boolean {
    val pm = packageManager
    try {
        pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
        return true
    } catch (e: PackageManager.NameNotFoundException) {
    }
    return false
}