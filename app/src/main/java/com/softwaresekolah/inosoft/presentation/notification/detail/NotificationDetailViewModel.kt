package com.softwaresekolah.inosoft.presentation.notification.detail

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plcoding.globalsnackbarscompose.SnackbarAction
import com.plcoding.globalsnackbarscompose.SnackbarController
import com.plcoding.globalsnackbarscompose.SnackbarEvent
import com.softwaresekolah.inosoft.data.notification.requests.UpdateReadNotificationRequestBody
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.notification.usecase.ReadNotificationUseCase
import com.softwaresekolah.inosoft.util.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class NotificationDetailViewModel @Inject constructor(
    private val localManager: LocalManager,
    private val readNotificationUseCase: ReadNotificationUseCase,
    private val application: Application
) : ViewModel() {
    private val _state = mutableStateOf(NotificationDetailState())
    val state: State<NotificationDetailState> = _state
    private val networkMonitor = NetworkMonitor(application)

      fun onEvent(event: NotificationDetailEvent){
        when(event){
            is NotificationDetailEvent.OnRead -> {
               readNotification(event.notificationId)
            }
        }
    }

    private fun readNotification(id: String){
        if (networkMonitor.isNetworkAvailable()){
            val studentId = runBlocking {
                localManager.getIdSiswa()
            }

            val departmentId = runBlocking {
                localManager.getIdDep()
            }

            viewModelScope.launch {
                val body = UpdateReadNotificationRequestBody(id_dep = departmentId.toString(), id_siswa = studentId.toString(), list_id_notif = listOf(id))
                readNotificationUseCase(body)
            }
        }else{
            showSnackBar("No Internet Connection", action = {
                val studentId = runBlocking {
                    localManager.getIdSiswa()
                }
                val departmentId = runBlocking {
                    localManager.getIdDep()
                }
                viewModelScope.launch {
                    val body = UpdateReadNotificationRequestBody(id_dep = departmentId.toString(), id_siswa = studentId.toString(), list_id_notif = listOf(id))
                    readNotificationUseCase(body)
                }

            })
        }
    }

     fun showSnackBar(text: String, actionText: String = "Retry", action: () -> Unit) {
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
}