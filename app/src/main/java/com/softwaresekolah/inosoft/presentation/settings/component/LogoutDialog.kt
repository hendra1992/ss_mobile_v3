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
import com.softwaresekolah.inosoft.domain.core.models.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoutDialog(user: User? = null, logout: () -> Unit, isLogoutDialogShow: MutableState<Boolean>, cancel: () -> Unit = {}) {
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
            onNegativeClick = {
                cancel()
            }
        ),
        onPositiveValid = true,
        body = {
            if (user != null){
                Text(text = "Logout Akun ${user.siswaNama} ?")                
            }else{
                Text(text = "Apakah anda yakin mau logout ?")
            }
        },
    )
}