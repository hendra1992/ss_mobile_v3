package com.softwaresekolah.inosoft.presentation.core.common

import android.content.res.Configuration
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.softwaresekolah.inosoft.presentation.core.theme.WhiteGray

@Composable
fun SoftwareSekolahButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = Color.White,
    style: TextStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
    ) {
        Text(
            text = text,
            style = style
        )
    }
}

@Composable
fun SoftwareSekolahTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    style: TextStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
    color: Color = WhiteGray,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
    ){
        Text(
            text = text,
            style = style,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SsButtonPreview() {

}