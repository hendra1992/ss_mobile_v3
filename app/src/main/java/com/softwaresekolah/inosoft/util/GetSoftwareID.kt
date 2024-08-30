package com.softwaresekolah.inosoft.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import javax.inject.Inject

class GetSoftwareID @Inject constructor(
) {

    @SuppressLint("HardwareIds")
    operator fun invoke(context: Context): String? {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID);
    }
}