package com.softwaresekolah.inosoft.presentation.profile.component

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FilePresent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun DateTextField(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    value: String,
    title: String,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge
    )
    OutlinedTextField(
        modifier = modifier
            .clickable {
                  onClick()
            },
        value = value,
        onValueChange = {  },
        placeholder = {
            Text(
                text = title
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            //For Icons
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        enabled = false,
        singleLine = true,
        trailingIcon = {
            Icon(imageVector = Icons.Outlined.CalendarMonth, contentDescription = "date")
        }
    )
}