package com.softwaresekolah.inosoft.presentation.core.SsNavigator

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
import com.softwaresekolah.inosoft.data.core.mapper.ErrorEnvelopeMapper
import com.softwaresekolah.inosoft.data.notification.requests.DeleteBatchNotificationRequest
import com.softwaresekolah.inosoft.data.notification.responses.NotificationListResponse
import com.softwaresekolah.inosoft.domain.auth.usecase.ReadUserExp
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.core.repository.ConfigAppRepository
import com.softwaresekolah.inosoft.domain.core.repository.UserRepository
import com.softwaresekolah.inosoft.domain.notification.usecase.DeleteBatchNotificationUseCase
import com.softwaresekolah.inosoft.domain.notification.usecase.GetNotificationCountUseCase
import com.softwaresekolah.inosoft.presentation.core.navgraph.Route
import com.softwaresekolah.inosoft.util.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val localManager: LocalManager,
    private val application: Application,
    private val configAppRepository: ConfigAppRepository,
    private val userRepository: UserRepository,
    private val readAppExp: ReadUserExp,
) : ViewModel() {
    private val _state = mutableStateOf(SharedState())
    val state: State<SharedState> = _state
    private val networkMonitor = NetworkMonitor(application)

    private val _userExpCondition = readAppExp()
    val redirectTo = mutableStateOf("")

    fun onEvent(event: SharedViewModelEvent){
        when(event){
            is SharedViewModelEvent.UpdateData ->{
                onUpdate()
            }

//            is SharedViewModelEvent.DeleteBatchNotification -> {
//                onDeleteBatch(event.notificationIds)
//            }

            SharedViewModelEvent.OnCLearText -> {
                OnCleartext()
            }
        }
    }

    init {
        _userExpCondition.onEach {
          if (it){
              redirectTo.value = Route.LoginExpScreen.route
          }else{
              redirectTo.value = ""
          }
        }.launchIn(viewModelScope)
    }

    fun OnCleartext(){
        _state.value = _state.value.copy(isLoading = false, success = null)
    }

    private fun onUpdate(){
        viewModelScope.launch {
            val departmentId = localManager.getIdDep()
            val studentId = localManager.getIdSiswa()

            val users = runBlocking {
                userRepository.getUsers().first()
            }

//            if (networkMonitor.isNetworkAvailable()){
//                getNotificationCountUseCase(studentId = studentId.toString(), departmentId = departmentId.toString()).onEach {
//                _state.value = _state.value.copy(notificationUnread = it.count_notif_unread)
//                }.launchIn(viewModelScope)
//            }


//            Timber.tag("Shared View Model").d(users.toString())
        }
    }



    fun showSnackBar(text: String, actionText: String? = null, action: () -> Unit = {}) {
        viewModelScope.launch {
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = text,
                    action = if(actionText != null){
                        SnackbarAction(
                            name = actionText,
                            action = action
                        )
                    }else{
                        null
                    }
                )
            )
        }
    }


//    private fun onDeleteBatch(notificationIds: List<NotificationListResponse>){
//        viewModelScope.launch {
//            val departmentId = localManager.getIdDep()
//            val studentId = localManager.getIdSiswa()
//
//            val users = runBlocking {
//                userRepository.getUsers().first()
//            }
//            val ids = mutableListOf<String>()
//            notificationIds.forEach {
//                ids.add(it.notif_id)
//            }
//
//            val body = DeleteBatchNotificationRequest(
//                id_siswa = studentId.toString(),
//                id_dep = departmentId.toString(),
//                list_id_notif = ids
//            )
//
//            if (networkMonitor.isNetworkAvailable()) {
//                val response = deleteBatchNotificationUseCase(body = body)
//                response.onSuccess {
//                    _state.value = _state.value.copy(isLoading = false, success = data.messages)
//                    _state.value = _state.value.copy(isLoading = false, success = null)
//                    showSnackBar(data.messages)
//                }.onError(ErrorEnvelopeMapper) {
//                    val message = this.body.errors
//                    _state.value = _state.value.copy(isLoading = false)
//                    showSnackBar(message.toString())
//                }.onException {
//                    _state.value = _state.value.copy(isLoading = false)
//                    showSnackBar("connection lost")
//                }.onFailure {
//                    val message: String = message()
//                    Timber.tag("SharedViewModel").d(message)
//                }
//            }else{
//                showSnackBar("No Internet Connection", "Retry", action = {
//                    viewModelScope.launch {
//                        val response = deleteBatchNotificationUseCase(body = body)
//                        response.onSuccess {
//                            _state.value = _state.value.copy(isLoading = false, success = data.messages)
//                            _state.value = _state.value.copy(isLoading = false, success = null)
//                            showSnackBar(data.messages)
//                        }.onError(ErrorEnvelopeMapper) {
//                            val message = this.body.errors
//                            _state.value = _state.value.copy(isLoading = false)
//                            showSnackBar(message.toString())
//                        }.onException {
//                            _state.value = _state.value.copy(isLoading = false)
//                            showSnackBar("connection lost")
//                        }.onFailure {
//                            val message: String = message()
//                            Timber.tag("SharedViewModel").d(message)
//                        }
//                    }
//                })
//            }
//        }
//    }

}