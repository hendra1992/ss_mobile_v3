package com.softwaresekolah.inosoft.core.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SsTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    label: String,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    height: Dp = 42.dp,
    trailingIcon: ImageVector? = null,
    trailingIconOnClick: ()-> Unit = {},
) {
    Column(modifier = modifier) {
        Text(text = label)
        Spacer(modifier = Modifier.height(10.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            readOnly = readOnly,
            singleLine = singleLine
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .height(height)
                    .background(Color(0xFFEFEEEE)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ){
                    it.invoke()
                }
                trailingIcon?.let {
                    IconButton(onClick = trailingIconOnClick) {
                        Icon(imageVector = trailingIcon, contentDescription = null, tint = Color(0xFF828282))
                    }
                }
            }
        }
    }
}

@Composable
fun SsPasswordTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    label: String,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    height: Dp = 42.dp,
    value: String,
) {
    Column(modifier = modifier) {

        var isPasswordVisible by remember {
            mutableStateOf(false)
        }

        Text(text = label)
        Spacer(modifier = Modifier.height(10.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            readOnly = readOnly,
            singleLine = singleLine,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .height(height)
                    .background(Color(0xFFEFEEEE)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ){
                    it.invoke()
                }
                    IconButton(onClick = {isPasswordVisible = !isPasswordVisible}) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = Color(0xFF828282)
                        )
                    }
            }
        }
    }
}
