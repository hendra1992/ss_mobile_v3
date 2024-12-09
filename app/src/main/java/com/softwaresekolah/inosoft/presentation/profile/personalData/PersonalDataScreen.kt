package com.softwaresekolah.inosoft.presentation.profile.personalData

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.softwaresekolah.inosoft.presentation.core.common.SimpleLoadingScreen
import com.softwaresekolah.inosoft.presentation.profile.component.DateTextField
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileMenuTopBar
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileTextField
import com.softwaresekolah.inosoft.presentation.profile.component.SelectOption
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataScreen(
    navigateUp: () -> Unit,
    state: PersonalDataState,
    onEvent: (PersonalDataEvent) -> Unit,
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val formatterDisplay = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val formatterApi = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val selectedDates = remember { mutableStateOf<LocalDate>(LocalDate.now()) }
    val calendarState = rememberUseCaseState()
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
            ),
        selection = CalendarSelection.Date { newDates ->
            selectedDates.value = newDates
        }
    )
    val namaPanggilan = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val noAkta = rememberSaveable { mutableStateOf("") }
    val tempatLahir = rememberSaveable { mutableStateOf("") }
    val telp = rememberSaveable { mutableStateOf("") }
    val wa = rememberSaveable { mutableStateOf("") }
    val genderOption = listOf("Laki-Laki", "Perempuan")
    val genderSelected = remember{
        mutableStateOf("")
    }
    Scaffold(
        topBar = { ProfileMenuTopBar(title = "Data Diri", scrollBehavior = scrollBehavior, navigateUp = navigateUp, actionOnClick = {
            onEvent(PersonalDataEvent.OnSave(
                nickname = namaPanggilan.value,
                email = email.value,
                birthCertificateNumber = noAkta.value,
                hp = telp.value,
                gender = if (genderSelected.value == "Laki-Laki") "L" else "P",
                birthDate = selectedDates.value.format(formatterApi),
                birthPlace = tempatLahir.value,
                wa = wa.value
            ))
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


            LaunchedEffect (state.personalData){
                when (state.personalData?.siswa_jenis_kelamin) {
                    "L" -> {
                        genderSelected.value = "Laki-Laki"
                    }
                    else -> {
                        genderSelected.value = "Perempuan"
                    }
                }

                if (state.personalData?.siswa_no_whatsapp != null ){
                    wa.value = state.personalData?.siswa_no_whatsapp.toString()
                }

                if (state.personalData?.siswa_hp != null ){
                    telp.value = state.personalData?.siswa_hp.toString()
                }

                if (state.personalData?.siswa_tempat_lahir != null ){
                    tempatLahir.value = state.personalData?.siswa_tempat_lahir.toString()
                }

                if (state.personalData?.siswa_no_akta_lahir != null ){
                    noAkta.value = state.personalData?.siswa_no_akta_lahir.toString()
                }

                if (state.personalData?.siswa_email != null ){
                    email.value = state.personalData?.siswa_email.toString()
                }

                if (state.personalData?.siswa_nama_panggilan != null ){
                    namaPanggilan.value = state.personalData?.siswa_nama_panggilan.toString()
                }

                if (state.personalData?.siswa_tanggal_lahir != null){
                    selectedDates.value = LocalDate.parse(state.personalData?.siswa_tanggal_lahir.toString())
                }

            }

             LaunchedEffect (state.text){
                if (state.text != null && state.text != ""){
                    Toast.makeText(context, state.text, Toast.LENGTH_SHORT).show()
                    onEvent(PersonalDataEvent.OnClearText)
                }
            }

            LaunchedEffect (state.success){
                if (state.success != null && state.success != ""){
                    Toast.makeText(context, state.success, Toast.LENGTH_SHORT).show()
                    onEvent(PersonalDataEvent.OnClearText)
                    navigateUp()
                }
            }

            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "Nama Panggilan",
                state = namaPanggilan,
                isError = state.nicknameIsError,
                supText = state.nicknameErrorText
            )
            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "Email Siswa",
                state = email,
                isError = state.emailIsError,
                supText = state.emailErrorText
            )
            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "No. Akta kelahiran",
                state = noAkta,
                isError = state.birthCertificateNumberIsError,
                supText = state.birthCertificateNumberErrorText
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    ProfileTextField(
                        modifier = Modifier.width(140.dp),
                        title = "Tempat Lahir",
                        state = tempatLahir,
                        isError = state.birthPlaceIsError,
                        supText = state.birthPlaceErrorText
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DateTextField(modifier = Modifier.fillMaxWidth(), title = "Tanggal Lahir", value = selectedDates.value.format(formatterDisplay), onClick = {calendarState.show()})
                }
            }
            SelectOption(modifier = Modifier.fillMaxWidth(), label = "Jenis Kelamin", state = genderSelected, options = genderOption)

            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "No. Telepon",
                state = telp,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.hpIsError,
                supText = state.hpErrorText
            )
            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "No. Whatsapp",
                state = wa,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.waIsError,
                supText = state.waErrorText
            )
        }
    }

    if (state.isLoading){
        SimpleLoadingScreen()
    }

}