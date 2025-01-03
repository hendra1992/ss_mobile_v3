package com.softwaresekolah.inosoft.presentation.notification.list.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.maxkeppeker.sheets.core.CoreDialog
import com.maxkeppeker.sheets.core.models.CoreSelection
import com.maxkeppeker.sheets.core.models.base.ButtonStyle
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDialog(action: () -> Unit, isNotificationDialogShow: MutableState<Boolean>, cancel: () -> Unit = {}, text: String) {
    CoreDialog(
        state = rememberUseCaseState(visible = true, onCloseRequest = {isNotificationDialogShow.value = false}),

        selection = CoreSelection(
            withButtonView = true,
            negativeButton = SelectionButton(
                "Batal",
                null,
                ButtonStyle.TEXT
            ),
            onPositiveClick = {action()},
            positiveButton = SelectionButton(
                "Iya",
                null,
                ButtonStyle.FILLED
            ),
            onNegativeClick = {
                cancel()
            }
        ),
        onPositiveValid = true,
        body = {
                Text(text = text)
        },
    )
}