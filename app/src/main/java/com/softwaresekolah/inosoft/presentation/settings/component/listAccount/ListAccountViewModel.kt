package com.softwaresekolah.inosoft.presentation.settings.component.listAccount

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwaresekolah.inosoft.domain.auth.usecase.LogoutUseCase
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.core.models.User
import com.softwaresekolah.inosoft.domain.core.repository.UserRepository
import com.softwaresekolah.inosoft.domain.core.usecase.GetAllUser
import com.softwaresekolah.inosoft.domain.core.usecase.SwitchAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ListAccountViewModel @Inject constructor(
    private val getAllUser: GetAllUser,
    private val switchAccountUseCase: SwitchAccount,
    private val logoutUseCase: LogoutUseCase,
    private val localManager: LocalManager,
    private val userRepository: UserRepository
): ViewModel(){
    private val _state = mutableStateOf(ListAccountState())
    val state: State<ListAccountState> = _state

    init {
        getUsers()
    }

    fun onEvent(event: ListAccountEvent){
        when(event){
            is ListAccountEvent.OnSwitchAccount ->{
                switchAccount(event.user)
            }
            is ListAccountEvent.OnClearText ->{
                clearText()
            }
            is ListAccountEvent.OnUpdate ->{
                getUsers()
            }
            is ListAccountEvent.OnLogout -> {
                logout(event.idSiswa)
            }
            is ListAccountEvent.OnUpdateUsers ->{
                updateUserState(event.updatedUsers)
            }
        }
    }

    private fun getUsers(){
        viewModelScope.launch {
            val idSiswa =  runBlocking {
               localManager.getIdSiswa()
            }
            _state.value =_state.value.copy(currentUser = idSiswa.toString())
        }

        getAllUser().onEach {
            _state.value = _state.value.copy(users = it)
        }.launchIn(viewModelScope)
    }

    private fun logout(idSiswa: String){
        viewModelScope.launch {
            val activeUser = runBlocking {
                localManager.getIdSiswa()
            }
            val isLogoutActiveUser = (idSiswa == activeUser.toString())


            logoutUseCase(idSiswa)

            if (isLogoutActiveUser){
                val changedIdSiswa = runBlocking {
                    localManager.getIdSiswa()
                }
                val user = runBlocking {
                    userRepository.getUser(changedIdSiswa.toString())
                }
                user?.let {
                    _state.value = _state.value.copy(currentUser = it.idSiswa, text = "logout berhasil beralih ke ${user.siswaNama}")
                }
            }else{
                _state.value = _state.value.copy(text = "logout berhasil")
            }
        }
    }

    private fun switchAccount(user: User){
        viewModelScope.launch {
            val success = switchAccountUseCase(user)
            _state.value =_state.value.copy(text = success, currentUser = user.idSiswa)
        }
    }

    private fun updateUserState(users: List<User>){
        _state.value = _state.value.copy(users = users)
    }

    private fun clearText(){
        _state.value =_state.value.copy(text = null)
    }
}