package com.king.template.app.me

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.king.template.BuildConfig
import com.king.template.R
import com.king.template.app.about.AboutActivity
import com.king.template.app.account.ChangePwdActivity
import com.king.template.app.base.BaseFragment
import com.king.template.constant.Constants
import com.king.template.databinding.MeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class MeFragment : BaseFragment<MeViewModel, MeFragmentBinding>(), View.OnClickListener {

    companion object {
        fun newInstance(): MeFragment {
            return MeFragment()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        updateUI()
        binding.tvAppVersion.text = "v${BuildConfig.VERSION_NAME}"

        // TODO 对应的菜单按钮
        binding.rlUser.setOnClickListener(this)
        binding.tvMenu1.setOnClickListener(this)
        binding.tvMenu2.setOnClickListener(this)
        binding.tvMenu3.setOnClickListener(this)
        binding.tvMenu4.setOnClickListener(this)
        binding.btnAbout.setOnClickListener(this)
    }

    private fun updateUI() {
        // TODO 更新UI显示
        binding.tvName.text = Constants.TAG
        binding.tvUsername.text = "138****8888"
    }

    override fun getLayoutId(): Int {
        return R.layout.me_fragment
    }

    //-------------------------------

    private fun clickChangePassword() {
        startActivity(ChangePwdActivity::class.java)
    }

    private fun clickUser() {
        // TODO 点击用户信息逻辑
        startLoginActivity()
    }


    private fun clickAbout() {
        startActivity(AboutActivity::class.java)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rlUser -> clickUser()
            R.id.btnAbout -> clickAbout()
            R.id.tvMenu1 -> clickChangePassword()
            R.id.tvMenu2 -> startWebActivity("https://github.com/jenly1314", "GitHub")
            R.id.tvMenu3 -> startWebActivity("https://jenly1314.github.io", "Jenly")
            R.id.tvMenu4 -> startWebActivity("https://developer.android.google.cn", "Android")
        }
    }
}
