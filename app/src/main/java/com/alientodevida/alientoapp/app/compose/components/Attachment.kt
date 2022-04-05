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
import androidx.compose.foundation.layout.Column
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
import com.alientodevida.alientoapp.domain.common.Attachment as DomainAttachment

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
fun AttachmentModel?.getDomainAttachment(context: Context, name: String? = null) =
  this?.let { attachment ->
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
fun AttachmentsWithCurrentImages(
  attachmentLimit: Int = 5,
  newAttachmentsTitle: String? = null,
  currentAttachmentsTitle: String? = null,
  newAttachments: List<AttachmentModel>,
  currentAttachments: List<String>,
  addToNewAttachments: (AttachmentModel) -> Unit,
  removeFromNewAttachments: (AttachmentModel) -> Unit,
  removeCurrentAttachment: (String) -> Unit
) {
  Spacer(modifier = Modifier.height(16.dp))
  Attachments(
    limit = attachmentLimit - currentAttachments.count(),
    attachmentsTitle = newAttachmentsTitle,
    attachments = newAttachments,
    addAttachment = addToNewAttachments,
    removeAttachment = removeFromNewAttachments,
  )
  
  if (currentAttachments.isNotEmpty()) {
    Spacer(modifier = Modifier.height(16.dp))
    CurrentAttachment(
      currentImagesTitle = currentAttachmentsTitle,
      images = currentAttachments,
      removeImage = removeCurrentAttachment,
    )
  }
}

@Composable
private fun CurrentAttachment(
  currentImagesTitle: String? = null,
  images: List<String>,
  removeImage: (String) -> Unit,
) {
  if (images.isNotEmpty()) {
    Spacer(Modifier.height(8.dp))
    
    Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      currentImagesTitle?.let {
        Body1(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = currentImagesTitle,
          color = MaterialTheme.colors.onBackground,
        )
        Spacer(Modifier.height(8.dp))
      }
      
      images.forEach { imageName ->
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
            contentDescription = "image",
            tint = MaterialTheme.colors.onSurface,
          )
          
          Spacer(modifier = Modifier.width(8.dp))
          Body2(
            modifier = Modifier.weight(1.0f),
            text = imageName, color = MaterialTheme.colors.onBackground
          )
          
          Spacer(modifier = Modifier.width(8.dp))
          ClickableIcon(
            icon = R.drawable.ic_delete_24,
            contentDescription = "Remove image",
            tint = MaterialTheme.colors.onSurface,
          ) { removeImage(imageName) }
        }
      }
    }
    
    Spacer(modifier = Modifier.height(16.dp))
  }
}

@Composable
fun Attachments(
  limit: Int = 5,
  attachmentsTitle: String? = null,
  attachments: List<AttachmentModel>,
  addAttachment: (AttachmentModel) -> Unit,
  removeAttachment: (AttachmentModel) -> Unit,
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
    addAttachment(context.createAttachment(value))
    uri = null
  }
  if (attachments.count() < limit) {
    FilledButton(
      text = "Agregar Imágen".uppercase(),
      modifier = Modifier.fillMaxWidth(),
      colors = ButtonDefaults.buttonColors(MaterialTheme.colors.surface),
      textColor = MaterialTheme.colors.onSurface,
    ) { launcher.launch(Unit) }
  }
  
  if (attachments.isNotEmpty()) {
    Spacer(Modifier.height(8.dp))
    
    Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      attachmentsTitle?.let {
        Body1(
          modifier = Modifier.padding(horizontal = 8.dp),
          text = it,
          color = MaterialTheme.colors.onBackground,
        )
        Spacer(Modifier.height(8.dp))
      }
      
      attachments.forEach { attachment ->
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
          Body2(
            modifier = Modifier.weight(1.0f),
            text = attachment.displayName, color = MaterialTheme.colors.onBackground
          )
          
          Spacer(modifier = Modifier.width(8.dp))
          ClickableIcon(
            icon = R.drawable.ic_delete_24,
            contentDescription = "Remove attachment",
            tint = MaterialTheme.colors.onSurface,
          ) { removeAttachment(attachment) }
        }
      }
    }
    
    Spacer(modifier = Modifier.height(16.dp))
  }
}