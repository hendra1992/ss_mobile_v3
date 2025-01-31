package com.softwaresekolah.inosoft.presentation.settings.changePassword

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.navigateTo
import com.softwaresekolah.inosoft.presentation.core.common.SimpleLoadingScreen
import com.softwaresekolah.inosoft.presentation.core.common.SoftwareSekolahButton
import com.softwaresekolah.inosoft.presentation.core.navgraph.Route
import com.softwaresekolah.inosoft.presentation.settings.component.ChangePasswordTextField
import com.softwaresekolah.inosoft.presentation.settings.component.SettingMenuTopbar
import com.softwaresekolah.inosoft.presentation.settings.setting.SettingEvent
import kotlin.reflect.KFunction1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    navController: NavController,
    state: ChangePasswordState,
    navigateUp: () -> Unit,
    onEvent: (ChangePasswordEvent) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val currentPass = rememberSaveable { mutableStateOf("") }
    val newPass = rememberSaveable { mutableStateOf("") }
    val confNewPass = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = { SettingMenuTopbar(title = "List Account", scrollBehavior = scrollBehavior, navigateUp = navigateUp) },
        bottomBar = {
                SoftwareSekolahButton(
                    text = "Ganti Password",
                    onClick = { onEvent(ChangePasswordEvent.OnSubmit(currentPass = currentPass.value, newPass = newPass.value, confNewPass = confNewPass.value)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp, 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            LaunchedEffect (state.text){
                if (state.text != null && state.text != ""){
                    Toast.makeText(context, state.text, Toast.LENGTH_SHORT).show()
                    onEvent(ChangePasswordEvent.OnClearText)
                }
            }

            LaunchedEffect (state.success){
                if (state.success != null && state.success != ""){
                    Toast.makeText(context, state.success, Toast.LENGTH_SHORT).show()
                    onEvent(ChangePasswordEvent.OnClearText)
                    navigateUp()
                }
            }

            ChangePasswordTextField(modifier = Modifier.fillMaxWidth(), title = "Password Saat Ini", state = currentPass, isError = state.currentPassIsError, supText = state.currentPassErrorText)
            ChangePasswordTextField(modifier = Modifier.fillMaxWidth(), title = "Password Baru", state = newPass, isError = state.newPassIsError, supText = state.newPassErrorText)
            ChangePasswordTextField(modifier = Modifier.fillMaxWidth(), title = "Konfirmasi Password Baru", state = confNewPass, isError = state.confNewPassIsError, supText = state.confNewPassErrorText)

        }

        if (state.isLoading){
            SimpleLoadingScreen()
        }
    }

}