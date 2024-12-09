package com.softwaresekolah.inosoft.presentation.profile.personalData

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
import com.softwaresekolah.inosoft.data.profile.request.PersonalBodyRequest
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
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
class PersonalDataViewModel @Inject constructor(
    private val personalDataUseCase: GetPersonalDataUseCase,
    private val savePersonalDataUseCase: SavePersonalDataUseCase,
    private val localManager: LocalManager,
    private val connectivityObserver: ConnectivityObserver
): ViewModel() {
    private val _state = mutableStateOf(PersonalDataState())
    val state: State<PersonalDataState> = _state

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
                showSnackbar("No Internet Connection", action = { update() })
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

    fun onEvent(event: PersonalDataEvent){
        when(event){
            is PersonalDataEvent.OnClearText -> {
                onClearText()
            }
            is PersonalDataEvent.OnUpdate -> {

            }

            is PersonalDataEvent.OnSave -> {
                saveData(
                    nickname = event.nickname,
                    email = event.email,
                    birthPlace = event.birthPlace,
                    birthDate = event.birthDate,
                    gender = event.gender,
                    hp = event.hp,
                    wa = event.wa,
                    birthCertificateNumber = event.birthCertificateNumber,
                )
            }

            PersonalDataEvent.OnClearError -> {
                onClearError()
            }
        }
    }

    private suspend fun loadData(){
        val studentId = runBlocking {
            localManager.getIdSiswa()
        }

        val departmentId = runBlocking {
            localManager.getIdDep()
        }

        personalDataUseCase(studentId = studentId.toString(), departmentId = departmentId.toString()).onEach {
            _state.value = _state.value.copy(isLoading = false, personalData =  it)
        }.launchIn(viewModelScope)
    }

    private fun onClearError(){
        _state.value = _state.value.copy(
            nicknameIsError = false,
            emailIsError = false,
            birthCertificateNumberIsError = false,
            hpIsError = false,
            genderIsError = false,
            birthDateIsError = false,
            birthPlaceIsError = false,
            waIsError = false,
            nicknameErrorText = null,
            emailErrorText = null,
            birthCertificateNumberErrorText = null,
            hpErrorText = null,
            genderErrorText = null,
            birthDateErrorText = null,
            birthPlaceErrorText = null,
            waErrorText = null,
        )
    }

    private fun onClearText(){
        _state.value = _state.value.copy(
            text =  null,
            success =  null,
        )
    }

    private fun saveData(
        nickname: String,
        email: String,
        birthPlace: String,
        birthDate: String,
        gender: String,
        hp: String,
        wa: String,
        birthCertificateNumber: String,
    ){
        onClearError()
        if (!isConnected.value){
            _state.value = _state.value.copy(isLoading = false)
            showSnackbar("No Internet Connection", action = { saveData(
                nickname, email, birthPlace, birthDate, gender, hp, wa, birthCertificateNumber
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
            val body = PersonalBodyRequest(
                id_dep = departmentId.toString(),
                id_siswa = studentId.toString(),
                siswa_nama_panggilan = nickname,
                siswa_email = email,
                siswa_tempat_lahir = birthPlace,
                siswa_tanggal_lahir = birthDate,
                siswa_jenis_kelamin = gender,
                siswa_hp = hp,
                siswa_no_whatsapp = wa,
                siswa_no_akta_lahir = birthCertificateNumber
            )

            val response = savePersonalDataUseCase(body)
            response.onSuccess {
                _state.value = _state.value.copy(isLoading = false, success = data.messages)
            }.onError(ProfileErrorEnvelopeMapper) {
                if (this.body.errors.isNotEmpty()){
                    this.body.errors.forEach {
                        if (it.field == "siswa_nama_panggilan"){
                            _state.value = _state.value.copy(isLoading = false, nicknameIsError = true, nicknameErrorText = it.client_message)
                        }else if (it.field == "siswa_email"){
                            _state.value = _state.value.copy(isLoading = false, emailIsError = true, emailErrorText = it.client_message)
                        }else if (it.field == "siswa_tempat_lahir"){
                            _state.value = _state.value.copy(isLoading = false, birthPlaceIsError = true, birthPlaceErrorText = it.client_message)
                        }else if (it.field == "siswa_tanggal_lahir"){
                            _state.value = _state.value.copy(isLoading = false, birthDateIsError = true, birthDateErrorText = it.client_message)
                        }else if (it.field == "siswa_jenis_kelamin"){
                            _state.value = _state.value.copy(isLoading = false, genderIsError = true, genderErrorText = it.client_message)
                        }else if (it.field == "siswa_hp"){
                            _state.value = _state.value.copy(isLoading = false, hpIsError = true, hpErrorText = it.client_message)
                        }else if (it.field == "siswa_no_whatsapp"){
                            _state.value = _state.value.copy(isLoading = false, waIsError = true, waErrorText = it.client_message)
                        }else if (it.field == "siswa_no_akta_lahir"){
                            _state.value = _state.value.copy(isLoading = false, birthCertificateNumberIsError = true, birthCertificateNumberErrorText = it.client_message)
                        }
                    }
                }
            }.onException {
                _state.value = _state.value.copy(isLoading = false, text = message)
            }.onFailure {
                val message: String = message()
                Timber.tag("Personal View Model").d(message)
            }
        }
    }
}