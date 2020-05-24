package com.king.template.app.about

import android.os.Bundle
import android.view.View
import com.king.app.dialog.AppDialog
import com.king.app.dialog.AppDialogConfig
import com.king.app.updater.AppUpdater
import com.king.template.BuildConfig
import com.king.template.R
import com.king.template.app.base.BaseActivity
import com.king.template.databinding.AboutActivityBinding
import kotlinx.android.synthetic.main.about_activity.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class AboutActivity : BaseActivity<AboutViewModel, AboutActivityBinding>(){

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        setToolbarTitle(getString(R.string.about_title))
        tvAppVersion.text = "V ${BuildConfig.VERSION_NAME}"
    }

    override fun getLayoutId(): Int {
        return R.layout.about_activity
    }

    private fun clickVersionUpdate(){
        //TODO 处理点击“版本更新”逻辑

        //当检测到新版本时，这里只是演示检测到新版本的大概流程
        var config = AppDialogConfig()
        with(config){
            isHideTitle = true
            content = "发现新版本(这里只是演示模板步骤)"
            ok = "更新"
            onClickOk = View.OnClickListener {
                AppDialog.INSTANCE.dismissDialog()
                showToast("后台下载更新中...")
                //下载App
//                AppUpdater.Builder().serUrl("https://xxx/xxx/xxx.apk").build(context).start()
            }
        }
        AppDialog.INSTANCE.showDialog(context,config)
    }

    private fun clickAbout(){
        //TODO 处理点击“关于”逻辑

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