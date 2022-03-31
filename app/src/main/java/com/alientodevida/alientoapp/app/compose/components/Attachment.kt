package com.alientodevida.alientoapp.app.compose.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.features.notifications.editcreate.createAttachment
import com.alientodevida.alientoapp.app.features.notifications.editcreate.parcelFileDescriptor
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.io.path.absolutePathString
import com.alientodevida.alientoapp.domain.notification.Attachment as DomainAttachment

data class AttachmentModel(
  val uri: Uri,
  val fileExtension: String? = null,
) {
  val displayName: String
    get() {
      val toString = uri.toString()
      val name = toString.substring(toString.lastIndexOf('/') + 1)
      return fileExtension?.let { "$name.$it" } ?: name
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun AttachmentModel?.getDomainAttachment(context: Context, name: String? = null) = this?.let { attachment ->
  val attachmentsDir = Paths.get(context.filesDir.path, "notification-attachment").toFile()
  attachmentsDir.mkdir()
  
  context.parcelFileDescriptor(attachment.uri)?.use { descriptor ->
    FileInputStream(descriptor.fileDescriptor).use { stream ->
      val outputPath = Paths.get(attachmentsDir.path, attachment.displayName)
      Files.copy(stream, outputPath, StandardCopyOption.REPLACE_EXISTING)
  
      DomainAttachment(
        name ?: attachment.displayName,
        outputPath.absolutePathString()
      )
    }
  }
}

@Composable
fun Attachment(
  attachment: AttachmentModel?,
  onImageChanged: (AttachmentModel) -> Unit,
) {
  var uri: Uri? by remember { mutableStateOf(null) }
  val chooserTitle = "Imágen"
  val launcher = rememberLauncherForActivityResult(
    object : ActivityResultContract<Unit, Uri?>() {
      override fun createIntent(context: Context, input: Unit): Intent =
        Intent.createChooser(
          Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" },
          chooserTitle,
        )
      
      override fun parseResult(resultCode: Int, intent: Intent?): Uri? = intent?.data
    }
  ) { result ->
    uri = result
  }
  uri?.let { value ->
    val context = LocalContext.current
    onImageChanged(context.createAttachment(value))
    uri = null
  }
  FilledButton(
    text = "Agregar Imágen".uppercase(),
    modifier = Modifier.fillMaxWidth(),
    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface),
    textColor = MaterialTheme.colors.onSurface,
  ) { launcher.launch(Unit) }
  
  attachment?.let {
    Spacer(modifier = Modifier.height(16.dp))
    Row(
      modifier = Modifier
        .border(width = 1.dp, color = MaterialTheme.colors.onBackground)
        .padding(horizontal = 16.dp, vertical = 2.dp)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Icon(
        icon = R.drawable.ic_attachment_24,
        contentDescription = "Attachment",
        tint = MaterialTheme.colors.onSurface,
      )
      
      Spacer(modifier = Modifier.width(8.dp))
      Body2(text = it.displayName, color = MaterialTheme.colors.onBackground)
      
      Spacer(modifier = Modifier.weight(1.0f))
    }
    Spacer(modifier = Modifier.height(16.dp))
  }
}
