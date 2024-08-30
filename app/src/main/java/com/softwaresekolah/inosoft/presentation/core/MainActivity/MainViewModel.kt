package com.softwaresekolah.inosoft.presentation.core.MainActivity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwaresekolah.inosoft.domain.auth.usecase.ReadUserExp
import com.softwaresekolah.inosoft.domain.auth.usecase.ReadUserLogin
import com.softwaresekolah.inosoft.domain.core.usecase.ReadAppEntry
import com.softwaresekolah.inosoft.domain.core.usecase.ReadFcmToken
import com.softwaresekolah.inosoft.domain.core.usecase.SaveFcmToken
import com.softwaresekolah.inosoft.presentation.core.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val readAppEntry: ReadAppEntry,
    private val readUserLogin: ReadUserLogin,
    private val readUserExp: ReadUserExp,
    private val readFcmToken: ReadFcmToken,
    private val saveFcmToken: SaveFcmToken,
): ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Route.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination

    private val _skipBoardingScreen = readAppEntry()
    private val _skipLoginScreen = readUserLogin()
    private val _isLoginExp = readUserExp()
    private val _currentFcmToken = mutableStateOf("")
    val currentFcmToken: State<String> = _currentFcmToken


    init {

       _skipBoardingScreen.zip(_skipLoginScreen){ skipBoarding, skipLogin ->
           if(skipBoarding){
                if (skipLogin){
                    _startDestination.value = Route.AppSsNavigation.route
                }else{
                    _startDestination.value = Route.AppAuthNavigation.route
                }
            }else{
                _startDestination.value = Route.AppStartNavigation.route
            }
            delay(300) //Without this delay, the onBoarding screen will show for a momentum.
            _splashCondition.value = false
       }.launchIn(viewModelScope)

       readFcmToken().onEach {token->
           _currentFcmToken.value = token
       }.launchIn(viewModelScope)

    }

    fun saveToken(token: String){
        viewModelScope.launch {
            saveFcmToken(token)
        }
    }

    fun dismissDialog(){
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ){
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)){
            visiblePermissionDialogQueue.add(permission)
        }
    }
}