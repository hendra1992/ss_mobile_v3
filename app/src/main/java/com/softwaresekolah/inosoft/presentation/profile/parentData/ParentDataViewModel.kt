package com.softwaresekolah.inosoft.presentation.profile.parentData

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import com.softwaresekolah.inosoft.data.core.mapper.ProfileErrorEnvelopeMapper
import com.softwaresekolah.inosoft.data.profile.request.ParentDataBodyRequest
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.profile.usecase.parentData.GetParentDataUseCase
import com.softwaresekolah.inosoft.domain.profile.usecase.parentData.SaveParentDataUseCase
import com.softwaresekolah.inosoft.domain.profile.usecase.personalData.GetPersonalDataUseCase
import com.softwaresekolah.inosoft.domain.profile.usecase.personalData.SavePersonalDataUseCase
import com.softwaresekolah.inosoft.util.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ParentDataViewModel @Inject constructor(
    private val application: Application,
    private val getParentDataUseCase: GetParentDataUseCase,
    private val saveParentDataUseCase: SaveParentDataUseCase,
    private val localManager: LocalManager,
    private val connectivityObserver: ConnectivityObserver
): ViewModel() {
    private val _state = mutableStateOf(ParentDataState())
    val state: State<ParentDataState> = _state

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

    fun onEvent(event: ParentDataEvent){
        when(event){
            ParentDataEvent.OnClearError -> {
                onClearError()
            }
            ParentDataEvent.OnClearText -> {
                onClearText()
            }
            ParentDataEvent.OnUpdate -> {

            }

            is ParentDataEvent.OnSave -> {
                saveData(
                    dadName = event.dadName,
                    dadPhone = event.dadPhone,
                    momName = event.momName,
                    momPhone = event.momPhone
                )
            }
        }
    }

    private fun onClearText(){
        _state.value = _state.value.copy(
            text =  null,
            success =  null,
        )
    }

    private fun onClearError(){
        _state.value = _state.value.copy(
            dadNameIsError = false,
            dadPhoneIsError = false,
            momNameIsError = false,
            momPhoneIsError = false,
            dadNameErrorText = null,
            dadPhoneErrorText = null,
            momNameErrorText = null,
            momPhoneErrorText = null,
        )
    }

    private suspend fun loadData(){
          val studentId = runBlocking {
            localManager.getIdSiswa()
        }

        val departmentId = runBlocking {
            localManager.getIdDep()
        }

        getParentDataUseCase(studentId = studentId.toString(), departmentId = departmentId.toString()).onEach {
            _state.value = _state.value.copy(isLoading = false, parentData =  it)
        }.launchIn(viewModelScope)
    }

    private fun saveData(
        dadName: String,
        dadPhone: String,
        momName: String,
        momPhone: String,
    ){
        onClearError()
        if (!isConnected.value){
            _state.value = _state.value.copy(isLoading = false)
            showSnackbar("No Internet Connection", action = { saveData(
                dadName, dadPhone, momName, momPhone
            ) })
            return
        }
        val studentId = runBlocking {
            localManager.getIdSiswa()
        }

        val departmentId = runBlocking {
            localManager.getIdDep()
        }
        viewModelScope.launch{
            _state.value = _state.value.copy(isLoading = true)
            val body = ParentDataBodyRequest(
                id_dep = departmentId.toString(),
                id_siswa = studentId.toString(),
                ortu_ayah_nama = dadName,
                ortu_ayah_hp = dadPhone,
                ortu_ibu_nama = momName,
                ortu_ibu_hp = momPhone
            )

            val response = saveParentDataUseCase(body)
            response.onSuccess {
                _state.value = _state.value.copy(isLoading = false, success = data.messages)
            }.onError(ProfileErrorEnvelopeMapper) {
                if (this.body.errors.isNotEmpty()){
                    this.body.errors.forEach {
                        if (it.field == "ortu_ayah_nama"){
                            _state.value = _state.value.copy(isLoading = false, dadNameIsError = true, dadNameErrorText = it.client_message)
                        }else if (it.field == "ortu_ayah_hp"){
                            _state.value = _state.value.copy(isLoading = false, dadPhoneIsError = true, dadPhoneErrorText = it.client_message)
                        }else if (it.field == "ortu_ibu_nama"){
                            _state.value = _state.value.copy(isLoading = false, momNameIsError = true, momNameErrorText = it.client_message)
                        }else if (it.field == "ortu_ibu_hp"){
                            _state.value = _state.value.copy(isLoading = false, momPhoneIsError = true, momPhoneErrorText = it.client_message)
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
}