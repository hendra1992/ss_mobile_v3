package com.softwaresekolah.inosoft.home.presentation.component

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.text.TextUtils
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import java.lang.ref.WeakReference

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(
    url: String,
){
    val activity = LocalContext.current.findActivity()
    // Declare a string that contains a url

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(factory = {
        WebView(it).apply {
            this.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            this.webViewClient = WebViewClient()
            this.settings.javaScriptEnabled = true
            this.addJavascriptInterface(WebInterface(activity), "Android")

//                this.webChromeClient = CustomWebChromeClient()
        }
    }, update = {
        it.loadUrl(url)
    })
}

fun Context.findActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

private class WebInterface(context: Activity?) {
    /**
     * Caution: If you've set your targetSdkVersion to 17 or higher, you must
     * add the @JavascriptInterface annotation to any method that you want
     * available to your JavaScript (the method must also be public). If you do
     * not provide the annotation, the method is not accessible by your web page
     * when running on Android 4.2 or higher.
     */
    private val mContextRef: WeakReference<Activity?>

    init {
        mContextRef = WeakReference(context)
    }

    @JavascriptInterface
    fun showToast(toastMsg: String?) {
        if (TextUtils.isEmpty(toastMsg) || mContextRef.get() == null) {
            return
        }

        // JavaScript doesn't run on the UI thread, make sure you do anything UI related like this
        // You don't need this for the Toast, but otherwise it's a good idea
        mContextRef.get()!!.runOnUiThread {
            Toast.makeText(mContextRef.get(), toastMsg, Toast.LENGTH_SHORT).show()
        }
    }
}