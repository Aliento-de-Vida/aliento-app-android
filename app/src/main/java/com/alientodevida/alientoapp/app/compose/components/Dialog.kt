package com.alientodevida.alientoapp.app.compose.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.compose.ui.window.Dialog as ComposeDialog

@Composable
fun Dialog(
  title: String,
  @DrawableRes
  image: Int,
  imageContentDescription: String,
  body: String,
  onDismiss: (() -> Unit)? = null,
  properties: DialogProperties = DialogProperties(),
) {
  ComposeDialog(
    onDismissRequest = {
      onDismiss?.invoke()
    },
    properties = properties,
  ) {
    val titleId = "title"
    val imageId = "image"
    val bodyId = "body"

    val constraintSet = ConstraintSet {
      val titleRef = createRefFor(titleId)
      val imageRef = createRefFor(imageId)
      val bodyRef = createRefFor(bodyId)

      constrain(titleRef) {
        start.linkTo(parent.start)
        top.linkTo(parent.top)
      }
      constrain(imageRef) {
        start.linkTo(parent.start)
        top.linkTo(titleRef.bottom, margin = 24.dp)
        end.linkTo(parent.end)
      }
      constrain(bodyRef) {
        start.linkTo(parent.start)
        top.linkTo(imageRef.bottom, margin = 16.dp)
      }
    }

    Surface(
      color = MaterialTheme.colors.surface,
      shape = MaterialTheme.shapes.medium,
      modifier = Modifier.fillMaxWidth(),
    ) {
      ConstraintLayout(
        constraintSet = constraintSet,
        modifier = Modifier.padding(16.dp)
      ) {
        H6(
          text = title,
          modifier = Modifier
            .layoutId(titleId)
            .testTag(titleId),
          color = MaterialTheme.colors.onSurface,
          fontWeight = FontWeight.Bold,
        )
        Image(
          painter = painterResource(image),
          modifier = Modifier.layoutId(imageId),
          contentDescription = imageContentDescription,
        )
        Body2(
          text = body,
          modifier = Modifier
            .layoutId(bodyId)
            .testTag(bodyId),
          color = MaterialTheme.colors.onSurface,
        )
      }
    }
  }
}

@Composable
fun Dialog(
  title: String,
  body: String,
  action: String,
  actionColor: Color = MaterialTheme.colors.onSurface,
  onAction: (() -> Unit)? = null,
  onDismiss: (() -> Unit)? = null,
  properties: DialogProperties = DialogProperties(),
) {
  ComposeDialog(
    onDismissRequest = {
      onDismiss?.invoke()
    },
    properties = properties,
  ) {
    
    Surface(
      color = MaterialTheme.colors.surface,
      shape = MaterialTheme.shapes.medium,
      modifier = Modifier.fillMaxWidth(),
    ) {
      Column(
        modifier = Modifier.padding(16.dp)
      ) {
        H6(
          text = title,
          color = MaterialTheme.colors.onSurface,
          fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Body2(
          text = body,
          color = MaterialTheme.colors.onSurface,
        )
        Spacer(modifier = Modifier.height(8.dp))
        FlatButton(
          text = action.uppercase(),
          textColor = actionColor,
          onClick = {
            onAction?.invoke()
          },
        )
      }
    }
  }
}

@Preview
@Composable
fun DialogPreview() {
  MaterialTheme {
    Dialog(
      title = "title",
      body = "message",
      action = "action",
      actionColor = MaterialTheme.colors.error,
      onAction = {
      },
      onDismiss = {
      },
    )
  }
}

@Composable
fun Dialog(
  title: String,
  @DrawableRes
  image: Int,
  imageContentDescription: String,
  body: String,
  action: String,
  actionColor: Color = MaterialTheme.colors.onSurface,
  onAction: (() -> Unit)? = null,
  negative: String,
  negativeColor: Color = MaterialTheme.colors.onSurface,
  onNegative: (() -> Unit)? = null,
  onDismiss: (() -> Unit)? = null,
  properties: DialogProperties = DialogProperties(),
) {
  ComposeDialog(
    onDismissRequest = {
      onDismiss?.invoke()
    },
    properties = properties,
  ) {
    val titleId = "title"
    val imageId = "image"
    val bodyId = "body"
    val negativeId = "negative"
    val actionId = "action"

    val constraintSet = ConstraintSet {
      val titleRef = createRefFor(titleId)
      val imageRef = createRefFor(imageId)
      val bodyRef = createRefFor(bodyId)
      val negativeRef = createRefFor(negativeId)
      val actionRef = createRefFor(actionId)

      constrain(titleRef) {
        start.linkTo(parent.start)
        top.linkTo(parent.top)
      }
      constrain(imageRef) {
        start.linkTo(parent.start)
        top.linkTo(titleRef.bottom, margin = 24.dp)
        end.linkTo(parent.end)
      }
      constrain(bodyRef) {
        start.linkTo(parent.start)
        top.linkTo(imageRef.bottom, margin = 16.dp)
      }
      constrain(actionRef) {
        top.linkTo(bodyRef.bottom, margin = 16.dp)
        end.linkTo(parent.end)
      }
      constrain(negativeRef) {
        baseline.linkTo(actionRef.baseline)
        end.linkTo(actionRef.start, margin = 16.dp)
      }
    }

    Surface(
      color = MaterialTheme.colors.surface,
      shape = MaterialTheme.shapes.medium,
      modifier = Modifier.fillMaxWidth(),
    ) {
      ConstraintLayout(
        constraintSet = constraintSet,
        modifier = Modifier.padding(16.dp)
      ) {
        H6(
          text = title,
          modifier = Modifier
            .layoutId(titleId)
            .testTag(titleId),
          color = MaterialTheme.colors.onSurface,
          fontWeight = FontWeight.Bold,
        )
        Image(
          painter = painterResource(image),
          modifier = Modifier.layoutId(imageId),
          contentDescription = imageContentDescription,
        )
        Body2(
          text = body,
          modifier = Modifier
            .layoutId(bodyId)
            .testTag(bodyId),
          color = MaterialTheme.colors.onSurface,
        )
        FlatButton(
          text = negative.uppercase(),
          modifier = Modifier
            .layoutId(negativeId)
            .testTag(negativeId),
          textColor = negativeColor,
          onClick = {
            onNegative?.invoke()
          },
        )
        FlatButton(
          text = action.uppercase(),
          modifier = Modifier
            .layoutId(actionId)
            .testTag(actionId),
          textColor = actionColor,
          onClick = {
            onAction?.invoke()
          },
        )
      }
    }
  }
}
