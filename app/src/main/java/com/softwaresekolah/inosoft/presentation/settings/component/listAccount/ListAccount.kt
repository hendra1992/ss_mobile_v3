package com.softwaresekolah.inosoft.presentation.settings.component.listAccount

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.softwaresekolah.inosoft.presentation.settings.component.LogoutDialog
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAccount(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: ListAccountState,
    onEvent: (ListAccountEvent) -> Unit,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val height = configuration.screenHeightDp * 0.8

    val currentSelectedItem = remember { mutableStateOf(User("", "", "", "", "", "", "", "", "", "",)) }





    LazyColumn(modifier = modifier.height(height.dp)) {
        itemsIndexed(state.users){ _, item ->
            val isCancel = remember {
                mutableStateOf(false)
            }

            val showDialog = remember {
                mutableStateOf(false)
            }

            if (showDialog.value){
                LogoutDialog(
                    logout = {
                        onEvent(ListAccountEvent.OnLogout(item.idSiswa))
                    },
                    isLogoutDialogShow = showDialog,
                    user = item,
                    cancel = {
                        isCancel.value = true
                    }
                )
            }
            SwipeToDeleteContainer(item = item, onDelete = {
                currentSelectedItem.value = it
                showDialog.value = true
            }, isCancel = isCancel, content = { user ->
                AccountItem(item = user, currentAcc = state.currentUser) {
                    onEvent(ListAccountEvent.OnSwitchAccount(user))
                }
            })
        }
    }

    LaunchedEffect(state.text) {
        state.text?.let {
            Toast.makeText( context, it, Toast.LENGTH_SHORT).show()
            onEvent(ListAccountEvent.OnClearText)
            onEvent(ListAccountEvent.OnUpdate)
            if (state.text.contains("beralih", ignoreCase = true)){
                navController.navigate(Route.HomeScreen.route){
                    popUpTo(0)
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit,
    isCancel: MutableState<Boolean>
    ) {

    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
    )



    LaunchedEffect(isCancel.value) {
        if (isCancel.value){
            isCancel.value = false
            state.snapTo(SwipeToDismissBoxValue.Settled)
        }
    }

    LaunchedEffect(key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackground(swipeDismissState = state)
            },
            content = { content(item) },
            enableDismissFromEndToStart = true,
            enableDismissFromStartToEnd = false
        )
    }

    when(state.currentValue){
        SwipeToDismissBoxValue.Settled->{
            isRemoved = false
        }
        SwipeToDismissBoxValue.StartToEnd -> {

        }
        SwipeToDismissBoxValue.EndToStart -> {
            if (!isRemoved){
                onDelete(item)
            }
            isRemoved = true
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    val color = if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        Color.Red
    } else Color.Transparent

   Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = color,
        ),
    ) {
       Box(modifier = Modifier
           .fillMaxSize()
           .padding(8.dp), contentAlignment = Alignment.CenterEnd){
           Row (verticalAlignment = Alignment.CenterVertically){
                Text(modifier = Modifier.padding(), text = "Logout", color = Color.White, style = MaterialTheme.typography.titleMedium)
                Icon(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = null,
                    tint = Color.White
                )
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