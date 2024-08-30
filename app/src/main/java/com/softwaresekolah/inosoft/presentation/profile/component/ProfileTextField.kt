package com.softwaresekolah.inosoft.presentation.profile.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTextField(
    modifier: Modifier = Modifier,
    title: String,
    state: MutableState<String>,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    singleLine: Boolean = true
) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge
    )
    OutlinedTextField(
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        value = state.value,
        onValueChange = { state.value = it },
        placeholder = {
            Text(
                text = title
            )
        },
        colors = OutlinedTextFieldDefaults.colors(),
        singleLine = singleLine
    )
}