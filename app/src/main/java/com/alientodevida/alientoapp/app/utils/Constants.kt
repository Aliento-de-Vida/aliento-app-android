package com.alientodevida.alientoapp.app.utils

object Constants {

    const val BASE_URL = "https://todoserver-peter.herokuapp.com"
    const val BASE_IMAGES_URL = "$BASE_URL/v1/files"

    const val SERMONS_IMAGE = "$BASE_IMAGES_URL/predicas.jpeg"

    const val CHURCH_IMAGE = "$BASE_IMAGES_URL/aliento_de_vida.jpeg"
    const val SOCIAL_WORK_IMAGE = "$BASE_IMAGES_URL/manos_extendidas.jpg"
    const val COURSES_IMAGE = "$BASE_IMAGES_URL/cursos.png"

    const val DONATIONS_IMAGE = "$BASE_IMAGES_URL/donaciones.png"
    const val PRAYER_IMAGE = "$BASE_IMAGES_URL/oraciones.png"
    const val EBOOK_IMAGE = "$BASE_IMAGES_URL/ebook.png"

    // Web page
    val webPageUrl = "https://alientodevida.mx/inicio"
    val ebookDownloadUrl =
        "https://drive.google.com/file/d/1GmhbCtpol8xT2JpmfexnhniKi3UW5t0z/view?usp=sharing"
    //val ebookDownloadUrl = "https://alientodevida.mx/uploads/ebooks/ebook-adv.pdf"

    // Twitter
    val TWITTER_USER_ID = "twitter://user?user_id=621332668"
    val TWITTER_URL = "https://twitter.com/AlientoMX"
    //val TWITTER_USER_ID = "twitter://user?user_id=489975902"
    //val TWITTER_URL = "https://twitter.com/brahamPerez"

    // Instagram
    val INSTAGRAM_URL = "https://www.instagram.com/alientomerida"
    //val INSTAGRAM_URL = "https://www.instagram.com/abrahampl4"

    // Facebook
    val FACEBOOK_PAGE_ID = "323027047774785"
    val FACEBOOK_URL = "https://www.facebook.com/AlientoDeVidaMerida/"

    const val SPOTIFY_ARTIST_ID = "4VYxusCiKsWxcfUveymGU5"
    const val PODCAST_ID = "6ib0Zcjx4PwHcn9S15EuAY"

    // Youtube

    const val US_VIDEO = "uBdoDNUF2_I"
    const val YOUTUBE_PREDICAS_PLAYLIST_CODE = "PLOPR9NDgPuLM8fP0Q_xvhaJ71IErDy21Y"
    const val YOUTUBE_CHANNEL_ID = "UCA5TwiI0Si1-zkgCL6gFk-A"
    const val YOUTUBE_CHANNEL_URL = "https://www.youtube.com/c/AlientoDeVidaTV"

    const val PRAYER_EMAIL = "infolineadebatalla@gmail.com"


    const val html =
        """
                <html lang="en" style="-ms-overflow-y: auto !important;">
                <head>
                    <meta http-equiv="x-ua-compatible" content="IE=Edge,chrome=IE8">
                    <meta http-equiv="content-type" content="text/html; charset=utf-8">
                    <meta name="viewport" content="width=device-width">
                </head>
                <body>
                    <iframe width="100%" src="https://www.youtube.com/embed/sze1a93hubs" frameborder="0"
                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                        allowfullscreen></iframe>
                </body>
                </html>
            """
}
