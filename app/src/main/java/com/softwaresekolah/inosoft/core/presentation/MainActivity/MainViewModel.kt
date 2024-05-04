package com.softwaresekolah.inosoft.core.presentation.MainActivity

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwaresekolah.inosoft.auth.domain.usecase.ReadUserLogin
import com.softwaresekolah.inosoft.core.domain.usecase.ReadAppEntry
import com.softwaresekolah.inosoft.core.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val readAppEntry: ReadAppEntry,
    private val readUserLogin: ReadUserLogin
): ViewModel() {
    private val _splashCondition = mutableStateOf(true)
    val splashCondition: State<Boolean> = _splashCondition

    private val _startDestination = mutableStateOf(Route.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination

    private val _skipBoardingScreen = readAppEntry()
    private val _skipLoginScreen = readUserLogin()

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

//        readAppEntry().onEach { skipBoardingScreen ->
//            if(skipBoardingScreen){
//                readUserLogin().onEach {skipLoginScreen ->
//                    if (skipLoginScreen){
//                        _startDestination.value = Route.AppSsNavigation.route
//                    }else{
//                        _startDestination.value = Route.AppAuthNavigation.route
//                    }
//                }
//            }else{
//                _startDestination.value = Route.AppStartNavigation.route
//            }
//            delay(300) //Without this delay, the onBoarding screen will show for a momentum.
//            _splashCondition.value = false
//        }.launchIn(viewModelScope)
    }
}