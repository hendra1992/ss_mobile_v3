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
        when(event) {
            is LoginEvent.OnClickLogin -> {
                login(body = event.loginBody, depkode = event.depkode)
            }

            is LoginEvent.OnClearError -> {
                clearError()
            }
        }
    }

    private fun validate(depkode: String, body: LoginRequestBody): String{
        if (depkode.isEmpty()){
            return "Kode Sekolah tidak boleh kosong"
        }
        if (body.siswa_username.isEmpty()){
            return "Nomor Induk Siswa tidak boleh kosong"
        }
        if(body.siswa_password.isEmpty()){
            return "Password tidak boleh kosong"
        }

        val user = runBlocking {
            userRepository.getUserByUsername(username = body.siswa_username)
        }
        if(user != null){
            return "Akun telah login di device ini"
        }

        return ""
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

            val errorValidate = validate(depkode, body)

            if(errorValidate.isNotEmpty()){
                _state.value = _state.value.copy(isLoading = false, error = errorValidate)
            }else{
                val response = loginUseCase(depkode = depkode, body = body)

                response.suspendOnSuccess {
                    data.data?.let {
                        runBlocking {
                            val users = userRepository.getUsers().first()

                            if (users.isNotEmpty()){
                                saveUserLogin(depkode = depkode,data = it, token = body.device_token, username = body.siswa_username)
                                _state.value = _state.value.copy(isLoading = false, isMultipleAcc = true)
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
                    if (code == 503){
                        _state.value = _state.value.copy(isLoading = false)
                        _state.value = _state.value.copy(error = "Kode Sekolah Salah / Tidak Ditemukan!")
                    }else{
                        _state.value = _state.value.copy(isLoading = false)
                        _state.value = _state.value.copy(error = errorMessage)
                    }
    //                Timber.tag("ONERROR").d("code : $code error : $message body : $errorMessage");
                }.onException {
                    _state.value = _state.value.copy(isLoading = false)
                    _state.value = _state.value.copy(error = message)
    //                    Timber.tag("ON EXCEPTION").d(message)
                }
            }

        }
    }
}