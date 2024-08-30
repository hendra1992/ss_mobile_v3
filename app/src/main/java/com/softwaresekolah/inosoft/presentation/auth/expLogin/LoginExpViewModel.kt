package com.softwaresekolah.inosoft.presentation.auth.expLogin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import com.softwaresekolah.inosoft.data.auth.request.LoginRequestBody
import com.softwaresekolah.inosoft.data.core.mapper.ErrorEnvelopeMapper
import com.softwaresekolah.inosoft.domain.auth.usecase.LoginUseCase
import com.softwaresekolah.inosoft.domain.auth.usecase.SaveUserLogin
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.core.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginExpViewModel @Inject constructor(
    private val saveUserLogin: SaveUserLogin,
    private val loginUseCase: LoginUseCase,
    private val userRepository: UserRepository,
    private val localManager: LocalManager
): ViewModel() {

    private var _state = mutableStateOf(LoginExpState())
    val state: State<LoginExpState> = _state

    init {
        val idSiswa = runBlocking {
            localManager.getIdSiswa()
        }
        _state.value = _state.value.copy(error = "Akses Telah Kedaluwarsa, Silahkan Login Kembali")
        viewModelScope.launch {
            val user = userRepository.getUser(idSiswa.toString())
            _state.value = _state.value.copy(user = user)
        }
    }
    fun onEvent(event: LoginExpEvent){
        when(event){
            is LoginExpEvent.OnClickLogin ->{
                login(body = event.loginBody, depkode = event.depkode)
            }
            is LoginExpEvent.OnClearError -> {
                clearError()
            }
            else->{

            }
        }
    }

    private fun clearError(){
        _state.value = _state.value.copy(isLoading = false, error = null, isSuccess = false)
    }
    private fun login(
        depkode: String,
        body: LoginRequestBody,
    ){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val response = loginUseCase(depkode = depkode, body = body)
            response.suspendOnSuccess {
                data.data?.let {
                    runBlocking {
                        saveUserLogin(depkode = depkode,data = it, token = body.device_token, username = body.siswa_username)
                        _state.value = _state.value.copy(isLoading = false, isSuccess = true)
                    }
                }
            }.onError(ErrorEnvelopeMapper) {
                    val code = this.code
                    val message = this.message
                    val errorMessage = this.body.messages
                _state.value = _state.value.copy(isLoading = false)
                _state.value = _state.value.copy(error = errorMessage)
//                Timber.tag("ONERROR").d("code : $code error : $message body : $errorMessage");
            }.onException {
                _state.value = _state.value.copy(isLoading = false)
                _state.value = _state.value.copy(error = message)
//                    Timber.tag("ON EXCEPTION").d(message)
            }
        }
    }
}