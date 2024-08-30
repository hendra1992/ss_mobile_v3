package com.softwaresekolah.inosoft.presentation.settings.setting

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwaresekolah.inosoft.domain.auth.usecase.LogoutUseCase
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    val logoutUseCase: LogoutUseCase,
    val localManager: LocalManager
): ViewModel() {

    private var _state = mutableStateOf(SettingState())
    val state: State<SettingState> = _state

    fun onEvent(event: SettingEvent){
        when(event){
            is SettingEvent.logout -> {
                logout()
            }
            is SettingEvent.resetState ->{
                clearSideEffect()
            }
        }
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