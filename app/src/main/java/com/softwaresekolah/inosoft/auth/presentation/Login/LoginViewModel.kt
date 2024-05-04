package com.softwaresekolah.inosoft.auth.presentation.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwaresekolah.inosoft.auth.domain.usecase.SaveUserLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(
    private val saveUserLogin: SaveUserLogin
): ViewModel() {
    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.onClickLogin ->{
                login()
            }
        }
    }

    private fun login(){
        viewModelScope.launch {
            saveUserLogin()
        }
    }
}