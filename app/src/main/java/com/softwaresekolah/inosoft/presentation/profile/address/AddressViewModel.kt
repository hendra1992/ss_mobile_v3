package com.softwaresekolah.inosoft.presentation.profile.address

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
import com.softwaresekolah.inosoft.data.profile.request.AddressBodyRequest
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.profile.usecase.address.GetAddressDataUseCase
import com.softwaresekolah.inosoft.domain.profile.usecase.address.GetCityUseCase
import com.softwaresekolah.inosoft.domain.profile.usecase.address.GetProvinceUseCase
import com.softwaresekolah.inosoft.domain.profile.usecase.address.SaveAddressDataUseCase
import com.softwaresekolah.inosoft.util.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.multibindings.IntoMap
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val application: Application,
    private val getProvinceUseCase: GetProvinceUseCase,
    private val getCityUseCase: GetCityUseCase,
    private val getAddressDataUseCase: GetAddressDataUseCase,
    private val saveAddressDataUseCase: SaveAddressDataUseCase,
    private val localManager: LocalManager,
    private val connectivityObserver: ConnectivityObserver
): ViewModel() {
    private val _state = mutableStateOf(AddressState())
    val state: State<AddressState> = _state
    val networkMonitor = NetworkMonitor(application)


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
    fun onEvent(event: AddressEvent){
        when(event){
            is AddressEvent.OnClearError -> {
                onClearError()
            }
            is AddressEvent.OnClearText -> {
                onClearText()
            }
            is AddressEvent.OnSave -> {
                saveData(
                    address = event.address,
                    telephone = event.telephone,
                    provinceId = event.provinceId,
                    cityId = event.cityId,
                    postalCode = event.postalCode,
                )
            }
            is AddressEvent.OnUpdate -> {

            }

            is AddressEvent.OnProvinceChanges -> {
                viewModelScope.launch {
                    if (networkMonitor.isNetworkAvailable()) {
                        getCities(event.provinceId)
                    } else {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            text = "no internet connection! coba lagi nanti"
                        )
                    }
                }
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
        getProvinceUseCase().onEach {
            val provinces = mutableListOf<String>()
            it.forEach {
                provinces.add(it.prop_nama)
            }
            _state.value = _state.value.copy(provinces = provinces, provinceRaw = it)
        }.launchIn(viewModelScope)

        getAddressDataUseCase(studentId = studentId.toString(), departmentId = departmentId.toString()).onEach {
            if (it.siswa_propinsi != null){
                getCityUseCase(it.siswa_propinsi.toString()).onEach { citiesRaw ->
                    val cities = mutableListOf<String>()
                    citiesRaw.forEach { city->
                        cities.add(city.kota_nama)
                    }
                    _state.value = _state.value.copy(isLoading = false, cityRaw = citiesRaw, cities = cities)
                }.launchIn(viewModelScope)
            }
            _state.value = _state.value.copy(addressData = it)
        }.launchIn(viewModelScope)
    }

    private fun onClearText(){
        _state.value = _state.value.copy(
            text =  null,
            success = null
        )
    }

    private suspend fun getCities(provinceId: Int){
        getCityUseCase(provinceId.toString()).onEach { citiesRaw ->
            val cities = mutableListOf<String>()
            citiesRaw.forEach { city ->
                cities.add(city.kota_nama)
            }
            _state.value = _state.value.copy(isLoading = false, cityRaw = citiesRaw, cities = cities)
        }.launchIn(viewModelScope)
    }

    private fun saveData(
        address: String,
        provinceId: Int,
        cityId: Int,
        postalCode: String,
        telephone: String,
    ){
        onClearError()
         if (!isConnected.value){
                _state.value = _state.value.copy(isLoading = false)
                showSnackbar("No Internet Connection", action = { saveData(
                    address, provinceId, cityId, postalCode, telephone
                ) })

            }
        val studentId = runBlocking {
            localManager.getIdSiswa()
        }

        val departmentId = runBlocking {
            localManager.getIdDep()
        }
        viewModelScope.launch{
            _state.value = _state.value.copy(isLoading = true)
            val body = AddressBodyRequest(
                id_dep = departmentId.toString(),
                id_siswa = studentId.toString(),
                siswa_alamat = address,
                siswa_propinsi = provinceId,
                siswa_kota = cityId,
                siswa_kodepos = postalCode,
                siswa_telp = telephone
            )

            val response = saveAddressDataUseCase(body)
            response.onSuccess {
                _state.value = _state.value.copy(isLoading = false, success = data.messages)
            }.onError(ProfileErrorEnvelopeMapper) {
                if (this.body.errors.isNotEmpty()){
                    this.body.errors.forEach {
                        if (it.field == "siswa_alamat"){
                            _state.value = _state.value.copy(isLoading = false, addressIsError = true, addressErrorText = it.client_message)
                        }else if (it.field == "siswa_propinsi"){
                            _state.value = _state.value.copy(isLoading = false, provinceIdIsError = true, provinceIdErrorText = it.client_message)
                        }else if (it.field == "siswa_kota"){
                            _state.value = _state.value.copy(isLoading = false, cityIdIsError = true, cityIdErrorText = it.client_message)
                        }else if (it.field == "siswa_kodepos"){
                            _state.value = _state.value.copy(isLoading = false, postalCodeIsError = true, postalCodeErrorText = it.client_message)
                        }else if (it.field == "siswa_telp"){
                            _state.value = _state.value.copy(isLoading = false, telephoneIsError = true, telephoneErrorText = it.client_message)
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

    private fun onClearError(){
        _state.value = _state.value.copy(
            cityIdIsError = false,
            cityIdErrorText = null,
            telephoneIsError = false,
            telephoneErrorText = null,
            addressIsError = false,
            postalCodeErrorText = null,
            postalCodeIsError = false,
            addressErrorText = null,
            provinceIdIsError = false,
            provinceIdErrorText = null,
        )
    }

}