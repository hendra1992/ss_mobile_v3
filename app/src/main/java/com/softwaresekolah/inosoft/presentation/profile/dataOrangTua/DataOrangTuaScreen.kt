package com.softwaresekolah.inosoft.presentation.profile.dataOrangTua

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileMenuTopBar
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataOrangTuaScreen(
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
            val namaAyah = rememberSaveable { mutableStateOf("") }

            val telpAyah = rememberSaveable { mutableStateOf("") }
            val namaIbu = rememberSaveable { mutableStateOf("") }

            val telpIbu = rememberSaveable { mutableStateOf("") }


            ProfileTextField(modifier = Modifier.fillMaxWidth(), title = "Nama Ayah", state = namaAyah)
            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "No. Telepon Ayah",
                state = telpAyah,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            ProfileTextField(modifier = Modifier.fillMaxWidth(), title = "Nama Ibu", state = namaAyah)
            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "No. Telepon Ibu",
                state = telpAyah,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

        }
    }
}