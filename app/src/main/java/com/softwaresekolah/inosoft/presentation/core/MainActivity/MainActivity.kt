package com.softwaresekolah.inosoft.presentation.core.MainActivity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowInsets.Side
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.messaging.FirebaseMessaging
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.presentation.core.common.CameraPermissionTextProvider
import com.softwaresekolah.inosoft.presentation.core.common.NotificationPermissionTextProvider
import com.softwaresekolah.inosoft.presentation.core.common.PermissionDialog
import com.softwaresekolah.inosoft.presentation.core.navgraph.NavGraph
import com.softwaresekolah.inosoft.presentation.core.theme.SsV3Theme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        arrayOf()
    }

    private val viewModel by viewModels<MainViewModel>()


    @Inject
    lateinit var localManager: LocalManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dialogQueue = viewModel.visiblePermissionDialogQueue

        installSplashScreen().apply {
            setKeepOnScreenCondition( condition = {
                    viewModel.splashCondition.value
                }
            )
        }
        setContent {
            SsV3Theme {

                val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { perms ->
                        permissionsToRequest.forEach { permission ->
                            viewModel.onPermissionResult(
                                permission = permission,
                                isGranted = perms[permission] == true
                            )
                        }
                    }
                )
                setStatusBarColor(color = Color.Transparent)
                Box(modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()) {
                    NavGraph(startDestination = viewModel.startDestination.value)
                }

                LaunchedEffect(true) {
                    multiplePermissionResultLauncher.launch(permissionsToRequest)
                }

                LaunchedEffect(viewModel.currentFcmToken) {
                    setupFcm()
                }

                dialogQueue
                    .reversed()
                    .forEach { permission ->
                        PermissionDialog(
                            permissionTextProvider = when (permission) {
                                Manifest.permission.POST_NOTIFICATIONS -> {
                                    NotificationPermissionTextProvider()
                                }
                                else -> return@forEach
                            },
                            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                                permission
                            ),
                            onDismiss = viewModel::dismissDialog,
                            onOkClick = {
                                viewModel.dismissDialog()
                                multiplePermissionResultLauncher.launch(
                                    arrayOf(permission)
                                )
                            },
                            onGoToAppSettingsClick = ::openAppSettings
                        )
                    }
            }
        }
    }

    private fun setupFcm(){
        FirebaseMessaging.getInstance().token.addOnSuccessListener { newToken ->
            if(newToken != null){
                val oldToken = viewModel.currentFcmToken.value
                Timber.tag("MainActivity").d("oldToken : $oldToken")
                Timber.tag("MainActivity").d("newToken: $newToken")
                if(viewModel.currentFcmToken.value != newToken){
                    viewModel.saveToken(newToken)
                }
            }
        }
    }

}

@Composable
fun setStatusBarColor(color: Color) {
    val view = LocalView.current

    if (!view.isInEditMode){
        LaunchedEffect(true) {
            val window = (view.context as Activity).window
            window.statusBarColor = color.toArgb()
        }
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}