package com.alientodevida.alientoapp.gallery.data

import com.alientodevida.alientoapp.domain.common.Gallery
import retrofit2.http.*

interface GalleryAdminApi {

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/v1/gallery", hasBody = true)
    suspend fun deleteGallery(
        @Field("id") id: Int,
    )

    @PUT("/v1/gallery")
    @FormUrlEncoded
    suspend fun editGallery(
        @Field("id") id: Int,
        @Field("name") name: String,
        @Field("cover_picture") coverPicture: String,
        @Field("images") images: String,
    ): Gallery

    @POST("/v1/gallery")
    @FormUrlEncoded
    suspend fun createGallery(
        @Field("name") name: String,
        @Field("cover_picture") coverPicture: String,
        @Field("images") images: String,
    ): Gallery
}
