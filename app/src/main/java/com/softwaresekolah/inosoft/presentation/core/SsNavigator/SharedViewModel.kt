package com.softwaresekolah.inosoft.presentation.core.SsNavigator

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwaresekolah.inosoft.data.core.local.UserDao
import com.softwaresekolah.inosoft.data.core.remote.request.ConfigRequestBody
import com.softwaresekolah.inosoft.domain.auth.usecase.ReadUserExp
import com.softwaresekolah.inosoft.domain.core.manager.LocalManager
import com.softwaresekolah.inosoft.domain.core.models.User
import com.softwaresekolah.inosoft.domain.core.repository.ConfigAppRepository
import com.softwaresekolah.inosoft.domain.core.repository.UserRepository
import com.softwaresekolah.inosoft.presentation.core.navgraph.Route
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
    private val configAppRepository: ConfigAppRepository,
    private val userRepository: UserRepository,
    private val readAppExp: ReadUserExp,
) : ViewModel() {

    private val _userExpCondition = readAppExp()
    val redirectTo = mutableStateOf("")

    fun onEvent(event: SharedViewModelEvent){
        when(event){
            is SharedViewModelEvent.UpdateData ->{
                onUpdate()
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

    private fun onUpdate(){
        viewModelScope.launch {
            val idDep = localManager.getIdDep()
            val idSiswa = localManager.getIdSiswa()
            val config = configAppRepository.readConfig(body = ConfigRequestBody(id_dep = idDep.toString(), id_siswa = idSiswa.toString()))

            val users = runBlocking {
                userRepository.getUsers().first()
            }

//            Timber.tag("Shared View Model").d(users.toString())
        }
    }
}