package com.softwaresekolah.inosoft.presentation.settings.component.listAccount

import com.softwaresekolah.inosoft.domain.core.models.User

data class ListAccountState (
    val users: List<User> = emptyList(),
    val currentUser: String = "",
    val text: String? = null,
)