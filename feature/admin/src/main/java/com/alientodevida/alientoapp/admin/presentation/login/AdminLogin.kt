package com.alientodevida.alientoapp.admin.presentation.login

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.admin.R
import com.alientodevida.alientoapp.app.extensions.Dialog
import com.alientodevida.alientoapp.ui.state.ViewModelResult

@Composable
fun AdminLogin(
    viewModel: AdminLoginViewModel,
    onBackPressed: () -> Unit,
) {
  val loginResult by viewModel.loginResult.collectAsState()
  
  (loginResult as? ViewModelResult.Error)?.Dialog()
  
  (loginResult as? ViewModelResult.Success)?.let {
    val context = LocalContext.current
    LaunchedEffect(true) {
      Toast.makeText(context, "Signed In!", Toast.LENGTH_SHORT).show()
      onBackPressed()
    }
  }
  
  AdminLoginContent(
    showLoading = loginResult == ViewModelResult.Loading,
    login = viewModel::login,
    onBackPressed = onBackPressed,
  )
}

@Composable
fun AdminLoginContent(
  showLoading: Boolean,
  login: (String, String) -> Unit,
  onBackPressed: () -> Unit,
) {
  Scaffold(
    topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .padding(paddingValues = paddingValues)
        .background(color = MaterialTheme.colors.background),
    ) {
      AdminLoginBody(login = login)
      if (showLoading) com.alientodevida.alientoapp.designsystem.components.LoadingIndicator()
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
        contentScale = ContentScale.Inside,
        alignment = Alignment.Center,
        modifier = Modifier
          .padding(8.dp)
          .fillMaxWidth(),
        contentDescription = null,
      )
    },
    navigationIcon = {
        com.alientodevida.alientoapp.designsystem.components.ClickableIcon(
            modifier = modifier,
            icon = R.drawable.ic_back_24,
            contentDescription = "Back Button",
            tint = Color.Black,
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
fun AdminLoginBody(
  login: (String, String) -> Unit,
) {
  Column(
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
  ) {
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.H5(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Login",
          color = MaterialTheme.colors.onBackground,
      )
  
    var email by remember { mutableStateOf("") }
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.InputField(
          modifier = Modifier.fillMaxWidth(),
          value = email,
          onChanged = { email = it },
          placeholder = "Email",
          placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      )
  
    var password by remember { mutableStateOf("") }
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.InputField(
          modifier = Modifier.fillMaxWidth(),
          value = password,
          onChanged = { password = it },
          placeholder = "Password",
          placeholderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
      )
    
    Spacer(modifier = Modifier.height(16.dp))
      com.alientodevida.alientoapp.designsystem.components.FilledButton(
          text = "Login".uppercase(),
          modifier = Modifier.fillMaxWidth(),
          colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface),
          textColor = MaterialTheme.colors.onSurface,
      ) { login(email, password) }
  }
}

@Preview
@Composable
fun NotificationsPreview() {
    com.alientodevida.alientoapp.designsystem.theme.AppTheme {
        AdminLoginContent(
            showLoading = true,
            login = { _, _ -> },
            onBackPressed = {},
        )
    }
}