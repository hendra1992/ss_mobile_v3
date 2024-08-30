package com.softwaresekolah.inosoft.presentation.auth.Login

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
import com.softwaresekolah.inosoft.domain.core.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val saveUserLogin: SaveUserLogin,
    private val loginUseCase: LoginUseCase,
    private val userRepository: UserRepository
): ViewModel() {

    private var _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state
    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.OnClickLogin ->{
                login(body = event.loginBody, depkode = event.depkode)
            }
            is LoginEvent.OnClearError -> {
                clearError()
            }
        }
    }

    private fun clearError(){
        _state.value = _state.value.copy(isLoading = false, error = null, isMultipleAcc = false)
    }
    private fun login(
        depkode: String,
        body: LoginRequestBody,
    ){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val response = loginUseCase(depkode = depkode, body = body)
            val users = runBlocking {
                userRepository.getUsers().first()
            }
            response.suspendOnSuccess {
                data.data?.let {
                    runBlocking {
                        // ini harus di ganti dan tidak boleh tembak api
                        if(users.isNotEmpty()){
                            val user = userRepository.getUser(idSiswa = it.id_siswa)
                            if(user != null){
                                _state.value = _state.value.copy(isLoading = false, isMultipleAcc = false, error = "akun yang anda masukkan telah login di device ini")
                            }else {
                                _state.value = _state.value.copy(isLoading = false, isMultipleAcc = true)
                                saveUserLogin(depkode = depkode, data = it, token = body.device_token, username = body.siswa_username)
                            }
                        }else{
                            saveUserLogin(depkode = depkode,data = it, token = body.device_token, username = body.siswa_username)
                            _state.value = _state.value.copy(isLoading = false, isMultipleAcc = false)
                        }
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