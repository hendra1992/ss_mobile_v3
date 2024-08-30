package com.softwaresekolah.inosoft.presentation.profile.dataDiri

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.softwaresekolah.inosoft.presentation.profile.component.DateTextField
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileMenuTopBar
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileTextField
import com.softwaresekolah.inosoft.presentation.profile.component.SelectOption
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataDiriScreen(
    navigateUp: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = { ProfileMenuTopBar(title = "Data Diri", scrollBehavior = scrollBehavior, navigateUp = navigateUp) }
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp, 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
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

            val namaLengkap = rememberSaveable { mutableStateOf("") }
            val email = rememberSaveable { mutableStateOf("") }
            val noKK = rememberSaveable { mutableStateOf("") }
            val tempatLahir = rememberSaveable { mutableStateOf("") }
            val telp = rememberSaveable { mutableStateOf("") }
            val wa = rememberSaveable { mutableStateOf("") }
            val genderOption = listOf("Laki-Laki", "Perempuan")
            val genderSelected = remember{
                mutableStateOf(genderOption[0])
            }

            ProfileTextField(modifier = Modifier.fillMaxWidth(), title = "Nama Lengkap", state = namaLengkap)
            ProfileTextField(modifier = Modifier.fillMaxWidth(), title = "Email Siswa", state = email)
            ProfileTextField(modifier = Modifier.fillMaxWidth(), title = "No. Induk Kartu Keluarga", state = noKK)
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    ProfileTextField(modifier = Modifier.width(140.dp), title = "Tempat Lahir", state = tempatLahir)
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DateTextField(modifier = Modifier.fillMaxWidth(), title = "Tanggal Lahir", value = selectedDates.value.format(formatter), onClick = {calendarState.show()})
                }
            }
            SelectOption(modifier = Modifier.fillMaxWidth(), label = "Jenis Kelamin", state = genderSelected, options = genderOption)

            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "No. Telepon",
                state = telp,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "No. Whatsapp",
                state = wa,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }

}