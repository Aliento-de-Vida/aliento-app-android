package com.alientodevida.alientoapp.app.features.prayer

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.compose.components.*
import com.alientodevida.alientoapp.app.extensions.SnackBar
import com.alientodevida.alientoapp.app.utils.Utils

@Composable
fun Prayer(
  viewModel: PrayerViewModel,
  onBackPressed: () -> Unit,
) {
  
  val viewModelState by viewModel.viewModelState.collectAsState()
  
  val context = LocalContext.current
  
  NotificationsContent(
    uiState = viewModelState,
    onMessageDismiss = viewModel::onMessageDismiss,
    onNameChanged = viewModel::onNameChanged,
    onEmailChanged = viewModel::onEmailChanged,
    onWhatsappChanged = viewModel::onWhatsappChanged,
    onMessageChanged = viewModel::onMessageChanged,
    onTopicChanged = viewModel::onTopicChanged,
    sendPrayerRequest = { sendPrayerRequest(context, viewModelState)},
    onBackPressed = onBackPressed,
  )
}

@Composable
fun NotificationsContent(
  uiState: PrayerUiState,
  scaffoldState: ScaffoldState = rememberScaffoldState(),
  onMessageDismiss: (Long) -> Unit,
  onNameChanged: (String) -> Unit,
  onEmailChanged: (String) -> Unit,
  onWhatsappChanged: (String) -> Unit,
  onMessageChanged: (String) -> Unit,
  onTopicChanged: (Int) -> Unit,
  sendPrayerRequest: () -> Unit,
  onBackPressed: () -> Unit,
) {
  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },
    floatingActionButton = {
      if (uiState.isValidForm) FloatingActionButton(
        onClick = { sendPrayerRequest() },
        contentColor = MaterialTheme.colors.surface,
      ) {
        Icon(
          icon = R.drawable.ic_send_24,
          contentDescription = "Send Prayer Request",
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
      NotificationsBody(
        state = uiState,
        onNameChanged = onNameChanged,
        onEmailChanged = onEmailChanged,
        onWhatsappChanged = onWhatsappChanged,
        onMessageChanged = onMessageChanged,
        onTopicChanged = onTopicChanged,
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
fun NotificationsBody(
  state: PrayerUiState,
  onNameChanged: (String) -> Unit,
  onEmailChanged: (String) -> Unit,
  onWhatsappChanged: (String) -> Unit,
  onMessageChanged: (String) -> Unit,
  onTopicChanged: (Int) -> Unit,
) {
  Column(
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  ) {
    Spacer(modifier = Modifier.height(16.dp))
    H5(
      modifier = Modifier.padding(horizontal = 8.dp).align(Alignment.CenterHorizontally),
      text = "Queremos orar por ti",
      color = MaterialTheme.colors.onBackground,
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    Body1(
      modifier = Modifier.padding(horizontal = 8.dp).align(Alignment.CenterHorizontally),
      text = "Por favor escríbenos tu petición de oración",
      color = MaterialTheme.colors.onBackground,
    )
    
    Spacer(modifier = Modifier.height(32.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = state.name ?: "",
      onChanged = onNameChanged,
      label = "Nombre",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = state.email ?: "",
      onChanged = onEmailChanged,
      label = "Email",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = state.whatsapp ?: "",
      onChanged = onWhatsappChanged,
      label = "Whatsapp",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    InputField(
      modifier = Modifier.fillMaxWidth(),
      value = state.message ?: "",
      onChanged = onMessageChanged,
      label = "Mensaje",
      labelColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
    )
  
    Spacer(modifier = Modifier.height(16.dp))
    Body1(
      modifier = Modifier.padding(horizontal = 8.dp),
      text = "Asunto",
      color = MaterialTheme.colors.onBackground,
    )
  
    Spacer(modifier = Modifier.height(8.dp))
    TopicDropDown(topic = state.topic, topics = state.topics, onTopicChanged = onTopicChanged)
  }
}

@Composable
fun TopicDropDown(
  topic: Int,
  topics: List<String>,
  onTopicChanged: (Int) -> Unit,
) {
  var expanded by remember { mutableStateOf(false) }
  
  Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
    Row(
      Modifier
        .clickable {
          expanded = !expanded
        }
        .padding(8.dp),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Body1(
        modifier = Modifier.padding(end = 8.dp),
        color = MaterialTheme.colors.onBackground,
        text = topics[topic],
      )
      Icon(
        icon = Icons.Filled.ArrowDropDown,
        contentDescription = "",
        tint = MaterialTheme.colors.onSurface
      )
      
      DropdownMenu(expanded = expanded, onDismissRequest = {
        expanded = false
      }) {
        topics.forEach { topic ->
          DropdownMenuItem(onClick = {
            val index = topics.indexOf(topic)
            expanded = false
            onTopicChanged(if (index > 0) index else 0)
          }) {
            Body1(
              text = topic,
              color = MaterialTheme.colors.onBackground,
            )
          }
        }
      }
    }
  }
}

fun sendMail(context: Context, to: String, subject: String, message: String) {
  val emailIntent = Intent(Intent.ACTION_SEND)
  
  emailIntent.data = Uri.parse("mailto:")
  emailIntent.type = "text/plain"
  emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
  emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
  emailIntent.putExtra(Intent.EXTRA_TEXT, message)
  
  try {
    startActivity(context, Intent.createChooser(emailIntent, "Send mail..."), null)
  } catch (ex: ActivityNotFoundException) {
    Utils.showDialog(
      context,
      "Lo sentimos",
      "Ha habido un error, por favor intente más tarde"
    )
  }
}

fun getMailMessage(name: String, email: String, whatsapp: String, message: String) =
  """
    Datos de contacto:
    
    nombre: $name
    email: $email
    whatsapp: $whatsapp
    
    mensaje:
    
    $message
	""".trimIndent()

fun sendPrayerRequest(context: Context, viewModelState: PrayerUiState) {
  viewModelState.home?.let { home ->
    sendMail(
      context = context,
      to = home.prayerEmail,
      subject = viewModelState.email!!,
      message = getMailMessage(
        viewModelState.name!!,
        viewModelState.email,
        viewModelState.whatsapp!!,
        viewModelState.message!!,
      )
    )
  }
}
