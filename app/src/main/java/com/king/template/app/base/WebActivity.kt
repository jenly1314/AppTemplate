package com.king.template.app.base

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.ClientCertRequest
import android.webkit.RenderProcessGoneDetail
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebView.RENDERER_PRIORITY_BOUND
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.webkit.SafeBrowsingResponseCompat
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewClientCompat
import androidx.webkit.WebViewFeature
import com.king.logx.LogX
import com.king.template.R
import com.king.template.constant.Constants
import com.king.template.databinding.WebActivityBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * 通用 WebActivity
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
open class WebActivity : BaseActivity<BaseViewModel, WebActivityBinding>() {

    private var url = BLANK_URL
    private lateinit var curl: String

    private var isError = false

    companion object {
        const val BLANK_URL = "about:blank"
    }


    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initToolbarTitle()

        intent.getStringExtra(Constants.KEY_URL)?.let {
            url = it
        }

        binding.pbFirst.isVisible = true

        intWebSettings(binding.web)

        binding.web.webChromeClient = object : WebChromeClient() {

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                title?.let {
                    if (!it.equals(BLANK_URL, true)) {
                        setToolbarTitle(it)
                    }
                }

            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                updateProgress(newProgress, isError)
            }

        }

        binding.web.webViewClient = object : WebViewClientCompat() {

            override fun onSafeBrowsingHit(
                view: WebView,
                request: WebResourceRequest,
                threatType: Int,
                callback: SafeBrowsingResponseCompat
            ) {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.SAFE_BROWSING_RESPONSE_BACK_TO_SAFETY)) {
                    callback.backToSafety(true)
                }
            }

            override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                LogX.d("url:$url")
                this@WebActivity.curl = url
                if (!url.equals(BLANK_URL, true)) {
                    this@WebActivity.url = url
                    isError = false
                }
                updateProgress(0, false)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                LogX.d("onPageFinished:$url")
                updateProgress(100, isError)

            }

            override fun onRenderProcessGone(
                view: WebView?,
                detail: RenderProcessGoneDetail
            ): Boolean {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LogX.d("onRenderProcessGone: didCrash=${detail.didCrash()}")
                    return !detail.didCrash()
                }
                return false
            }

            @SuppressLint("WebViewClientOnReceivedSslError")
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                super.onReceivedSslError(view, handler, error)
                LogX.d("onReceivedSslError:$url")
                handler?.cancel()
                handler?.proceed()
            }

        }

        onBackPressedDispatcher.addCallback(this) {
            if (canGoBack()) {
                binding.web.goBack()
                // 返回上一页时如果是空白页，表示之前加载页面出错过
                if (curl.equals(BLANK_URL, true)) {
                    if (canGoBack()) {
                        binding.web.goBack()
                    } else {
                        finish()
                    }
                }
            } else {
                finish()
            }
        }

        binding.web.loadUrl(url)

    }

    private fun initToolbarTitle() {
        intent.getStringExtra(Constants.KEY_TITLE)?.let {
            setToolbarTitle(it)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    open fun intWebSettings(webView: WebView) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webView.setRendererPriorityPolicy(RENDERER_PRIORITY_BOUND, true)
        }

        webView.settings.apply {
            // 如果访问的页面中要与Javascript交互，则webView必须设置支持Javascript
            this.javaScriptEnabled = true

            // 设置自适应屏幕，两者合用
            this.useWideViewPort = true // 将图片调整到适合webView的大小
            this.loadWithOverviewMode = true // 缩放至屏幕的大小

            // 缩放操作
            this.setSupportZoom(true) // 支持缩放，默认为true。是下面那个的前提。

            this.builtInZoomControls = true // 设置内置的缩放控件。若为false，则该WebView不可缩放

            this.displayZoomControls = false // 隐藏原生的缩放控件

            // 其他细节操作
            this.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK // 关闭webView中缓存

            this.allowFileAccess = true // 设置可以访问文件

            this.javaScriptCanOpenWindowsAutomatically = true // 支持通过JS打开新窗口

            this.loadsImagesAutomatically = true // 支持自动加载图片

            this.defaultTextEncodingName = "utf-8" // 设置编码格式
        }
    }

    /**
     * 更新进度
     */
    private fun updateProgress(progress: Int, isError: Boolean) {

        if (isError) {
            binding.pb.progress = 0
            binding.pb.visibility = View.GONE
            binding.llError.visibility = View.VISIBLE
            binding.pbFirst.isVisible = false
        } else {
            binding.pb.progress = progress
            if (binding.llError.visibility != View.GONE) {
                binding.llError.visibility = View.GONE
            }

            if (progress < 100) {
                if (binding.pb.visibility != View.VISIBLE) {
                    binding.pb.visibility = View.VISIBLE
                }

            } else {
                binding.pb.visibility = View.GONE
                binding.pbFirst.isVisible = false
            }

        }
    }


    override fun getLayoutId(): Int {
        return R.layout.web_activity
    }


    private fun retry() {
        binding.web.loadUrl(url)
    }

    private fun canGoBack(): Boolean {
        return binding.web.canGoBack()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ivLeft -> finish()
            R.id.llError -> retry()
        }
    }
}
