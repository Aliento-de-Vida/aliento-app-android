package com.alientodevida.alientoapp.donations

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.domain.common.BankAccount
import com.alientodevida.alientoapp.domain.common.DonationType
import com.alientodevida.alientoapp.domain.common.PaymentItem
import com.alientodevida.alientoapp.domain.common.Paypal
import com.alientodevida.alientoapp.ui.extensions.copyToClipboard
import com.alientodevida.alientoapp.ui.extensions.goToUrl

@Composable
fun Donations(
  viewModel: DonationsViewModel,
  onBackPressed: () -> Unit,
) {
  
  val viewModelState by viewModel.viewModelState.collectAsState()
  val context = LocalContext.current
  
  DonationsContent(
    uiState = viewModelState,
    onBackPressed = onBackPressed,
    onCardClick = { onCardClick(context, it) },
  )
}

private fun onCardClick(context: Context, item: PaymentItem) {
  when {
    item.paypal != null -> context.goToUrl(item.paypal!!.url)
    item.bankAccount != null -> {
      context.copyToClipboard(
        name = "Número de tarjeta",
        value = item.bankAccount!!.cardNumber
      )
    }
  }
}

@Composable
fun DonationsContent(
    uiState: DonationsUiState,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onBackPressed: () -> Unit,
    onCardClick: (PaymentItem) -> Unit,
) {
  Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
      TopAppBar(onBackPressed = onBackPressed)
    },
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .padding(paddingValues = paddingValues)
        .background(color = MaterialTheme.colors.background),
    ) {
      DonationsBody(
        paymentOptions = uiState.paymentOptions,
        onCardClick = onCardClick,
      )
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
        com.alientodevida.alientoapp.designsystem.components.ClickableIcon(
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
fun DonationsBody(
    paymentOptions: List<PaymentItem>,
    onCardClick: (PaymentItem) -> Unit,
) {
  Column(Modifier.padding(horizontal = 8.dp)) {
    Spacer(modifier = Modifier.height(8.dp))
      com.alientodevida.alientoapp.designsystem.components.H5(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = "Participa junto con nosotros para avanzar en la obra",
          color = MaterialTheme.colors.onBackground,
      )
    Spacer(modifier = Modifier.height(8.dp))
      com.alientodevida.alientoapp.designsystem.components.Body2(
          modifier = Modifier.align(Alignment.End),
          text = "(Haz click para copiar los datos)",
          color = MaterialTheme.colors.onBackground,
      )
    Spacer(modifier = Modifier.height(16.dp))
    LazyColumn(
      contentPadding = PaddingValues(bottom = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      items(paymentOptions, key = { it.id }) { item ->
        DonationItem(
          modifier = Modifier.animateItemPlacement(),
          item = item,
          onClick = onCardClick,
        )
      }
    }
  }
}

@Composable
fun DonationItem(
    modifier: Modifier = Modifier,
    item: PaymentItem,
    onClick: (PaymentItem) -> Unit,
) {
  Card(
    modifier
      .fillMaxWidth()
      .aspectRatio(1.77f)
      .clickable { onClick(item) },
  ) {
    when {
      item.paypal != null -> PayPalItemContent()
      item.bankAccount != null -> BankAccountItem(item.bankAccount!!)
    }
  }
}

@Composable
private fun BankAccountItem(item: BankAccount) {
  Box {
    Image(
      painter = painterResource(item.backgroundResource),
      contentScale = ContentScale.Crop,
      alignment = Alignment.Center,
      contentDescription = null,
    )
    
    Column(Modifier.padding(8.dp)) {
      Spacer(modifier = Modifier.weight(1.0f))
        com.alientodevida.alientoapp.designsystem.components.Subtitle1(
            text = "No. de Cuenta",
            color = colorResource(R.color.pantone_white_c),
        )
        com.alientodevida.alientoapp.designsystem.components.Subtitle1(
            text = item.accountNumber,
            color = colorResource(R.color.pantone_white_c),
        )

        com.alientodevida.alientoapp.designsystem.components.Subtitle1(
            text = "Clabe",
            color = colorResource(R.color.pantone_white_c),
        )
        com.alientodevida.alientoapp.designsystem.components.Subtitle1(
            text = item.clabe,
            color = colorResource(R.color.pantone_white_c),
        )

        com.alientodevida.alientoapp.designsystem.components.Subtitle1(
            text = "No. de Tarjeta",
            color = colorResource(R.color.pantone_white_c),
        )
        com.alientodevida.alientoapp.designsystem.components.Subtitle1(
            text = item.cardNumber,
            color = colorResource(R.color.pantone_white_c),
        )
    }
  }
}

@Composable
private fun PayPalItemContent() {
  Box(
    Modifier
      .background(Color.White)
      .padding(8.dp)
  ) {
    Image(
      painter = painterResource(R.drawable.paypal_logo),
      contentScale = ContentScale.Inside,
      alignment = Alignment.Center,
      modifier = Modifier
        .size(150.dp, 70.dp)
        .align(Alignment.Center),
      contentDescription = null,
    )
      com.alientodevida.alientoapp.designsystem.components.Subtitle1(
          modifier = Modifier.align(Alignment.BottomStart),
          text = "Click para donar",
          color = Color.Black,
      )
  }
}


@Preview
@Composable
fun NotificationsPreview() {
  com.alientodevida.alientoapp.designsystem.theme.AppTheme {
    DonationsContent(
      DonationsUiState(
        listOf(
          PaymentItem(
            0,
            DonationType.OFRENDA,
            "Aliento de Vida AC",
            null,
            BankAccount(
              R.drawable.bbva_card,
              "BBVA BANCOMER",
              "4555 1130 0604 1497",
              "0113500640",
              "012910001135006409",
            ),
          ),
          PaymentItem(
            1,
            DonationType.OFRENDA,
            "Aliento de Vida AC",
            Paypal("https://www.paypal.com/paypalme/AlientoDeVidaMx"),
            null,
          ),
        ),
      ),
      onBackPressed = {},
      onCardClick = {},
    )
  }
}