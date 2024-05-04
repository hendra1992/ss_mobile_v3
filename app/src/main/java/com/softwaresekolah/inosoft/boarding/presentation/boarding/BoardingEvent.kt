package com.softwaresekolah.inosoft.boarding.presentation.boarding

sealed class BoardingEvent {
    object saveAppEntry: BoardingEvent()
}