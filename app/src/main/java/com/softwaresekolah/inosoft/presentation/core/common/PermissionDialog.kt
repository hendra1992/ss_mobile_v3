package com.softwaresekolah.inosoft.presentation.core.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.info.InfoDialog
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Timber.tag("!Rationale").d(isPermanentlyDeclined.toString())
    InfoDialog(
        state = rememberUseCaseState(onDismissRequest = {onDismiss();this.finish()}, visible = true),
        header = Header.Default(
            title = "Request Permission"
        ),
        body = InfoBody.Default(
            bodyText = permissionTextProvider.getDescription(isPermanentlyDeclined = isPermanentlyDeclined),
        ),
        selection = InfoSelection(
            onPositiveClick = {
              if (isPermanentlyDeclined){
                  onGoToAppSettingsClick()
              }else{
                  onOkClick()
              }
            },
            negativeButton = null,
            positiveButton = SelectionButton(
                text = if (isPermanentlyDeclined) {
                    "Grant Permission"
                } else {
                    "OK"
                }
            )
        ),
        properties = DialogProperties()
    )
}

interface PermissionTextProvider{
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class CameraPermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined){
            "Sepertinya Anda Telah Menolak Perizinan Kamera Secara Permanen" +
                    "Anda Bisa Mengizinkan Perizinan Kamera Secara Manual Di Pengaturan Aplikasi"
        }else{
            "Aplikasi Ini Membutuhkan Perizinan Kamera Untuk Mengambil Gambar"
        }
    }
}

class NotificationPermissionTextProvider: PermissionTextProvider{
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined){
            "Sepertinya Anda Telah Menolak Perizinan Notfikasi Secara Permanen" +
                    "Anda Bisa Mengizinkan Perizinan Notifikasi Secara Manual Di Pengaturan Aplikasi"
        }else{
            "Aplikasi Ini Membutuhkan Perizinan Notifikasi Untuk Memberikan Pemberitahuan Kepada Anda"
        }
    }
}