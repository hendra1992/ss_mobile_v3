package com.softwaresekolah.inosoft.presentation.profile.etc

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.softwaresekolah.inosoft.presentation.core.common.SimpleLoadingScreen
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileMenuTopBar
import com.softwaresekolah.inosoft.presentation.profile.component.SelectOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EtcScreen(
    navigateUp: () -> Unit,
    state: EtcState,
    onEvent: (EtcEvent) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val context = LocalContext.current
    val goldarOption = listOf("-", "A", "B", "AB", "O")
    val goldarSelected = remember{
        mutableStateOf("-")
    }
    val kwnOption = listOf("-", "WNI", "WNA")
    val kwnSelected = remember{
        mutableStateOf("-")
    }

    var agmOption = state.religions
    val agmSelected = remember{
        mutableStateOf("")
    }
    Scaffold(
        topBar = { ProfileMenuTopBar(title = "Data Diri", scrollBehavior = scrollBehavior, navigateUp = navigateUp, actionOnClick = {
            onEvent(EtcEvent.OnSave(goldar = goldarSelected.value, kwn = kwnSelected.value, agm = agmSelected.value))
        }) }
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp, 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            LaunchedEffect (state.etcData?.siswa_warganegara){
                if (state.etcData?.siswa_warganegara.toString().isNotEmpty() && state.etcData?.siswa_warganegara.toString() != "null"){
                    kwnSelected.value = state.etcData?.siswa_warganegara.toString()
                }else{
                    kwnSelected.value = "-"
                }
            }

            LaunchedEffect (state.etcData?.siswa_gol_darah){
               if (state.etcData?.siswa_gol_darah.toString().isNotEmpty() && state.etcData?.siswa_gol_darah.toString() != "null"){
                    goldarSelected.value = state.etcData?.siswa_gol_darah.toString()
                }else{
                    goldarSelected.value = "-"
                }
            }

            LaunchedEffect (state.etcData?.siswa_agama){
                if (state.religionRaw.isNotEmpty()){
                    state.etcData?.siswa_agama?.let {selected ->
                        val agm = state.religionRaw.find { it.agm_id == selected }
                        agm?.agm_nama?.let {
                            agmSelected.value = it
                        }
                    }
                }
            }

            LaunchedEffect (state.text){
                if (state.text != null && state.text != ""){
                    Toast.makeText(context, state.text, Toast.LENGTH_SHORT).show()
                    onEvent(EtcEvent.OnClearText)
                }
            }

            LaunchedEffect (state.success){
                if (state.success != null && state.success != ""){
                    Toast.makeText(context, state.success, Toast.LENGTH_SHORT).show()
                    onEvent(EtcEvent.OnClearText)
                    navigateUp()
                }
            }

            SelectOption(modifier = Modifier.fillMaxWidth(), label = "Golongan Darah", state = goldarSelected, options = goldarOption)
            SelectOption(modifier = Modifier.fillMaxWidth(), label = "Kewarganegaraan", state = kwnSelected, options = kwnOption)
            SelectOption(modifier = Modifier.fillMaxWidth(), label = "Agama", state = agmSelected, options = agmOption)

        }

         if (state.isLoading){
             SimpleLoadingScreen()
         }
    }
}