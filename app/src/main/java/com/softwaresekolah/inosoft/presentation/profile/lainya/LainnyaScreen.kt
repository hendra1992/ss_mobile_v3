package com.softwaresekolah.inosoft.presentation.profile.lainya

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.softwaresekolah.inosoft.presentation.profile.component.ProfileMenuTopBar
import com.softwaresekolah.inosoft.presentation.profile.component.SelectOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LainnyaScreen(
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

            val goldarOption = listOf("-", "A", "B", "AB", "O")
            val goldarSelected = remember{
                mutableStateOf(goldarOption[0])
            }
            val kwnOption = listOf("WNI", "WNA")
            val kwnSelected = remember{
                mutableStateOf(kwnOption[0])
            }

            val agmOption = listOf("Islam", "Kristen Protestan", "Kristen Katolik", "Hindu", "Budha", "Kepercayaan", "Lain-Lain")
            val agmSelected = remember{
                mutableStateOf(agmOption[0])
            }

            SelectOption(modifier = Modifier.fillMaxWidth(), label = "Golongan Darah", state = goldarSelected, options = goldarOption)
            SelectOption(modifier = Modifier.fillMaxWidth(), label = "Kewarganegaraan", state = kwnSelected, options = kwnOption)
            SelectOption(modifier = Modifier.fillMaxWidth(), label = "Agama", state = agmSelected, options = agmOption)

        }
    }
}