package com.softwaresekolah.inosoft.presentation.settings.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.maxkeppeker.sheets.core.CoreDialog
import com.maxkeppeker.sheets.core.models.CoreSelection
import com.maxkeppeker.sheets.core.models.base.ButtonStyle
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoutDialog(logout: () -> Unit, isLogoutDialogShow: MutableState<Boolean>) {
    CoreDialog(
        state = rememberUseCaseState(visible = true, onCloseRequest = {isLogoutDialogShow.value = false}),

        selection = CoreSelection(
            withButtonView = true,
            negativeButton = SelectionButton(
                "Batal",
                null,
                ButtonStyle.TEXT
            ),
            onPositiveClick = {logout()},
            positiveButton = SelectionButton(
                "Iya",
                null,
                ButtonStyle.FILLED
            ),
        ),
        onPositiveValid = true,
        body = {
            Text(text = "Apakah anda yakin mau logout ?")
        },
    )
}