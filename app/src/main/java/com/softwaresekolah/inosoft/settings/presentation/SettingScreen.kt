package com.softwaresekolah.inosoft.settings.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwaresekolah.inosoft.R




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(sheetState: SheetState, isAccountBottomSheetOpen: MutableState<Boolean>) {
    Column() {
//        HeaderText()
        ProfileCardUI()
        GeneralOptionsUI()
        SupportOptionsUI(sheetState, isAccountBottomSheetOpen)
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
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
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

@Composable
fun GeneralSettingItem(
    icon: ImageVector,
    mainText: String,
    subText: String,
    onClick: () -> Unit,
    checked: MutableState<Boolean>
) {
    Card(
        onClick = { onClick() },
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(Color.White)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.offset(y = (2).dp)
                ) {
                    Text(
                        text = mainText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = subText,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.offset(y = (-4).dp)
                    )
                }


            }

            Switch(
                checked = checked.value,
                onCheckedChange = {
                    checked.value = it
                },
                thumbContent = if (checked.value) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "",
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    null
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportOptionsUI(sheetState: SheetState, isAccountBottomSheetOpen: MutableState<Boolean>) {
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
            onClick = { isAccountBottomSheetOpen.value = true }
        )
        SupportItem(
            icon = Icons.Default.PowerSettingsNew,
            mainText = "Logout",
            onClick = {}
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

@Composable
fun SupportItem(modifier: Modifier = Modifier, icon: ImageVector, mainText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        modifier = modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = mainText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )

        }
    }
}