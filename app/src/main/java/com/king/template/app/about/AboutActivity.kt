package com.king.template.app.about

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.king.app.dialog.AppDialog
import com.king.app.dialog.AppDialogConfig
import com.king.template.BuildConfig
import com.king.template.R
import com.king.template.app.base.BaseActivity
import com.king.template.databinding.AboutActivityBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class AboutActivity : BaseActivity<AboutViewModel, AboutActivityBinding>(){

    @SuppressLint("SetTextI18n")
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        setToolbarTitle(getString(R.string.about_title))
        binding.tvAppVersion.text = "v${BuildConfig.VERSION_NAME}"
    }

    override fun getLayoutId(): Int {
        return R.layout.about_activity
    }

    private fun clickVersionUpdate(){
        // TODO 处理点击“版本更新”逻辑

        // 当检测到新版本时，这里只是演示检测到新版本的大概流程
        var config = AppDialogConfig(getContext()).apply {
            title = "版本更新"
            content = "发现新版本(这里只是演示模板步骤)"
            confirm = "更新"
            onClickConfirm = View.OnClickListener {
                AppDialog.INSTANCE.dismissDialog()
                showToast("后台下载更新中...")
                // 下载App
//                AppUpdater.Builder().serUrl("https://xxx/xxx/xxx.apk").build(context).start()
            }
        }
        AppDialog.INSTANCE.showDialog(config)
    }

    private fun clickAbout(){
        // TODO 处理点击“关于”逻辑

        startWebActivity("https://github.com/jenly1314/AppTemplate",getString(R.string.app_name))
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.tvVersionUpdate -> clickVersionUpdate()
            R.id.tvAbout -> clickAbout()
        }
    }
}