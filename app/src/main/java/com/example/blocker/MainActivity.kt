package com.example.blocker
// MainActivity.kt

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView: WebView = findViewById(R.id.webView)

        // Enable JavaScript in WebView
        webView.settings.javaScriptEnabled = true

        // Set a WebViewClient to handle page navigation
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }

        // Inject JavaScript to block ads
        val adBlockScript = """
            javascript:(function() {
                var elements = document.querySelectorAll("div[id^='ad'], div[class^='ad'], div[title^='ad'], div[title^='sponsor'], div[class^='sponsor'], div[id^='sponsor'], div[class^='googlead'], iframe[src*='ad'], iframe[src*='sponsor'], iframe[src*='googlead']");
                elements.forEach(element => element.style.display = 'none');
            })();
        """.trimIndent()

        webView.loadUrl("https://example.com")
        webView.postDelayed({
            webView.evaluateJavascript(adBlockScript, null)
        }, 3000) // Wait for the page to load before injecting the ad blocker
    }
}
