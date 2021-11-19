package com.king.template.app.base

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.core.view.isVisible
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.databinding.WebActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


/**
 * 通用 WebActivity，细节处理待完善
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
open class WebActivity : BaseActivity<BaseViewModel,WebActivityBinding>() {

    private var url = BLANK_URL
    private lateinit var curl: String

    private var isError = false

    companion object{
        const val BLANK_URL = "about:blank"
    }


    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initToolbarTitle()

        intent.getStringExtra(Constants.KEY_URL)?.let {
            url = it
        }

        viewDataBinding.pbFirst.isVisible = true

        intWebSettings(viewDataBinding.web)

        viewDataBinding.web.webChromeClient = object : WebChromeClient(){

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                title?.let {
                    if(!it.equals(BLANK_URL,true)){
                        setToolbarTitle(it)
                    }
                }

            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                updateProgress(newProgress,isError)
            }

        }
        viewDataBinding.web.webViewClient = object : WebViewClient(){

            override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Timber.d("url:$url")
                this@WebActivity.curl = url
                if(!url.equals(BLANK_URL,true)){
                    this@WebActivity.url = url
                    isError = false
                }
                updateProgress(0,false)
            }



            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Timber.d("onPageFinished:$url")
                updateProgress(100,isError)

            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                Timber.d("onReceivedError:$url")

//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                    val code = error.errorCode
//                    Timber.d("errorCode:${code}")
//                    if(code <= ERROR_TIMEOUT){
//                        isError = true
//                        view?.loadUrl(BLANK_URL)
//                        updateProgress(0,isError)
//                    }
//                }else{
//                    isError = true
//                    view?.loadUrl(BLANK_URL)
//                    updateProgress(0,isError)
//                }

            }

            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse) {
                super.onReceivedHttpError(view, request, errorResponse)
                Timber.d("onReceivedHttpError:$url")
                val code = errorResponse.statusCode
                Timber.d("errorCode:${code}")
//                if(code == 400 || code == 500){
//                    isError = true
//                    view?.loadUrl(BLANK_URL)
//                    updateProgress(0,isError)
//                }

            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler, error: SslError?) {
                super.onReceivedSslError(view, handler, error)
                Timber.d("onReceivedSslError:$url")
                handler.cancel()
                handler.proceed()
            }


        }

        viewDataBinding.web.loadUrl(url)

    }

    private fun initToolbarTitle(){
        intent.getStringExtra(Constants.KEY_TITLE)?.let {
            setToolbarTitle(it)
        }
    }

    open fun intWebSettings(webView: WebView){
        webView.settings.apply {
            //如果访问的页面中要与Javascript交互，则webView必须设置支持Javascript
            this.javaScriptEnabled = true

            //设置自适应屏幕，两者合用
            this.useWideViewPort = true //将图片调整到适合webView的大小
            this.loadWithOverviewMode = true // 缩放至屏幕的大小

            //缩放操作
            this.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。

            this.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放

            this.displayZoomControls = false //隐藏原生的缩放控件

            //其他细节操作
            this.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK //关闭webView中缓存

            this.allowFileAccess = true //设置可以访问文件

            this.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口

            this.loadsImagesAutomatically = true //支持自动加载图片

            this.defaultTextEncodingName = "utf-8" //设置编码格式
        }
    }

    /**
     * 更新进度
     */
    private fun updateProgress(progress: Int,isError: Boolean){

        if(isError){
            viewDataBinding.pb.progress = 0
            viewDataBinding.pb.visibility = View.GONE
            viewDataBinding.llError.visibility = View.VISIBLE
            viewDataBinding.pbFirst.isVisible = false
        }else{
            viewDataBinding.pb.progress = progress
            if(viewDataBinding.llError.visibility != View.GONE){
                viewDataBinding.llError.visibility = View.GONE
            }

            if(progress < 100){
                if(viewDataBinding.pb.visibility != View.VISIBLE){
                    viewDataBinding.pb.visibility = View.VISIBLE
                }

            }else{
                viewDataBinding.pb.visibility = View.GONE
                viewDataBinding.pbFirst.isVisible = false
            }

        }
    }


    override fun getLayoutId(): Int {
        return R.layout.web_activity
    }


    private fun retry(){
        viewDataBinding.web.loadUrl(url)
    }

    private fun isGoBack(): Boolean {
        return viewDataBinding.web != null && viewDataBinding.web.canGoBack()
    }


    override fun onBackPressed() {
        if(isGoBack()){
            viewDataBinding.web.goBack()
            if(curl.equals(BLANK_URL,true)){//返回上一页时如果是空白页，表示之前加载页面出错过
                if(isGoBack()){
                    viewDataBinding.web.goBack()
                }else{
                    super.onBackPressed()
                }
            }
            return
        }
        super.onBackPressed()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.ivLeft -> finish()
            R.id.llError -> retry()
        }
    }
}