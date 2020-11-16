package com.alientodevida.alientoapp.data.repository

import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.*
import com.alientodevida.alientoapp.data.networking.RetrofitService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService
): Repository {

    override suspend fun getYoutubePlayList(youtubeKey: String, playListId: String): PlayListItems {
        return retrofitService.getYoutubePlaylist(
            "https://www.googleapis.com/youtube/v3/playlistItems?key=${youtubeKey}" +
                    "&playlistId=${playListId}&part=snippet&order=date&maxResults=50"
        )
    }

    override suspend fun getImageUrl(authorization: String, folderName: String): ImageUrlResponse {
        return retrofitService.getImageUrl(
            "https://api.cloudinary.com/api/v1_1/dpeeqsw78/resources/search/?expression=folder=${folderName}",
            authorization
        )
    }

    override suspend fun getToken(): Token {
        // TODO inject as dependencies and put in constants file
        return retrofitService.getToken(
            "https://accounts.spotify.com/api/token/",
            "Basic ZTBlNDMyMWYxNTI4NGU5YzkwNzRmMDFjNjAwOTdkOGY6YTQyNjk2MzViYzMyNDkxNjlkNjRhZWYzZTgwNGM1NGM=",
            "client_credentials"
        )
    }

    override suspend fun getPlaylist(authorization: String, playlistId: String): PlayList {
        return retrofitService.getPlaylist(authorization, playlistId)
    }

    override suspend fun getPodcast(authorization: String, podcastId: String): Podcast {
        return retrofitService.getPodcast(authorization, podcastId)
    }
}