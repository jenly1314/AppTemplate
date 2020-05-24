package com.king.template.app.base

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.core.view.isVisible
import com.github.lzyzsd.jsbridge.DefaultHandler
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.databinding.WebActivityBinding
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.web_activity.*
import timber.log.Timber

/**
 * 细节处理待完善
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class WebActivity : BaseActivity<BaseViewModel,WebActivityBinding>() {

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

        pbFirst.isVisible = true
        web.setDefaultHandler(DefaultHandler())

        web.webChromeClient = object : WebChromeClient(){

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                title?.let {
                    if(!it.equals(BLANK_URL,true)){
                        tvTitle.text = it
                    }
                }

            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                updateProgress(newProgress,isError)
            }

        }
        web.webViewClient = object : WebViewClient(){

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
                pbFirst.isVisible = false
                super.onPageFinished(view, url)
                Timber.d("onPageFinished:$url")
                updateProgress(100,isError)

            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                Timber.d("onReceivedError:$url")

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    val code = error.errorCode
                    Timber.d("errorCode:${code}")
                    if(code <= ERROR_TIMEOUT){
                        isError = true
                        view?.loadUrl(BLANK_URL)
                        updateProgress(0,isError)
                    }
                }else{
                    isError = true
                    view?.loadUrl(BLANK_URL)
                    updateProgress(0,isError)
                }

            }

            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse) {
                super.onReceivedHttpError(view, request, errorResponse)
                Timber.d("onReceivedHttpError:$url")
                val code = errorResponse.statusCode
                Timber.d("errorCode:${code}")
                if(code == 400 || code == 500){
                    isError = true
                    view?.loadUrl(BLANK_URL)
                    updateProgress(0,isError)
                }

            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler, error: SslError?) {
                super.onReceivedSslError(view, handler, error)
                Timber.d("onReceivedSslError:$url")
                handler.cancel()
                handler.proceed()
            }


        }

        web.loadUrl(url)

    }

    private fun initToolbarTitle(){
        intent.getStringExtra(Constants.KEY_TITLE)?.let {
            tvTitle.text = it
        }
    }

    /**
     * 更新进度
     */
    private fun updateProgress(progress: Int,isError: Boolean){

        if(isError){
            pb.progress = 0
            pb.visibility = View.GONE
            llError.visibility = View.VISIBLE

        }else{
            pb.progress = progress
            if(llError.visibility != View.GONE){
                llError.visibility = View.GONE
            }

            if(progress<100){
                if(pb.visibility != View.VISIBLE){
                    pb.visibility = View.VISIBLE
                }

            }else{
                pb.visibility = View.GONE
            }

        }
    }


    override fun getLayoutId(): Int {
        return R.layout.web_activity
    }


    private fun retry(){
        web.loadUrl(url)
    }

    private fun isGoBack(): Boolean {
        return web != null && web.canGoBack()
    }


    override fun onBackPressed() {
        if(isGoBack()){
            web.goBack()
            if(curl.equals(BLANK_URL,true)){//返回上一页时如果是空白页，表示之前加载页面出错过
                if(isGoBack()){
                    web.goBack()
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