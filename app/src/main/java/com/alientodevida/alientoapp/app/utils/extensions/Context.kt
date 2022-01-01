package com.alientodevida.alientoapp.app.utils.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.alientodevida.alientoapp.app.utils.Utils

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

fun Context.sendEmail(to: String, subject: String, message: String) {
  val emailIntent = Intent(Intent.ACTION_SEND)
  
  emailIntent.data = Uri.parse("mailto:")
  emailIntent.type = "text/plain"
  emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
  emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
  emailIntent.putExtra(Intent.EXTRA_TEXT, message)
  
  try {
    startActivity(Intent.createChooser(emailIntent, "Send mail..."))
  } catch (ex: ActivityNotFoundException) {
    Utils.showDialog(
      this,
      "Lo sentimos",
      "Ha habido un error, por favor intente más tarde"
    )
  }
}