package com.softwaresekolah.inosoft.presentation.boarding

sealed class BoardingEvent {
    data object saveAppEntry: BoardingEvent()
}