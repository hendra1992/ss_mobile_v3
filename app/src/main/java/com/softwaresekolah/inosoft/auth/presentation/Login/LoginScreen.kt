package com.softwaresekolah.inosoft.auth.presentation.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softwaresekolah.inosoft.R
import com.softwaresekolah.inosoft.boarding.presentation.boarding.BoardingEvent
import com.softwaresekolah.inosoft.core.Util.isSmallScreenHeight
import com.softwaresekolah.inosoft.core.presentation.Dimens.MediumPadding1
import com.softwaresekolah.inosoft.core.presentation.common.GradientBox
import com.softwaresekolah.inosoft.core.presentation.common.SoftwareSekolahButton
import com.softwaresekolah.inosoft.core.presentation.common.SsPasswordTextField
import com.softwaresekolah.inosoft.core.presentation.common.SsTextField

@Composable
fun LoginScreen(
    onEvent: (LoginEvent) -> Unit
) {
    GradientBox(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box (
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.35f),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.logo_login),
                    contentDescription = "logo",
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                        .background(Color.Transparent)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(Color.White)
                    .padding(horizontal = MediumPadding1),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var kode_sekolah by remember {
                    mutableStateOf("")
                }
                var nim by remember {
                    mutableStateOf("")
                }
                var password by remember {
                    mutableStateOf("")
                }
                val focusManager = LocalFocusManager.current


                if (isSmallScreenHeight()){
                    Spacer(modifier = Modifier.fillMaxSize(0.05f))
                }else{
                    Spacer(modifier = Modifier.fillMaxSize(0.1f))
                }
                SsTextField(
                    label = "Kode Sekolah",
                    value = kode_sekolah,
                    onValueChange = {kode_sekolah = it},
                    keyboardOptions = KeyboardOptions( imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {focusManager.moveFocus(FocusDirection.Next)}
                    ),
                )
                Spacer(modifier = Modifier.height(MediumPadding1))
                SsTextField(
                    label = "Nim",
                    value = nim,
                    onValueChange = {nim = it},
                    keyboardOptions = KeyboardOptions( imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {focusManager.moveFocus(FocusDirection.Next)}
                    ),
                )
                Spacer(modifier = Modifier.height(MediumPadding1))
                SsPasswordTextField(
                    label = "Password",
                    value = password,
                    onValueChange = {password = it},
                    keyboardOptions = KeyboardOptions( imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {focusManager.clearFocus()}
                    ),
                )
                Box (
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ){
                    SoftwareSekolahButton(
                        text = "Login",
                        onClick = { onEvent(LoginEvent.onClickLogin) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(16.dp)
                            ),
                        containerColor = Color(0xFF0053D3),
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight(500))
                    )
                }
            }
        }
    }
}