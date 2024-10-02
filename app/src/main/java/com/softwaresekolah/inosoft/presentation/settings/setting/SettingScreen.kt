package com.softwaresekolah.inosoft.presentation.settings.setting

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.softwaresekolah.inosoft.R
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.navigateTo
import com.softwaresekolah.inosoft.presentation.core.navgraph.Route
import com.softwaresekolah.inosoft.presentation.settings.component.GeneralSettingItem
import com.softwaresekolah.inosoft.presentation.settings.component.LogoutDialog
import com.softwaresekolah.inosoft.presentation.settings.component.SupportItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onEvent: (SettingEvent) -> Unit,
    navController: NavController,
    state: SettingState,
    isAccountBottomSheetOpen: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
//        HeaderText()
        val context = LocalContext.current
        val isLogoutDilalogShow = remember {
            mutableStateOf(false)
        }

        if (isLogoutDilalogShow.value){
            LogoutDialog(logout = { onEvent(SettingEvent.logout) }, isLogoutDialogShow =  isLogoutDilalogShow)
        }

        LaunchedEffect(state.logoutSuccess) {
            if (state.logoutSuccess){
                Toast.makeText(context, "Logout Success", Toast.LENGTH_SHORT).show()
                navController.navigate(Route.HomeScreen.route){
                    popUpTo(0)
                }
            }
            onEvent(SettingEvent.resetState)
        }

        ProfileCardUI()
        GeneralOptionsUI()
        SupportOptionsUI(navController = navController, isLogoutDialogShow = isLogoutDilalogShow)
    }
}



@Composable
fun HeaderText() {
    Text(
        text = "Pengaturan",
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 10.dp),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp
    )
}

@Composable
fun ProfileCardUI() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
           horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    text = "Check Kelengkapan Data dirimu",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = "Pastikan data dirimu lengkap",
                    color = Color.Gray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Button(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = {},
                    contentPadding = PaddingValues(horizontal = 30.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Lihat",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.kelengkapan),
                contentDescription = "",
                modifier = Modifier.height(120.dp),
            )
        }
    }
}

@Composable
fun GeneralOptionsUI() {
    var checkedPengumuman = remember { mutableStateOf(true) }
    var checkedAbsensi = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = "Umum",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        GeneralSettingItem(
            icon = Icons.Outlined.Notifications,
            checked = checkedPengumuman,
            mainText = "Notifikasi Pengumuman Sekolah",
            subText = "Atur Notifikasi Pengumuman Sekolah",
            onClick = {}
        )
        GeneralSettingItem(
            icon = Icons.Outlined.CalendarMonth,
            checked = checkedAbsensi,
            mainText = "Notifikasi Absensi",
            subText = "Atur Notifikasi Absensi",
            onClick = {}
        )
//        GeneralSettingItem()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportOptionsUI(navController: NavController, isLogoutDialogShow: MutableState<Boolean>,) {
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = "Akun",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        SupportItem(
            icon = Icons.Outlined.Person,
            mainText = "Tambah / Ganti Akun",
            onClick = { navigateTo(navController, Route.ListAccountScreen.route) }
        )
        SupportItem(
            icon = Icons.Default.PowerSettingsNew,
            mainText = "Logout",
            onClick = {
                isLogoutDialogShow.value = true
            }
        )
//        SupportItem(
//            icon = R.drawable.ic_privacy_policy,
//            mainText = "Privacy Policy",
//            onClick = {}
//        )
//        SupportItem(
//            icon = R.drawable.ic_about,
//            mainText = "About",
//            onClick = {}
//        )
    }
}

