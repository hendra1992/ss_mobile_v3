package com.softwaresekolah.inosoft.presentation.core.SsNavigator

sealed class SharedViewModelEvent {
    data object UpdateData: SharedViewModelEvent()
}