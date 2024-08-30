package com.softwaresekolah.inosoft.presentation.boarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwaresekolah.inosoft.domain.core.usecase.SaveAppEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardingViewModel @Inject constructor(
    private val saveAppEntry: SaveAppEntry
): ViewModel() {

    fun onEvent(event: BoardingEvent){
        when(event){
            is BoardingEvent.saveAppEntry ->{
                saveUserEntry()
            }
        }
    }

    private fun saveUserEntry(){
        viewModelScope.launch {
            saveAppEntry()
        }
    }
}