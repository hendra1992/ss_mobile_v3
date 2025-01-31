package com.softwaresekolah.inosoft.presentation.settings.changePassword

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.globalsnackbarscompose.SnackbarAction
import com.plcoding.globalsnackbarscompose.SnackbarController
import com.plcoding.globalsnackbarscompose.SnackbarEvent
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.softwaresekolah.inosoft.data.core.mapper.ProfileErrorEnvelopeMapper
import com.softwaresekolah.inosoft.data.settings.requests.ChangePasswordRequestBody
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.settings.usecase.ChangePasswordUseCase
import com.softwaresekolah.inosoft.util.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val application: Application,
    private val localManager: LocalManager,
    private val changePasswordUseCase: ChangePasswordUseCase,
): ViewModel() {

    private val _state = mutableStateOf(ChangePasswordState())
    val state: State<ChangePasswordState> = _state
    val networkMonitor = NetworkMonitor(application)


    val isConnected = networkMonitor.isNetworkAvailable()

    fun onEvent(event: ChangePasswordEvent){
        when(event){
            ChangePasswordEvent.OnClearError -> {
                onClearError()
            }
            ChangePasswordEvent.OnClearText -> {
                onClearText()
            }
            is ChangePasswordEvent.OnSubmit -> {
                changePassword(currentPass = event.currentPass, newPass = event.newPass, confNewPass = event.confNewPass)
            }
        }
    }

    private fun showSnackbar(text: String, actionText: String = "Retry", action: () -> Unit) {
        viewModelScope.launch {
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = text,
                    action = SnackbarAction(
                        name = actionText,
                        action = action
                    )
                )
            )
        }
    }

     private fun changePassword(
         currentPass: String,
         newPass: String,
         confNewPass: String,
    ){
         val studentId = runBlocking {
            localManager.getIdSiswa()
         }
         val departementId = runBlocking {
            localManager.getIdDep()
         }
         onClearError()
         
         val requestBody = ChangePasswordRequestBody(
             id_siswa = studentId.toString(),
             id_dep = departementId.toString(),
             old_password = currentPass,
             new_password = newPass,
             confirm_new_password = confNewPass,
         )

         if (!isConnected){
                _state.value = _state.value.copy(isLoading = false)
                showSnackbar("No Internet Connection", action = { changePassword(
                    currentPass = currentPass,
                    newPass = newPass,
                    confNewPass = confNewPass
                ) })

            }

        viewModelScope.launch{
            _state.value = _state.value.copy(isLoading = true)

            val response = changePasswordUseCase(requestBody)
            response.onSuccess {
                _state.value = _state.value.copy(isLoading = false, success = data.messages)
            }.onError(ProfileErrorEnvelopeMapper) {
                if (this.body.errors.isNotEmpty()){
                    this.body.errors.forEach {
                        if (it.field == "old_password"){
                            _state.value = _state.value.copy(isLoading = false, currentPassIsError = true, currentPassErrorText = it.client_message)
                        }else if (it.field == "new_password"){
                            _state.value = _state.value.copy(isLoading = false, newPassIsError = true, newPassErrorText = it.client_message)
                        }else if (it.field == "confirm_new_password"){
                            _state.value = _state.value.copy(isLoading = false, confNewPassIsError = true, confNewPassErrorText = it.client_message)
                        }else if(it.field == "id_dep"){
                            _state.value = _state.value.copy(isLoading = false, text = it.client_message)
                        }
                    }

                }
            }.onException {
                _state.value = _state.value.copy(isLoading = false, text = message)
            }.onFailure {
                val message: String = message()
                Timber.tag("Parent View Model").d(message)
            }
        }
    }

    private fun onClearText(){
         _state.value = _state.value.copy(isLoading = false, text = null, success = null)
    }

    private fun onClearError(){
        _state.value = _state.value.copy(
            currentPassIsError = false,
            currentPassErrorText = null,
            newPassIsError = false,
            newPassErrorText = null,
            confNewPassIsError = false,
            confNewPassErrorText = null,
        )
    }
}