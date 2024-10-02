package com.softwaresekolah.inosoft.presentation.auth.expLogin

import android.widget.Toast
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.softwaresekolah.inosoft.R
import com.softwaresekolah.inosoft.util.isSmallScreenHeight
import com.softwaresekolah.inosoft.data.auth.request.LoginRequestBody
import com.softwaresekolah.inosoft.presentation.core.Dimens.MediumPadding1
import com.softwaresekolah.inosoft.presentation.core.SsNavigator.navigateTo
import com.softwaresekolah.inosoft.presentation.core.common.GradientBox
import com.softwaresekolah.inosoft.presentation.core.common.SimpleLoadingScreen
import com.softwaresekolah.inosoft.presentation.core.common.SoftwareSekolahButton
import com.softwaresekolah.inosoft.presentation.core.common.SoftwareSekolahTextButton
import com.softwaresekolah.inosoft.presentation.core.common.SsPasswordTextField
import com.softwaresekolah.inosoft.presentation.core.common.SsTextField
import com.softwaresekolah.inosoft.presentation.core.navgraph.Route
import com.softwaresekolah.inosoft.util.GetSoftwareID

@Composable
fun LoginExpScreen(
    onEvent: (LoginExpEvent) -> Unit,
    state: LoginExpState,
    navController: NavController? = null
) {
        val context = LocalContext.current
        val softwareId = GetSoftwareID().invoke(context = context)

        GradientBox(modifier = Modifier.fillMaxSize()) {
            LaunchedEffect(state.error) {
                state.error?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    onEvent(LoginExpEvent.OnClearError)
                }
            }

            LaunchedEffect(state.isSuccess) {
               if (state.isSuccess){
                   navController?.navigate(Route.HomeScreen.route){
                       popUpTo(0)
                   }
                   onEvent(LoginExpEvent.OnClearError)
               }
            }


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

                        kode_sekolah = state.user?.depkode.toString()
                        nim = state.user?.username.toString()

                        if (isSmallScreenHeight()){
                            Spacer(modifier = Modifier.fillMaxSize(0.05f))
                        }else{
                            Spacer(modifier = Modifier.fillMaxSize(0.1f))
                        }
                        SsTextField(
                            label = "Kode Sekolah",
                            value = kode_sekolah,
                            onValueChange = {kode_sekolah = it},
                            readOnly = true,
                            keyboardOptions = KeyboardOptions( imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = {focusManager.moveFocus(FocusDirection.Next)}
                            ),
                        )
                        Spacer(modifier = Modifier.height(MediumPadding1))
                        SsTextField(
                            label = "Nomor Induk Siswa",
                            value = nim,
                            onValueChange = {nim = it},
                            readOnly = true,
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
                            Column {
                                    SoftwareSekolahButton(
                                    text = "Login",
                                    onClick = { onEvent(LoginExpEvent.OnClickLogin(
                                        depkode = kode_sekolah,
                                        loginBody = LoginRequestBody(
                                            siswa_username = nim,
                                            siswa_password = password,
                                            device_token = "token",
                                            device_imei = softwareId.toString()
                                        )
                                    )) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(
                                            RoundedCornerShape(16.dp)
                                        ),
                                    containerColor = Color(0xFF0053D3),
                                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight(500))
                                )
                                SoftwareSekolahTextButton(modifier = Modifier.fillMaxWidth(), text = "Ganti Akun", color = MaterialTheme.colorScheme.primary, onClick = { navController?.let {
                                    navigateTo(it, Route.ListAccountScreen.route)
                                } })
                            }

                        }
                    }
                }

                if (state.isLoading){
                    SimpleLoadingScreen()
                }
        }
}