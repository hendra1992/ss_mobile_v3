package com.softwaresekolah.inosoft.presentation.profile.address

sealed class AddressEvent {
    data object OnUpdate: AddressEvent()
    data object OnClearText: AddressEvent()
    data object OnClearError: AddressEvent()
    data class OnProvinceChanges(
        val provinceId: Int
    ): AddressEvent()
    data class OnSave(
        val address: String,
        val provinceId: Int,
        val cityId: Int,
        val postalCode: String,
        val telephone: String,
    ): AddressEvent()
}