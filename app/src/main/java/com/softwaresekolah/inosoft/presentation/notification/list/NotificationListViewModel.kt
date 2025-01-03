package com.softwaresekolah.inosoft.presentation.notification.list

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
import com.softwaresekolah.inosoft.data.notification.requests.DeleteAllReadBodyRequest
import com.softwaresekolah.inosoft.data.notification.requests.DeleteBatchNotificationRequest
import com.softwaresekolah.inosoft.data.notification.requests.ReadAllBodyRequest
import com.softwaresekolah.inosoft.data.notification.responses.NotificationListResponse
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.notification.usecase.DeleteAllReadNotificationUseCase
import com.softwaresekolah.inosoft.domain.notification.usecase.DeleteBatchNotificationUseCase
import com.softwaresekolah.inosoft.domain.notification.usecase.GetNotificationCountUseCase
import com.softwaresekolah.inosoft.domain.notification.usecase.GetNotificationListUseCase
import com.softwaresekolah.inosoft.domain.notification.usecase.ReadAllNotificationUseCase
import com.softwaresekolah.inosoft.util.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class NotificationListViewModel @Inject constructor(
    private val localManager: LocalManager,
    private val getNotificationListUseCase: GetNotificationListUseCase,
    private val getNotificationCountUseCase: GetNotificationCountUseCase,
    private val deleteBatchNotificationUseCase: DeleteBatchNotificationUseCase,
    private val readAllNotificationUseCase: ReadAllNotificationUseCase,
    private val deleteAllReadNotification: DeleteAllReadNotificationUseCase,
    private val application: Application
) : ViewModel() {
    private val _state = mutableStateOf(NotificationListState())
    val state: State<NotificationListState> = _state
    val networkMonitor = NetworkMonitor(application)

    val isConnected = networkMonitor.isNetworkAvailable()

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

    private fun update(){
         viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, notificationList = emptyList())
            Timber.tag("connection : ").d(isConnected.toString())
            if (isConnected){
                loadData()
            }else{
                _state.value = _state.value.copy(isLoading = false)
                showSnackBar("No Internet Connection", action = {update()})
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
        
        getNotificationListUseCase(studentId = studentId.toString(), departmentId = departmentId.toString()).onEach {
            _state.value = _state.value.copy(isLoading = false, notificationList = it)
        }.launchIn(viewModelScope)

        getNotificationCountUseCase(studentId = studentId.toString(), departmentId = departmentId.toString()).onEach {
            _state.value = _state.value.copy(notificationUnread = it.count_notif_unread)
        }.launchIn(viewModelScope)

    }

    fun onEvent(event: NotificationListEvent){
        when(event){
            NotificationListEvent.OnClearError -> TODO()
            NotificationListEvent.OnClearText -> {
                onClearText()
            }

            NotificationListEvent.OnUpdate -> {
                update()
            }

            is NotificationListEvent.DeleteBatchNotification -> {
                onDeleteBatch(event.notificationIds)
            }

            NotificationListEvent.OnDeleteAllRead -> {
                onDeleteAll()
            }
            NotificationListEvent.OnReadAll -> {
                onReadAll()
            }
        }
    }

   private fun onDeleteBatch(notificationIds: List<NotificationListResponse>){
        viewModelScope.launch {
            val departmentId = localManager.getIdDep()
            val studentId = localManager.getIdSiswa()

            val ids = mutableListOf<String>()
            notificationIds.forEach {
                ids.add(it.notif_id)
            }

            val body = DeleteBatchNotificationRequest(
                id_siswa = studentId.toString(),
                id_dep = departmentId.toString(),
                list_id_notif = ids
            )

            if (networkMonitor.isNetworkAvailable()) {
                val response = deleteBatchNotificationUseCase(body = body)
                response.onSuccess {
                    _state.value = _state.value.copy(isLoading = false, success = data.messages)
                }.onError(ErrorEnvelopeMapper) {
                    val message = this.body.errors
                    _state.value = _state.value.copy(isLoading = false)
                }.onException {
                    _state.value = _state.value.copy(isLoading = false)
                    showSnackBar("connection lost")
                }.onFailure {
                    val message: String = message()
                    Timber.tag("SharedViewModel").d(message)
                }
            }else{
                showSnackBar("No Internet Connection", "Retry", action = {
                    viewModelScope.launch {
                        val response = deleteBatchNotificationUseCase(body = body)
                        response.onSuccess {
                            _state.value = _state.value.copy(isLoading = false, success = data.messages)
                        }.onError(ErrorEnvelopeMapper) {
                            val message = this.body.errors
                            _state.value = _state.value.copy(isLoading = false, text = message)
                        }.onException {
                            _state.value = _state.value.copy(isLoading = false)
                            showSnackBar("connection lost")
                        }.onFailure {
                            val message: String = message()
                            Timber.tag("SharedViewModel").d(message)
                        }
                    }
                })
            }
        }
    }

    private fun onDeleteAll(){
        viewModelScope.launch {
            val departmentId = localManager.getIdDep()
            val studentId = localManager.getIdSiswa()

            val body = DeleteAllReadBodyRequest(
                id_siswa = studentId.toString(),
                id_dep = departmentId.toString(),
            )

            if (networkMonitor.isNetworkAvailable()) {
                val response = deleteAllReadNotification(body = body)
                response.onSuccess {
                    _state.value = _state.value.copy(isLoading = false, success = data.messages)
                }.onError(ErrorEnvelopeMapper) {
                    val message = this.body.errors
                    _state.value = _state.value.copy(isLoading = false)
                }.onException {
                    _state.value = _state.value.copy(isLoading = false)
                    showSnackBar("connection lost")
                }.onFailure {
                    val message: String = message()
                    Timber.tag("SharedViewModel").d(message)
                }
            }else{
                showSnackBar("No Internet Connection", "Retry", action = {
                    viewModelScope.launch {
                        val response = deleteAllReadNotification(body = body)
                        response.onSuccess {
                            _state.value = _state.value.copy(isLoading = false, success = data.messages)
                        }.onError(ErrorEnvelopeMapper) {
                            val message = this.body.errors
                            _state.value = _state.value.copy(isLoading = false, text = message)
                        }.onException {
                            _state.value = _state.value.copy(isLoading = false)
                            showSnackBar("connection lost")
                        }.onFailure {
                            val message: String = message()
                            Timber.tag("SharedViewModel").d(message)
                        }
                    }
                })
            }
        }
    }

    private fun onReadAll(){
        viewModelScope.launch {
            val departmentId = localManager.getIdDep()
            val studentId = localManager.getIdSiswa()

            val body = ReadAllBodyRequest(
                id_siswa = studentId.toString(),
                id_dep = departmentId.toString(),
            )

            if (networkMonitor.isNetworkAvailable()) {
                val response = readAllNotificationUseCase(body = body)
                response.onSuccess {
                    _state.value = _state.value.copy(isLoading = false, success = data.messages)
                }.onError(ErrorEnvelopeMapper) {
                    val message = this.body.errors
                    _state.value = _state.value.copy(isLoading = false)
                }.onException {
                    _state.value = _state.value.copy(isLoading = false)
                    showSnackBar("connection lost")
                }.onFailure {
                    val message: String = message()
                    Timber.tag("SharedViewModel").d(message)
                }
            }else{
                showSnackBar("No Internet Connection", "Retry", action = {
                    viewModelScope.launch {
                        val response = readAllNotificationUseCase(body = body)
                        response.onSuccess {
                            _state.value = _state.value.copy(isLoading = false, success = data.messages)
                        }.onError(ErrorEnvelopeMapper) {
                            val message = this.body.errors
                            _state.value = _state.value.copy(isLoading = false, text = message)
                        }.onException {
                            _state.value = _state.value.copy(isLoading = false)
                            showSnackBar("connection lost")
                        }.onFailure {
                            val message: String = message()
                            Timber.tag("SharedViewModel").d(message)
                        }
                    }
                })
            }
        }
    }


    private fun onClearText(){
         _state.value = _state.value.copy(isLoading = false, text = null, success = null)
    }

}