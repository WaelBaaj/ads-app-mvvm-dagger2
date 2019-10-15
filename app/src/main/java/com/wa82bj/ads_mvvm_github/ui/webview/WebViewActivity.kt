package com.wa82bj.ads_mvvm_github.ui.webview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wa82bj.ads_mvvm_github.R
import com.wa82bj.ads_mvvm_github.databinding.ActivityWebViewBinding
import android.webkit.WebView
import android.webkit.WebViewClient


class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityWebViewBinding>(
            this,
            R.layout.activity_web_view
        )

        init(binding)

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init(binding: ActivityWebViewBinding) {
        val frameVideo =
            "<html><body><iframe width=\"420\" height=\"315\" src=\"https://www.youtube.com/embed/PMBcMBhsm80\" frameborder=\"0\" allowfullscreen></iframe></body></html>"


        val newsUrl = intent?.extras?.getString(KEY_URL) ?: return
        val webSiteUrl = newsUrl


        binding.apply {
            webView.setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    return false
                }
            })
            webView.settings.javaScriptEnabled = true
            webView.loadData(frameVideo, "text/html", "utf-8");

            url = webSiteUrl
            toolbarTitle = webSiteUrl
            setToolbarHomeNavClickListener {
                onBackPressed()
            }
        }
    }

    companion object {
        const val EXTRA_URL = "Your WebSite"
        private val KEY_URL = "KEY_URL"
    }

}