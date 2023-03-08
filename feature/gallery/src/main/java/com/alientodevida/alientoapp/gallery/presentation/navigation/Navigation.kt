package com.alientodevida.alientoapp.gallery.presentation.navigation

import android.os.Bundle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alientodevida.alientoapp.domain.common.Gallery
import com.alientodevida.alientoapp.gallery.presentation.editcreate.EditCreateGallery
import com.alientodevida.alientoapp.gallery.presentation.list.Galleries
import com.alientodevida.alientoapp.ui.extensions.getParcelableValue
import com.alientodevida.alientoapp.ui.navigation.GALLERY_DESTINATION
import com.alientodevida.alientoapp.ui.navigation.GenericNavigationActions
import com.alientodevida.alientoapp.ui.navigation.MainActions
import com.alientodevida.alientoapp.ui.navigation.MainDestination
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.adminGallery(
    genericActions: GenericNavigationActions,
) {
    composable(
        route = MainDestination.AdminGallery.path,
        arguments = listOf(navArgument(GALLERY_DESTINATION) { type = GalleryType() })
    ) {
        val gallery = it.arguments?.getParcelableValue(GALLERY_DESTINATION, Gallery::class.java)!!

        EditCreateGallery(
            viewModel = hiltViewModel(),
            gallery = gallery,
            onBackPressed = genericActions::back,
        )
    }
}

fun NavGraphBuilder.galleries(
    genericActions: GenericNavigationActions,
    actions: MainActions,
) {
    composable(MainDestination.Galleries.path) {
        Galleries(
            viewModel = hiltViewModel(),
            onBackPressed = genericActions::back,
            goToCreateGallery = actions::navigateToAdminGallery,
            goToEditGallery = actions::navigateToAdminGallery,
        )
    }
}

class GalleryType : NavType<Gallery>(
    isNullableAllowed = true
) {
    override fun put(bundle: Bundle, key: String, value: Gallery) {
        bundle.putParcelable(key, value)
    }

    override fun get(bundle: Bundle, key: String): Gallery {
        return bundle.getParcelableValue(key, Gallery::class.java)
    }

    override fun parseValue(value: String): Gallery {
        return Json.decodeFromString(value)
    }
}