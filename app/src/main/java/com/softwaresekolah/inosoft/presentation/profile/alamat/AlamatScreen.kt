package com.softwaresekolah.inosoft.presentation.profile.alamat

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileMenuTopBar
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileTextField
import com.softwaresekolah.inosoft.presentation.profile.component.SelectOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlamatScreen(
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
            val alamat = rememberSaveable { mutableStateOf("") }
            val provinsiOption = listOf("Jawa Timur", "Jawa Barat", "Jawa Tengah")
            val provinsiSelected = remember{
                mutableStateOf(provinsiOption[0])
            }
            val kotaOption = listOf("Surabaya", "Sidoarjo")
            val kotaSelected = remember{
                mutableStateOf(kotaOption[0])
            }
            val kodepos = rememberSaveable { mutableStateOf("") }
            val telpRumah = rememberSaveable { mutableStateOf("") }


            ProfileTextField(modifier = Modifier.fillMaxWidth(), title = "Alamat", state = alamat)
            SelectOption(modifier = Modifier.fillMaxWidth(), label = "Provinsi", state = provinsiSelected, options = provinsiOption)
            SelectOption(modifier = Modifier.fillMaxWidth(), label = "Kota", state = kotaSelected, options = kotaOption)
            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "Kode Pos",
                state = kodepos,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            ProfileTextField(
                modifier = Modifier.fillMaxWidth(),
                title = "No. Telepon Rumah",
                state = telpRumah,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

        }
    }
}