package com.alientodevida.alientoapp.domain.home

data class HomeImages(
    val sermonsImage: String? = null,
    val churchImage: String? = null,
    val campusImage: String? = null,
    val galleriesImage: String? = null,
    val donationsImage: String? = null,
    val prayerImage: String? = null,
    val ebookImage: String? = null,
) {
    companion object {
        const val SERMONS_IMAGE = "predicas"
        const val CHURCH_IMAGE = "aliento_de_vida"
        const val CAMPUS_IMAGE = "manos_extendidas"
        const val GALLERY_IMAGE = "cursos"

        const val DONATIONS_IMAGE = "donaciones"
        const val PRAYER_IMAGE = "oraciones"
        const val EBOOK_IMAGE = "ebook"
    }
}
