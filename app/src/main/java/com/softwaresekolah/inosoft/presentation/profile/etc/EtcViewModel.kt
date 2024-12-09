package com.softwaresekolah.inosoft.presentation.profile.etc

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.softwaresekolah.inosoft.domain.profile.usecase.etc.GetReligionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.globalsnackbarscompose.SnackbarAction
import com.plcoding.globalsnackbarscompose.SnackbarController
import com.plcoding.globalsnackbarscompose.SnackbarEvent
import com.plcoding.internetconnectionobserver.ConnectivityObserver
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.softwaresekolah.inosoft.data.core.mapper.ErrorEnvelopeMapper
import com.softwaresekolah.inosoft.data.core.mapper.ProfileErrorEnvelopeMapper
import com.softwaresekolah.inosoft.data.profile.request.EtcDataBodyRequest
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.profile.usecase.etc.GetEtcDataUseCase
import com.softwaresekolah.inosoft.domain.profile.usecase.etc.SaveEtcDataUseCase
import com.softwaresekolah.inosoft.util.NetworkMonitor
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber


@HiltViewModel
class EtcViewModel @Inject constructor(
    private val localManager: LocalManager,
    private val getReligionsUseCase: GetReligionsUseCase,
    private val getEtcDataUseCase: GetEtcDataUseCase,
    private val saveEtcDataUseCase: SaveEtcDataUseCase,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _state = mutableStateOf(EtcState())
    val state: State<EtcState> = _state


    val isConnected = connectivityObserver
        .isConnected
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            if (isConnected.value){
                loadData()
            }else{
                _state.value = _state.value.copy(isLoading = false)
                showSnackbar("No Internet Connection", action = {update()})
            }
        }
    }

     fun update(){
        Timber.tag("Personal View Model").d("test")
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            if (isConnected.value){
                loadData()
            }else{
                _state.value = _state.value.copy(isLoading = false)
                showSnackbar("No Internet Connection", action = {update()})
            }
        }

    }

    fun showSnackbar(text: String, actionText: String = "Retry", action: () -> Unit) {
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

    override fun onCleared() {
        super.onCleared()
        Timber.tag("ETC VIEWMODEL").d("on cleared")
    }
    fun onEvent(event: EtcEvent){
        when(event){
            is EtcEvent.OnUpdate -> {

            }

            is EtcEvent.OnSave -> {
                saveData(agm = event.agm, goldar = event.goldar, kwn = event.kwn)
            }

            is EtcEvent.OnClearText -> {
                clearText()
            }
        }
    }

    private fun clearText(){
        _state.value = _state.value.copy(isLoading = false, text = null, success = null)
    }

    private suspend fun loadData(){
        val studentId = runBlocking {
            localManager.getIdSiswa()
        }

        val departmentId = runBlocking {
            localManager.getIdDep()
        }
        getReligionsUseCase().onEach {
            val religions = mutableListOf<String>()
            it.forEach {
                religions.add(it.agm_nama)
            }
            _state.value = _state.value.copy(isLoading = false, religions = religions, religionRaw = it)
        }.launchIn(viewModelScope)

        getEtcDataUseCase(studentId = studentId.toString(), departmentId = departmentId.toString()).onEach {
            _state.value = _state.value.copy(isLoading = false, etcData = it)
        }.launchIn(viewModelScope)
    }

    private fun saveData(goldar: String, kwn: String, agm: String){
            _state.value = _state.value.copy(isLoading = true)

            if (!isConnected.value){
                _state.value = _state.value.copy(isLoading = false)
                showSnackbar("No Internet Connection", action = { saveData(
                    goldar, kwn, agm
                ) })

            }

            val studentId = runBlocking {
                localManager.getIdSiswa()
            }
            val departmentId = runBlocking {
                localManager.getIdDep()
            }

            var agmId = 0
            val selectedAgm = _state.value.religionRaw.find { it.agm_nama == agm }

            selectedAgm?.agm_id?.let {
                agmId = it
            }

            val body = EtcDataBodyRequest(
                id_dep = departmentId.toString(),
                id_siswa = studentId.toString(),
                siswa_agama = agmId,
                siswa_gol_darah = goldar,
                siswa_warganegara = kwn,
            )
        viewModelScope.launch{

            val response = saveEtcDataUseCase(body)
            response.onSuccess {
                _state.value = _state.value.copy(isLoading = false, success = data.messages)
            }.onError(ProfileErrorEnvelopeMapper) {
                val field = this.body.errors[0].field
                val message = this.body.errors[0].message
                _state.value = _state.value.copy(isLoading = false, text = "field $field : $message")
            }.onException {
                _state.value = _state.value.copy(isLoading = false, text = message)
            }.onFailure {
                val message: String = message()
                Timber.tag("Etc View Model").d(message)
            }
        }
    }
}