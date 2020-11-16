package com.alientodevida.alientoapp.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayListItems(
    var items: List<Playlistitem>
)

@JsonClass(generateAdapter = true)
data class Playlistitem(
    val snippet: Snippet
)
@JsonClass(generateAdapter = true)
class Snippet(
    val publishedAt: String,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val resourceId: ResourceId
)

@JsonClass(generateAdapter = true)
data class ResourceId(
    val kind: String,
    val videoId: String
)

@JsonClass(generateAdapter = true)
data class Thumbnails(
    val default: Default,
    val medium: Medium,
    var high: High
)

@JsonClass(generateAdapter = true)
data class Default(
    val url: String,
    val width: String,
    val height: String,
)

@JsonClass(generateAdapter = true)
data class Medium(
    val url: String,
    val width: String,
    val height: String,
)

@JsonClass(generateAdapter = true)
data class High(
    val url: String,
    val width: String,
    val height: String,
)