package com.alientodevida.alientoapp.app.features.campus.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.AlwaysRefreshableSwipeRefresh
import com.alientodevida.alientoapp.app.compose.components.Body2
import com.alientodevida.alientoapp.app.compose.components.ClickableIcon
import com.alientodevida.alientoapp.app.compose.components.Gradient
import com.alientodevida.alientoapp.app.compose.components.H5
import com.alientodevida.alientoapp.app.compose.components.Icon
import com.alientodevida.alientoapp.app.compose.components.ImageWithShimmering
import com.alientodevida.alientoapp.app.compose.components.LoadingIndicator
import com.alientodevida.alientoapp.app.compose.components.ModalExpandedOnlyBottomSheetLayout
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.extensions.SnackBar
import com.alientodevida.alientoapp.app.features.campus.detail.CampusDetail
import com.alientodevida.alientoapp.app.utils.extensions.toImageUrl
import com.alientodevida.alientoapp.domain.campus.Campus
import com.alientodevida.alientoapp.domain.campus.Location
import kotlinx.coroutines.launch

@Composable
fun Campuses(
  viewModel: CampusesViewModel,
  onBackPressed: () -> Unit,
  goToEditCampus: (Campus) -> Unit,
  goToCreateCampus: () -> Unit,
) {
  LaunchedEffect(true) {
    viewModel.getCampuses()
  }
  
  val viewModelState by viewModel.viewModelState.collectAsState()
  val isAdmin by viewModel.isAdmin.collectAsState(false)
  
  CampusesWithDialog(
    uiState = viewModelState,
    isAdmin = isAdmin,
    refresh = viewModel::getCampuses,
    onMessageDismiss = viewModel::onMessageDismiss,
    deleteCampus = viewModel::deleteCampus,
    onBackPressed = onBackPressed,
    goToEditCampus = goToEditCampus,
    goToCreateCampus = goToCreateCampus,
  )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CampusesWithDialog(
  uiState: CampusesUiState,
  isAdmin: Boolean,
  refresh: () -> Unit,
  onMessageDismiss: (Long) -> Unit,
  deleteCampus: (Campus) -> Unit,
  onBackPressed: () -> Unit,
  goToEditCampus: (Campus) -> Unit,
  goToCreateCampus: () -> Unit,
) {
  val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
  val coroutineScope = rememberCoroutineScope()
  val campus = remember { mutableStateOf(Campus.empty()) }
  
  ModalExpandedOnlyBottomSheetLayout(
    sheetState = modalBottomSheetState,
    sheetBackgroundColor = MaterialTheme.colors.background,
    sheetContent = { CampusDetail(campus.value) },
    scrimColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
  ) {
    CampusesContent(
      uiState = uiState,
      isAdmin = isAdmin,
      refresh = refresh,
      onMessageDismiss = onMessageDismiss,
      deleteCampus = deleteCampus,
      goToCampus = {
        coroutineScope.launch {
          campus.value = it
          modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
        }
      },
      onBackPressed = onBackPressed,
      goToEditCampus = goToEditCampus,
      goToCreateCampus = goToCreateCampus,
    )
  }
}

@Composable
fun CampusesContent(
  uiState: CampusesUiState,
  isAdmin: Boolean,
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  refresh: () -> Unit,
  onMessageDismiss: (Long) -> Unit,
  deleteCampus: (Campus) -> Unit,
  onBackPressed: () -> Unit,
  goToCampus: (Campus) -> Unit,
  goToEditCampus: (Campus) -> Unit,
  goToCreateCampus: () -> Unit,
) {
  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },
    floatingActionButton = {
      if (isAdmin) FloatingActionButton(
        onClick = { goToCreateCampus() },
        contentColor = MaterialTheme.colors.surface,
      ) {
        Icon(
          icon = R.drawable.ic_add_24,
          contentDescription = "Create Campus",
          tint = MaterialTheme.colors.onSurface
        )
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .padding(paddingValues = paddingValues)
        .background(color = MaterialTheme.colors.background),
    ) {
      CampusesBody(
        campuses = uiState.campuses,
        deleteCampus = deleteCampus,
        loading = uiState.loading,
        refresh = refresh,
        goToCampus = goToCampus,
        goToEditCampus = goToEditCampus,
        isAdmin = isAdmin,
      )
      if (uiState.loading) LoadingIndicator()
      
      uiState.messages.firstOrNull()?.SnackBar(scaffoldState, onMessageDismiss)
    }
  }
}

// TODO can we extract a component ?
@Composable
fun TopAppBar(
  onBackPressed: () -> Unit,
) {
  val modifier = Modifier.size(width = 60.dp, height = 50.dp)
  
  androidx.compose.material.TopAppBar(
    title = {
      Image(
        painter = painterResource(id = R.drawable.logo_negro),
        colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onBackground),
        contentScale = ContentScale.Inside,
        alignment = Alignment.Center,
        modifier = Modifier
          .padding(8.dp)
          .fillMaxWidth(),
        contentDescription = null,
      )
    },
    navigationIcon = {
      ClickableIcon(
        modifier = modifier,
        icon = R.drawable.ic_back_24,
        contentDescription = "Back Button",
        tint = MaterialTheme.colors.onBackground,
        onClick = onBackPressed,
      )
    },
    actions = {
      Box(modifier = modifier, contentAlignment = Alignment.Center) { }
    },
    backgroundColor = MaterialTheme.colors.background,
  )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun CampusesBody(
  campuses: List<Campus>,
  isAdmin: Boolean,
  loading: Boolean,
  refresh: () -> Unit,
  deleteCampus: (Campus) -> Unit,
  goToCampus: (Campus) -> Unit,
  goToEditCampus: (Campus) -> Unit,
) {
  AlwaysRefreshableSwipeRefresh(
    isRefreshing = loading,
    items = campuses,
    onRefresh = refresh,
  ) {
    Column(Modifier.padding(horizontal = 8.dp)) {
      Spacer(modifier = Modifier.height(8.dp))
      H5(
        modifier = Modifier.padding(horizontal = 8.dp),
        text = "Campus",
        color = MaterialTheme.colors.onBackground,
      )
      Spacer(modifier = Modifier.height(16.dp))
      LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        items(campuses, key = { it.id }) { campus ->
          CampusItem(
            modifier = Modifier.animateItemPlacement(),
            campus = campus,
            isAdmin = isAdmin,
            height = 220.dp,
            deleteCampus = deleteCampus,
            goToCampus = goToCampus,
            goToEditCampus = goToEditCampus,
          )
        }
      }
    }
  }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun CampusItem(
  modifier: Modifier = Modifier,
  campus: Campus,
  height: Dp,
  isAdmin: Boolean,
  deleteCampus: (Campus) -> Unit,
  goToCampus: (Campus) -> Unit,
  goToEditCampus: (Campus) -> Unit,
) {
  var expanded by remember { mutableStateOf(false) }
  val hapticFeedback = LocalHapticFeedback.current
  
  Card(
    modifier
      .fillMaxWidth()
      .height(height)
      .combinedClickable(
        onClick = { goToCampus(campus) },
        onLongClick = {
          if (isAdmin) {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
            expanded = expanded.not()
          }
        }
      ),
  ) {
    Box {
      CampusItemContent(campus)
      
      DropdownMenu(
        modifier = Modifier.background(MaterialTheme.colors.surface),
        expanded = expanded,
        onDismissRequest = { expanded = false }
      ) {
        DropdownMenuItem(
          onClick = { deleteCampus(campus) }) {
          Body2(
            text = "Eliminar",
            color = MaterialTheme.colors.onSurface,
          )
        }
        DropdownMenuItem(onClick = { goToEditCampus(campus) }) {
          Body2(
            text = "Editar",
            color = MaterialTheme.colors.onSurface,
          )
        }
      }
    }
  }
}

@Composable
private fun CampusItemContent(campus: Campus) {
  Box {
    // TODO the ViewModel should do this conversion
    ImageWithShimmering(
      modifier = Modifier.align(Alignment.Center),
      contentScale = ContentScale.Crop,
      url = campus.imageUrl.toImageUrl(),
      description = campus.name
    )
    
    Column {
      Spacer(Modifier.weight(0.38f))
      Gradient(
        modifier = Modifier
          .fillMaxWidth()
          .weight(0.62f),
      ) {
        Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
          Spacer(Modifier.weight(1.0f))
          H5(
            text = campus.name,
            color = colorResource(R.color.pantone_white_c),
          )
          Body2(
            text = campus.shortDescription,
            color = colorResource(R.color.pantone_white_c),
          )
        }
      }
    }
  }
}

@Preview
@Composable
fun CampusesPreview() {
  AppTheme {
    CampusesContent(
      CampusesUiState(
        listOf(
          Campus(
            1,
            "Test Notification",
            "This is a test",
            "This is short description",
            "cursos.png",
            null,
            Location("", ""),
            emptyList(),
            "",
          ),
          Campus(
            2,
            "Test Notification",
            "This is a test",
            "This is short description",
            "cursos.png",
            null,
            Location("", ""),
            emptyList(),
            "",
          ),
        ),
        true,
        emptyList(),
      ),
      isAdmin = true,
      refresh = {},
      onMessageDismiss = {},
      onBackPressed = {},
      deleteCampus = {},
      goToCampus = {},
      goToEditCampus = {},
      goToCreateCampus = {},
    )
  }
}
