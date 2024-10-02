package com.softwaresekolah.inosoft.presentation.profile.address

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.softwaresekolah.inosoft.presentation.core.common.SimpleLoadingScreen
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileMenuTopBar
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileTextField
import com.softwaresekolah.inosoft.presentation.profile.component.SelectOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(
    navigateUp: () -> Unit,
    state: AddressState,
    onEvent: (AddressEvent) -> Unit,
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val alamat = rememberSaveable { mutableStateOf("") }
    val provinsiSelected = remember{
        mutableStateOf("")
    }
    val kotaSelected = remember{
        mutableStateOf("")
    }
    val kodepos = rememberSaveable { mutableStateOf("") }
    val telpRumah = rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = { ProfileMenuTopBar(
            title = "Data Diri",
            scrollBehavior = scrollBehavior,
            navigateUp = navigateUp,
            actionOnClick = {
                var provinceId: Int = 0
                val province = state.provinceRaw.find { it.prop_nama == provinsiSelected.value }
                province?.let {
                    provinceId = it.prop_id
                }
                var cityId: Int = 0
                val city = state.cityRaw.find { it.kota_nama == kotaSelected.value }
                city?.let {
                    cityId = it.kota_id
                }
                onEvent(AddressEvent.OnSave(
                    address = alamat.value,
                    postalCode = kodepos.value,
                    cityId = cityId,
                    provinceId = provinceId,
                    telephone = telpRumah.value
                ))
            }
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

            LaunchedEffect (state.text){
                if (state.text != null && state.text != ""){
                    Toast.makeText(context, state.text, Toast.LENGTH_SHORT).show()
                    onEvent(AddressEvent.OnClearText)
                    if (state.text.toString().contains("successfully")){
                        navigateUp()
                    }
                }
            }

            LaunchedEffect(state.addressData) {
                if (state.addressData?.siswa_alamat != null ){
                    alamat.value = state.addressData?.siswa_alamat.toString()
                }

                if (state.addressData?.siswa_kodepos != null ){
                    kodepos.value = state.addressData?.siswa_kodepos.toString()
                }

                if (state.addressData?.siswa_telp != null ){
                    telpRumah.value = state.addressData?.siswa_telp.toString()
                }

                if (state.addressData?.siswa_propinsi != null ){
                    val province = state.provinceRaw.find { it.prop_id == state.addressData?.siswa_propinsi }
                    province?.let {
                        provinsiSelected.value = it.prop_nama
                    }
                }

                 if (state.addressData?.siswa_kota != null ){
                    val province = state.cityRaw.find { it.kota_id == state.addressData?.siswa_kota }
                    province?.let {
                        kotaSelected.value = it.kota_nama
                    }
                }
            }

            LaunchedEffect(provinsiSelected.value) {
                if (provinsiSelected.value != ""){
                    val province = state.provinceRaw.find { it.prop_nama == provinsiSelected.value }
                    province?.let {
                        if (state.addressData?.siswa_propinsi != it.prop_id){
                            kotaSelected.value = ""
                        }
                        onEvent(AddressEvent.OnProvinceChanges(it.prop_id))
                    }
                }
            }

            ProfileTextField(modifier = Modifier.fillMaxWidth(), title = "Alamat", state = alamat, isError = state.addressIsError, supText = state.addressErrorText)
            SelectOption(modifier = Modifier.fillMaxWidth(), label = "Provinsi", state = provinsiSelected, options = state.provinces)
            SelectOption(modifier = Modifier.fillMaxWidth(), label = "Kota", state = kotaSelected, options = state.cities)
            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "Kode Pos",
                state = kodepos,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.postalCodeIsError, supText = state.postalCodeErrorText
            )
            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "No. Telepon Rumah",
                state = telpRumah,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.telephoneIsError, supText = state.telephoneErrorText
            )
        }

        if (state.isLoading){
             SimpleLoadingScreen()
         }
    }
}