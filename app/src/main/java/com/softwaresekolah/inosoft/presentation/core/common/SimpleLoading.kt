package com.softwaresekolah.inosoft.presentation.core.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.softwaresekolah.inosoft.presentation.core.theme.SsV3Theme

// 1. Create the state

data class SimpleLoadingState(
    val loading: Boolean = false
)
// 3. Create the screen

@Composable
fun SimpleLoadingScreen() {

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Gray.copy(alpha = 0.4f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(20.dp))
        CircularProgressIndicator()
        Spacer(modifier = Modifier.padding(20.dp))
        Text("Wait...", color = MaterialTheme.colorScheme.primary)

    }
}


// 4. Create a preview
//@Preview
//@Composable
//fun SimpleLoadingPreview() {
//    SsV3Theme{
//        Surface {
//            SimpleLoadingScreen()
//        }
//    }
//}