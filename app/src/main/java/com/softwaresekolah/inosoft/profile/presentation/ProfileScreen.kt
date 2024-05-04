package com.softwaresekolah.inosoft.profile.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.DevicesOther
import androidx.compose.material.icons.outlined.FamilyRestroom
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwaresekolah.inosoft.R
import com.softwaresekolah.inosoft.settings.presentation.SupportItem

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(8.dp)
                .height(138.dp)
                .width(138.dp)
                .clip(shape = CircleShape)
                .border(
                    BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary),
                    shape = CircleShape
                )
                .background(MaterialTheme.colorScheme.background),
            painter = painterResource(id = R.drawable.default_profile),
            contentDescription = "profile picture",
            contentScale = ContentScale.Crop
        )
        Text(modifier = Modifier.padding(vertical = 16.dp), text = "Nama Lengkap", style = MaterialTheme.typography.headlineMedium)

        Row {
            Text(
                text = "NIM: 04222001",
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.offset(y = (-4).dp)
            )
            Text(
                text = "|",
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.offset(y = (-4).dp).padding(horizontal = 8.dp)
            )
            Text(
                text = "NIS: 04222001",
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.offset(y = (-4).dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        SupportItem(
            modifier = Modifier.padding(horizontal = 16.dp),
            icon = Icons.Outlined.Person,
            mainText = "Data Diri",
            onClick = {}
        )
        SupportItem(
            modifier = Modifier.padding(horizontal = 16.dp),
            icon = Icons.Outlined.Home,
            mainText = "Alamat",
            onClick = {}
        )
        SupportItem(
            modifier = Modifier.padding(horizontal = 16.dp),
            icon = Icons.Outlined.FamilyRestroom,
            mainText = "Data Orang tua",
            onClick = {}
        )
        SupportItem(
            modifier = Modifier.padding(horizontal = 16.dp),
            icon = Icons.Outlined.Menu,
            mainText = "Lainnya",
            onClick = {}
        )
    }
}