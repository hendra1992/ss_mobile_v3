package com.softwaresekolah.inosoft.core.Util

sealed class Resource<T> {
    data class Loading<T>(val isLoading: Boolean) : Resource<T>()

    data class Success<T>(val data: T) : Resource<T>()

    data class Response<T>(val uiComponent: UIComponent, val error: Exception?=null) : Resource<T>()
}

sealed class UIComponent {

    data class Toast(val message: String): UIComponent()

    data class Dialog(val title: String, val message: String): UIComponent()

    data class None(val message: String? = null): UIComponent()

}