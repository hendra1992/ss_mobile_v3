package com.softwaresekolah.inosoft.presentation.settings.listAccount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.navigateTo
import com.softwaresekolah.inosoft.presentation.core.common.SoftwareSekolahButton
import com.softwaresekolah.inosoft.presentation.core.navgraph.Route
import com.softwaresekolah.inosoft.presentation.settings.component.SettingMenuTopbar
import com.softwaresekolah.inosoft.presentation.settings.component.listAccount.ListAccount
import com.softwaresekolah.inosoft.presentation.settings.component.listAccount.ListAccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAccountScreen(
    navController: NavController,
    navigateUp: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = { SettingMenuTopbar(title = "List Account", scrollBehavior = scrollBehavior, navigateUp = navigateUp) },
        bottomBar = {
                SoftwareSekolahButton(
                    text = "Tambah Akun",
                    onClick = { navigateTo(navController, Route.LoginScreen.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
        }
    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp, 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
                val viewModel: ListAccountViewModel = hiltViewModel()
                val state = viewModel.state.value
                ListAccount(navController = navController, state = state, onEvent = viewModel::onEvent)
        }
    }

}