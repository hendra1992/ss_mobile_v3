package com.softwaresekolah.inosoft.presentation.settings.component.listAccount

import com.softwaresekolah.inosoft.domain.core.models.User

sealed class ListAccountEvent {
    data class OnSwitchAccount(
        val user: User
    ): ListAccountEvent()
    data class OnUpdateUsers(
        val updatedUsers: List<User>,
    ): ListAccountEvent()
    data object OnClearText : ListAccountEvent()
    data object OnUpdate: ListAccountEvent()

    data class OnLogout(
        val idSiswa: String
    ): ListAccountEvent()

}