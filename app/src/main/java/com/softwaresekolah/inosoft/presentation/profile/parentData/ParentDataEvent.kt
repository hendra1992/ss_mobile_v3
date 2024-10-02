package com.softwaresekolah.inosoft.presentation.profile.parentData

sealed class ParentDataEvent {
    data object OnUpdate: ParentDataEvent()
    data object OnClearText: ParentDataEvent()
    data object OnClearError: ParentDataEvent()
    data class OnSave(
        val dadName: String,
        val dadPhone: String,
        val momName: String,
        val momPhone: String,
    ): ParentDataEvent()
}