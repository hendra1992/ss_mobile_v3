package com.softwaresekolah.inosoft.presentation.settings.setting

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.globalsnackbarscompose.SnackbarAction
import com.plcoding.globalsnackbarscompose.SnackbarController
import com.plcoding.globalsnackbarscompose.SnackbarEvent
import com.softwaresekolah.inosoft.data.settings.requests.SaveSettingRequestBody
import com.softwaresekolah.inosoft.data.settings.responses.GetSettingResponse
import com.softwaresekolah.inosoft.domain.auth.usecase.LogoutUseCase
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.settings.usecase.GetSettingUseCase
import com.softwaresekolah.inosoft.domain.settings.usecase.SaveSettingUseCase
import com.softwaresekolah.inosoft.util.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    val logoutUseCase: LogoutUseCase,
    val getSettingUseCase: GetSettingUseCase,
    val saveSettingUseCase: SaveSettingUseCase,
    val localManager: LocalManager,
    val application: Application
): ViewModel() {

    private var _state = mutableStateOf(SettingState())
    val state: State<SettingState> = _state
    val networkMonitor = NetworkMonitor(application)

    val isConnected = networkMonitor.isNetworkAvailable()
    init {
        update()
    }

    private fun update(){
         viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            if (isConnected){
                loadData()
            }else{
                _state.value = _state.value.copy(isLoading = false)
                showSnackBar("No Internet Connection", action = {update()})
            }
        }
    }

    fun showSnackBar(text: String, actionText: String = "Retry", action: () -> Unit = {}) {
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

    private suspend fun loadData(){
         val studentId = runBlocking {
            localManager.getIdSiswa()
        }

        val departmentId = runBlocking {
            localManager.getIdDep()
        }

        getSettingUseCase(studentId = studentId.toString(), departmentId = departmentId.toString()).onEach {
            _state.value = _state.value.copy(isLoading = false, settings = it)
        }.launchIn(viewModelScope)

    }

    fun onEvent(event: SettingEvent){
        when(event){
            is SettingEvent.logout -> {
                logout()
            }
            is SettingEvent.resetState ->{
                clearSideEffect()
            }
            SettingEvent.OnClearText -> {
                onclearText()
            }
            SettingEvent.OnUpdate -> {
                update()
            }

            is SettingEvent.OnSave -> {
                saveSetting(event.setting)
            }
        }
    }

    private fun saveSetting(setting: GetSettingResponse){
        val studentId = runBlocking {
            localManager.getIdSiswa()
        }

        val departmentId = runBlocking {
            localManager.getIdDep()
        }

        val requestBody = SaveSettingRequestBody(
             id_siswa = studentId.toString(),
             id_dep = departmentId.toString(),
             siswa_notif_alpa = setting.siswa_notif_alpa,
             siswa_notif_bayar = setting.siswa_notif_bayar,
             siswa_notif_telat = setting.siswa_notif_telat
        )
         if (networkMonitor.isNetworkAvailable()){


            viewModelScope.launch {
                val body = saveSettingUseCase(requestBody)
                saveSettingUseCase(requestBody)
            }
        }else{
            showSnackBar("No Internet Connection", action = {
                viewModelScope.launch {
                    val body = saveSettingUseCase(requestBody)
                }
            })
        }
    }

    private fun onclearText(){
        _state.value = _state.value.copy(isLoading = false, text = null, success = null, logoutSuccess = false)
    }

    private  fun logout(){
        viewModelScope.launch {
            val idSiswa = runBlocking {
                localManager.getIdSiswa()
            }
            val logout = logoutUseCase(idSiswa.toString())
            _state.value = _state.value.copy(logoutSuccess = logout)
        }
    }

    private fun clearSideEffect(){
        _state.value =_state.value.copy(logoutSuccess = false)
    }
}