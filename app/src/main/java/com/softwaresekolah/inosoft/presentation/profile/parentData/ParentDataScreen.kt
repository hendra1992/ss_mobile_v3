package com.softwaresekolah.inosoft.presentation.profile.parentData

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.softwaresekolah.inosoft.presentation.core.common.SimpleLoadingScreen
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileMenuTopBar
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentDataScreen(
    navigateUp: () -> Unit,
    state: ParentDataState,
    onEvent: (ParentDataEvent) -> Unit,
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val dadName = rememberSaveable { mutableStateOf("") }
    val dadPhone = rememberSaveable { mutableStateOf("") }
    val momName = rememberSaveable { mutableStateOf("") }
    val momPhone = rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = { ProfileMenuTopBar(
            title = "Data Diri",
            scrollBehavior = scrollBehavior,
            navigateUp = navigateUp,
            actionOnClick = { onEvent(ParentDataEvent.OnSave(
                dadName = dadName.value,
                dadPhone = dadPhone.value,
                momName = momName.value,
                momPhone = momPhone.value,
            )) }
        ) }
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp, 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            LaunchedEffect(state.parentData) {
                if (state.parentData?.ortu_ayah_nama != null ){
                    dadName.value = state.parentData?.ortu_ayah_nama.toString()
                }

                if (state.parentData?.ortu_ayah_hp != null ){
                    dadPhone.value = state.parentData?.ortu_ayah_hp.toString()
                }

                if (state.parentData?.ortu_ibu_nama != null ){
                    momName.value = state.parentData?.ortu_ibu_nama.toString()
                }

                if (state.parentData?.ortu_ibu_hp != null ){
                    momPhone.value = state.parentData?.ortu_ibu_hp.toString()
                }
            }

            LaunchedEffect (state.text){
                if (state.text != null && state.text != ""){
                    Toast.makeText(context, state.text, Toast.LENGTH_SHORT).show()
                    onEvent(ParentDataEvent.OnClearText)
                }
            }

            LaunchedEffect (state.success){
                if (state.success != null && state.success != ""){
                    Toast.makeText(context, state.success, Toast.LENGTH_SHORT).show()
                    onEvent(ParentDataEvent.OnClearText)
                    navigateUp()
                }
            }




            ProfileTextField(modifier = Modifier.fillMaxWidth(), title = "Nama Ayah", state = dadName, isError = state.dadNameIsError, supText = state.dadNameErrorText)
            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "No. Telepon Ayah",
                state = dadPhone,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.dadPhoneIsError, supText = state.dadPhoneErrorText
            )
            ProfileTextField(modifier = Modifier.fillMaxWidth(), title = "Nama Ibu", state = momName, isError = state.momNameIsError, supText = state.momNameErrorText)
            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "No. Telepon Ibu",
                state = momPhone,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.momPhoneIsError, supText = state.momPhoneErrorText
            )

        }


        if (state.isLoading){
            SimpleLoadingScreen()
        }
    }
}