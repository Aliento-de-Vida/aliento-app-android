package com.alientodevida.alientoapp.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.IconOpacity
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun InputField(
    value: String,
    onChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current.copy(MaterialTheme.colors.onSurface),
    label: String? = null,
    labelColor: Color = MaterialTheme.colors.secondary,
    placeholder: String = "",
    placeholderColor: Color = MaterialTheme.colors.secondary,
    isError: Boolean = false,
    errorColor: Color = MaterialTheme.colors.error,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = textFieldColors(),
    singleLine: Boolean = true,
    enabled: Boolean = true,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = value,
        onValueChange = onChanged,
        modifier = modifier,
        singleLine = singleLine,
        isError = isError,
        textStyle = textStyle,
        label = if (label != null) {
            {
                Body2(
                    text = if (isError) "$label*" else label,
                    color = if (isError) errorColor else labelColor,
                )
            }
        } else {
            null
        },
        placeholder = { Body2(text = placeholder, color = placeholderColor) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        shape = shape,
        colors = colors,
        enabled = enabled,
    )
}

@Composable
fun OutlinedInputField(
    value: String,
    onChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    inputModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current.copy(MaterialTheme.colors.onSurface),
    label: String,
    labelColor: Color = MaterialTheme.colors.secondary,
    placeholder: String = label,
    placeholderColor: Color = MaterialTheme.colors.secondary,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    colors: TextFieldColors = textFieldColors(),
    singleLine: Boolean = true,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.surface,
    borderWidth: Dp = 1.dp,
    borderColor: Color = MaterialTheme.colors.primary,
    shape: Shape = MaterialTheme.shapes.small,
) {
    Outlined(
        modifier = modifier,
        backgroundColor = backgroundColor,
        borderWidth = borderWidth,
        borderColor = borderColor,
        shape = shape,
        content = {
            InputField(
                value = value,
                onChanged = onChanged,
                modifier = inputModifier.fillMaxWidth(),
                textStyle = textStyle,
                label = label,
                labelColor = labelColor,
                placeholder = placeholder,
                placeholderColor = placeholderColor,
                keyboardType = keyboardType,
                imeAction = imeAction,
                shape = shape,
                colors = colors,
                singleLine = singleLine,
                enabled = enabled,
            )
        },
    )
}

@Composable
fun ErrorInputField(
    value: String,
    onChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    inputModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current.copy(MaterialTheme.colors.onSurface),
    label: String,
    labelColor: Color = MaterialTheme.colors.secondary,
    placeholder: String = label,
    placeholderColor: Color = MaterialTheme.colors.secondary,
    error: String? = null,
    errorColor: Color = MaterialTheme.colors.error,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    colors: TextFieldColors = textFieldColors(),
    singleLine: Boolean = true,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.small,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        InputField(
            value = value,
            onChanged = onChanged,
            modifier = inputModifier.fillMaxWidth(),
            textStyle = textStyle,
            label = label,
            labelColor = labelColor,
            placeholder = placeholder,
            placeholderColor = placeholderColor,
            isError = error != null,
            errorColor = errorColor,
            keyboardType = keyboardType,
            imeAction = imeAction,
            shape = shape,
            colors = colors,
            singleLine = singleLine,
            enabled = enabled,
        )
        Overline(
            text = error ?: "",
            color = MaterialTheme.colors.error,
            modifier = Modifier.padding(start = 6.dp),
        )
    }
}

@Composable
fun OutlinedErrorInputField(
    value: String,
    onChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    outlinedModifier: Modifier = Modifier,
    inputModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current.copy(MaterialTheme.colors.onSurface),
    label: String,
    labelColor: Color = MaterialTheme.colors.secondary,
    placeholder: String = label,
    placeholderColor: Color = MaterialTheme.colors.secondary,
    error: String? = null,
    errorColor: Color = MaterialTheme.colors.error,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    colors: TextFieldColors = textFieldColors(),
    singleLine: Boolean = true,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.surface,
    borderWidth: Dp = 1.dp,
    borderColor: Color = MaterialTheme.colors.primary,
    shape: Shape = MaterialTheme.shapes.small,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        Outlined(
            modifier = outlinedModifier.fillMaxWidth(),
            backgroundColor = backgroundColor,
            borderWidth = borderWidth,
            borderColor = borderColor,
            shape = shape,
            content = {
                InputField(
                    value = value,
                    onChanged = onChanged,
                    modifier = inputModifier.fillMaxWidth(),
                    textStyle = textStyle,
                    label = label,
                    labelColor = labelColor,
                    placeholder = placeholder,
                    placeholderColor = placeholderColor,
                    isError = error != null,
                    errorColor = errorColor,
                    keyboardType = keyboardType,
                    imeAction = imeAction,
                    shape = shape,
                    colors = colors,
                    singleLine = singleLine,
                    enabled = enabled,
                )
            },
        )
        Overline(
            text = error ?: "",
            color = MaterialTheme.colors.error,
            modifier = Modifier.padding(start = 6.dp),
        )
    }
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun PasswordField(
    value: String,
    onChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current.copy(MaterialTheme.colors.onSurface),
    label: String,
    labelColor: Color = MaterialTheme.colors.secondary,
    placeholder: String = label,
    placeholderColor: Color = MaterialTheme.colors.secondary,
    isError: Boolean = false,
    errorColor: Color = MaterialTheme.colors.error,
    keyboardType: KeyboardType = KeyboardType.Password,
    imeAction: ImeAction = ImeAction.Done,
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = textFieldColors(),
    singleLine: Boolean = true,
    enabled: Boolean = true,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var passwordShowing by remember { mutableStateOf(false) }
    TextField(
        value = value,
        onValueChange = onChanged,
        modifier = modifier,
        singleLine = singleLine,
        isError = isError,
        textStyle = textStyle,
        label = {
            Caption(
                text = if (isError) "$label*" else label,
                color = if (isError) errorColor else labelColor,
            )
        },
        placeholder = { Body2(text = placeholder, color = placeholderColor) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        visualTransformation = if (passwordShowing) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordShowing = !passwordShowing }) {
                Icon(
                    imageVector = if (passwordShowing) Filled.VisibilityOff else Filled.Visibility,
                    contentDescription = if (passwordShowing) "Hide" else "Show",
                )
            }
        },
        shape = shape,
        colors = colors,
        enabled = enabled,
    )
}

@Composable
fun ErrorPasswordField(
    value: String,
    onChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    inputModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current.copy(MaterialTheme.colors.onSurface),
    label: String,
    labelColor: Color = MaterialTheme.colors.secondary,
    placeholder: String = label,
    placeholderColor: Color = MaterialTheme.colors.secondary,
    error: String? = null,
    errorColor: Color = MaterialTheme.colors.error,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    colors: TextFieldColors = textFieldColors(),
    singleLine: Boolean = true,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.small,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        PasswordField(
            value = value,
            onChanged = onChanged,
            modifier = inputModifier.fillMaxWidth(),
            textStyle = textStyle,
            label = label,
            labelColor = labelColor,
            placeholder = placeholder,
            placeholderColor = placeholderColor,
            isError = error != null,
            errorColor = errorColor,
            keyboardType = keyboardType,
            imeAction = imeAction,
            shape = shape,
            colors = colors,
            singleLine = singleLine,
            enabled = enabled,
        )
        Overline(
            text = error ?: "",
            color = MaterialTheme.colors.error,
            modifier = Modifier.padding(start = 6.dp),
        )
    }
}

@Composable
fun OutlinedErrorPasswordField(
    value: String,
    onChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    outlinedModifier: Modifier = Modifier,
    inputModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current.copy(MaterialTheme.colors.onSurface),
    label: String,
    labelColor: Color = MaterialTheme.colors.secondary,
    placeholder: String = label,
    placeholderColor: Color = MaterialTheme.colors.secondary,
    error: String? = null,
    errorColor: Color = MaterialTheme.colors.error,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    colors: TextFieldColors = textFieldColors(),
    singleLine: Boolean = true,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.surface,
    borderWidth: Dp = 1.dp,
    borderColor: Color = MaterialTheme.colors.primary,
    shape: Shape = MaterialTheme.shapes.small,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        Outlined(
            modifier = outlinedModifier.fillMaxWidth(),
            backgroundColor = backgroundColor,
            borderWidth = borderWidth,
            borderColor = borderColor,
            shape = shape,
            content = {
                PasswordField(
                    value = value,
                    onChanged = onChanged,
                    modifier = inputModifier.fillMaxWidth(),
                    textStyle = textStyle,
                    label = label,
                    labelColor = labelColor,
                    placeholder = placeholder,
                    placeholderColor = placeholderColor,
                    isError = error != null,
                    errorColor = errorColor,
                    keyboardType = keyboardType,
                    imeAction = imeAction,
                    shape = shape,
                    colors = colors,
                    singleLine = singleLine,
                    enabled = enabled,
                )
            },
        )
        Overline(
            text = error ?: "",
            color = MaterialTheme.colors.error,
            modifier = Modifier.padding(start = 6.dp),
        )
    }
}

@Composable
fun textFieldColors(
    textColor: Color = LocalContentColor.current.copy(LocalContentAlpha.current),
    disabledTextColor: Color = textColor.copy(ContentAlpha.disabled),
    backgroundColor: Color = MaterialTheme.colors.surface,
    cursorColor: Color = MaterialTheme.colors.primary,
    errorCursorColor: Color = MaterialTheme.colors.error,
    focusedIndicatorColor: Color = Color.Transparent,
    unfocusedIndicatorColor: Color = Color.Transparent,
    disabledIndicatorColor: Color = Color.Transparent,
    errorIndicatorColor: Color = Color.Transparent,
    leadingIconColor: Color = MaterialTheme.colors.onSurface.copy(alpha = IconOpacity),
    disabledLeadingIconColor: Color = leadingIconColor.copy(alpha = ContentAlpha.disabled),
    errorLeadingIconColor: Color = leadingIconColor,
    trailingIconColor: Color = MaterialTheme.colors.onSurface.copy(alpha = IconOpacity),
    disabledTrailingIconColor: Color = trailingIconColor.copy(alpha = ContentAlpha.disabled),
    errorTrailingIconColor: Color = MaterialTheme.colors.error,
    focusedLabelColor: Color = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high),
    unfocusedLabelColor: Color = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
    disabledLabelColor: Color = unfocusedLabelColor.copy(ContentAlpha.disabled),
    errorLabelColor: Color = MaterialTheme.colors.error,
    placeholderColor: Color = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
    disabledPlaceholderColor: Color = placeholderColor.copy(ContentAlpha.disabled),
): TextFieldColors = TextFieldDefaults.textFieldColors(
    textColor = textColor,
    disabledTextColor = disabledTextColor,
    backgroundColor = backgroundColor,
    cursorColor = cursorColor,
    errorCursorColor = errorCursorColor,
    focusedIndicatorColor = focusedIndicatorColor,
    unfocusedIndicatorColor = unfocusedIndicatorColor,
    disabledIndicatorColor = disabledIndicatorColor,
    errorIndicatorColor = errorIndicatorColor,
    leadingIconColor = leadingIconColor,
    disabledLeadingIconColor = disabledLeadingIconColor,
    errorLeadingIconColor = errorLeadingIconColor,
    trailingIconColor = trailingIconColor,
    disabledTrailingIconColor = disabledTrailingIconColor,
    errorTrailingIconColor = errorTrailingIconColor,
    focusedLabelColor = focusedLabelColor,
    unfocusedLabelColor = unfocusedLabelColor,
    disabledLabelColor = disabledLabelColor,
    errorLabelColor = errorLabelColor,
    placeholderColor = placeholderColor,
    disabledPlaceholderColor = disabledPlaceholderColor,
)
