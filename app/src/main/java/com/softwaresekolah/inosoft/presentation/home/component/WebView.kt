package com.softwaresekolah.inosoft.presentation.home.component

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.softwaresekolah.inosoft.domain.core.models.User
import com.softwaresekolah.inosoft.util.SsBiometricPromptManager
import com.softwaresekolah.inosoft.util.SsBiometricPromptManager.BiometricResult
import timber.log.Timber

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(
    url: String,
){
    val activity = LocalContext.current as FragmentActivity

    val promptManager by lazy {
        SsBiometricPromptManager(activity)
    }

    val biometricResult by promptManager.promptResults.collectAsState(
        initial = null
    )
    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            println("Activity result: $it")
        }
    )

    var webView by remember { mutableStateOf<WebView?>(null) }

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
            this.webChromeClient = WebChromeClient()
            this.settings.javaScriptEnabled = true
            this.addJavascriptInterface(WebInterface(activity, promptManager), "app")
            this.settings.allowContentAccess = true;
            this.settings.allowFileAccess = true;

//                this.webChromeClient = CustomWebChromeClient()
        }


    }, update = {
        it.loadUrl(url)
        webView = it

    })
    LaunchedEffect(biometricResult) {
        if(biometricResult is SsBiometricPromptManager.BiometricResult.AuthenticationNotSet) {
            if(Build.VERSION.SDK_INT >= 30) {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BiometricManager.Authenticators.BIOMETRIC_STRONG
                    )
                }
                enrollLauncher.launch(enrollIntent)
            }
        }else{
            biometricResult?.let { result ->
                when(result) {
                    is BiometricResult.AuthenticationError -> {
                        result.error
                    }
                    BiometricResult.AuthenticationFailed -> {
                        webView?.evaluateJavascript("sendBiometricData('Authentication failed')", null)
                    }
                    BiometricResult.AuthenticationNotSet -> {
                        webView?.evaluateJavascript("sendBiometricData('Authentication not set')", null)

                    }
                    BiometricResult.AuthenticationSuccess -> {
                        val jsonData = Gson().toJson("newData")
                        webView!!.evaluateJavascript("sendBiometricData('$jsonData')", null)

                    }
                    BiometricResult.FeatureUnavailable -> {
                        webView?.evaluateJavascript("sendBiometricData('Feature unavailable')", null)

                    }
                    BiometricResult.HardwareUnavailable -> {
                        webView?.evaluateJavascript("sendBiometricData('Hardware unavailable')", null)
                    }
                }
            }
        }
    }




}

private class WebInterface(context: FragmentActivity, promptManager: SsBiometricPromptManager) {
    /**
     * Caution: If you've set your targetSdkVersion to 17 or higher, you must
     * add the @JavascriptInterface annotation to any method that you want
     * available to your JavaScript (the method must also be public). If you do
     * not provide the annotation, the method is not accessible by your web page
     * when running on Android 4.2 or higher.
     */
    private val mContextRef: FragmentActivity
    private val manager: SsBiometricPromptManager


    init {
        mContextRef = context
        manager = promptManager
    }

    @JavascriptInterface
    fun showToast(toastMsg: String?) {
        Timber.tag("TOAST WEBVIEW").d(toastMsg.toString())
        // JavaScript doesn't run on the UI thread, make sure you do anything UI related like this
        // You don't need this for the Toast, but otherwise it's a good idea
        Toast.makeText(mContextRef, toastMsg, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun biometricAuth() {
        manager.showBiometricPrompt(
            title = "Gunakan Biometric",
            description = "Gunakan Biometric untuk melanjutkan"
        )
    }
}