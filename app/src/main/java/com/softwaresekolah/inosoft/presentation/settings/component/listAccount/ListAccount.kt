package com.softwaresekolah.inosoft.presentation.settings.component.listAccount

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.softwaresekolah.inosoft.domain.core.models.User
import com.softwaresekolah.inosoft.presentation.core.common.SoftwareSekolahButton
import com.softwaresekolah.inosoft.presentation.core.navgraph.Route

@Composable
fun ListAccount(
    modifier: Modifier = Modifier,
    state: ListAccountState,
    navController: NavController,
    onEvent: (ListAccountEvent) -> Unit,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp * 0.8
    LazyColumn(modifier = modifier.height(height.dp)) {
        itemsIndexed(state.users){ index, item ->
            AccountItem(item = item, currentAcc = state.currentUser) {
                onEvent(ListAccountEvent.OnSwitchAccount(item))
            }
        }
    }

    SideEffect {
        onEvent(ListAccountEvent.OnUpdate)
    }

    LaunchedEffect(state.text) {
        state.text?.let {
            Toast.makeText( context, it, Toast.LENGTH_SHORT).show()
            onEvent(ListAccountEvent.OnClearText)
            navController.navigate(Route.HomeScreen.route){
                popUpTo(0)
            }
        }
    }
}

@Composable
fun AccountItem(modifier: Modifier = Modifier, item: User,currentAcc: String, onClick: () -> Unit) {
    val context = LocalContext.current

    Card(
        onClick = {
            if (currentAcc == item.idSiswa){
                Toast.makeText(context, "Anda Sedang Menggunakan Akun Ini", Toast.LENGTH_SHORT).show()
            }else{
                onClick()
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = CircleShape)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    if(item.imageUrl.isNotEmpty()){
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = null,
                            modifier.clip(CircleShape)
                        )
                    }else{
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "",
                            tint = Color.Unspecified,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = item.siswaNama,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            if (currentAcc == item.idSiswa){
                SoftwareSekolahButton(text = "active", onClick = {})
            }
        }
    }
}