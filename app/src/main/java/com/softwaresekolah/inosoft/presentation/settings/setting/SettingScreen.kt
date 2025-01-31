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
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.softwaresekolah.inosoft.data.settings.responses.GetSettingResponse
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
        GeneralOptionsUI(state, onEvent)
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
fun GeneralOptionsUI(state: SettingState, onEvent: (SettingEvent) -> Unit, ) {
    var checkedPengumuman = remember { mutableStateOf(false) }
    var checkedAbsensi = remember { mutableStateOf(false) }
    var checkedTelat = remember { mutableStateOf(false) }
    LaunchedEffect(state.settings) {
        if (state.settings?.siswa_notif_alpa != null ){
            checkedAbsensi.value = state.settings!!.siswa_notif_alpa
        }

        if (state.settings?.siswa_notif_bayar != null ){
            checkedPengumuman.value = state.settings!!.siswa_notif_bayar
        }

        if (state.settings?.siswa_notif_telat != null ){
            checkedTelat.value = state.settings!!.siswa_notif_telat
        }
    }

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
            onClick = {},
            onChange = {
                  onEvent(SettingEvent.OnSave(GetSettingResponse(
                    siswa_notif_bayar = checkedPengumuman.value,
                    siswa_notif_telat = checkedTelat.value,
                    siswa_notif_alpa = checkedAbsensi.value
                )))
            }
        )
        GeneralSettingItem(
            icon = Icons.Outlined.CalendarMonth,
            checked = checkedAbsensi,
            mainText = "Notifikasi Absensi",
            subText = "Atur Notifikasi Absensi",
            onClick = {},
            onChange = {
                  onEvent(SettingEvent.OnSave(GetSettingResponse(
                    siswa_notif_bayar = checkedPengumuman.value,
                    siswa_notif_telat = checkedTelat.value,
                    siswa_notif_alpa = checkedAbsensi.value
                )))
            }
        )
        GeneralSettingItem(
            icon = Icons.Outlined.Timer,
            checked = checkedTelat,
            mainText = "Notifikasi Siswa Telat",
            subText = "Atur Notifikasi Siswa Telat",
            onClick = {},
            onChange = {
                  onEvent(SettingEvent.OnSave(GetSettingResponse(
                    siswa_notif_bayar = checkedPengumuman.value,
                    siswa_notif_telat = checkedTelat.value,
                    siswa_notif_alpa = checkedAbsensi.value
                )))
            }
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
            icon = Icons.Outlined.Lock,
            mainText = "Ganti Password",
            onClick = { navigateTo(navController, Route.ChangePasswordScreen.route) }
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

