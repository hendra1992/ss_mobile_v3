package com.softwaresekolah.inosoft.presentation.home

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.SharedViewModel
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.SharedViewModelEvent
import com.softwaresekolah.inosoft.presentation.core.common.SoftwareSekolahButton
import com.softwaresekolah.inosoft.presentation.home.component.WebView
import com.softwaresekolah.inosoft.util.NetworkMonitor
import com.softwaresekolah.inosoft.util.SsBiometricPromptManager
import javax.inject.Inject

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
) {
    val context = LocalContext.current as FragmentActivity


    val networkMonitor = NetworkMonitor(context)
    val isNetworkAvailable = remember {
        mutableStateOf(networkMonitor.isNetworkAvailable())
    }

    SideEffect {
        onNavigate()
    }

    if (isNetworkAvailable.value){
        val url = "file:///android_asset/webview.html"
        Box(modifier = modifier){
            WebView(url)
        }
    }else{
        Box(modifier = modifier){

        }
    }

}