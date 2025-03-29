package com.king.template.app.account

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import com.king.template.R
import com.king.template.app.base.BaseActivity
import com.king.template.constant.Constants
import com.king.template.databinding.CodeLoginActivityBinding
import com.king.template.dict.VerifyCodeScene
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class CodeLoginActivity : BaseActivity<LoginViewModel, CodeLoginActivityBinding>() {

    var username: String? = null

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        setToolbarTitle(getString(R.string.login))

        binding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.etUsername.isSelected = !TextUtils.isEmpty(s)
            }

        })
        setClickRightClearListener(binding.etUsername)

        viewModel.liveData.observe(this) {
            startHomeActivity()
            finish()
        }

        viewModel.liveDataGetCode.observe(this) {
            if (it) {
                startCountDownTime()
            }
        }

        username = intent.getStringExtra(Constants.KEY_USERNAME)

        username?.let {
            binding.etUsername.setText(it)
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.code_login_activity
    }

    private fun startCountDownTime() {
        object : CountDownTimer(
            Constants.VERIFY_CODE_COUNT_DOWN_DURATION,
            Constants.VERIFY_CODE_COUNT_DOWN_INTERVAL
        ) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvGetCode.isEnabled = false
                binding.tvGetCode.setTextColor(ContextCompat.getColor(getContext(), R.color.text_9))
                binding.tvGetCode.text =
                    java.lang.String.format(
                        getString(R.string.verify_code_down_time_),
                        millisUntilFinished / 1000
                    )
            }

            override fun onFinish() {
                binding.tvGetCode.isEnabled = true
                binding.tvGetCode.setTextColor(
                    ContextCompat.getColor(
                        getContext(),
                        R.color.text_theme
                    )
                )
                binding.tvGetCode.setText(R.string.send_verify_code)
            }
        }.start()
    }

    //-------------------------------

    /**
     * 获取验证码
     */
    private fun clickGetCode() {
        // TODO 点击“发送验证码”逻辑
        if (!checkInput(binding.etUsername, R.string.hint_username)) {
            return
        }
        username = binding.etUsername.text.toString()
        viewModel.getVerifyCode(username!!, VerifyCodeScene.LOGIN)
    }

    private fun clickRegister() {
        username = binding.etUsername.text.toString()
        startActivity(RegisterActivity::class.java, username)
    }

    private fun clickPwdLogin() {
        finish()
    }


    private fun clickLogin() {
        // TODO 点击“登录”逻辑

        if (!checkInput(binding.etUsername, R.string.hint_username)) {
            return
        }
        if (!checkInput(binding.etCode, R.string.hint_verify_code)) {
            return
        }

        // TODO 点击“登录”逻辑
        Timber.d(getString(R.string.verify_code_login))

        val username = binding.etUsername.text.toString()
        val verifyCode = binding.etCode.text.toString()
        viewModel.verifyCodeLogin(username, verifyCode)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.tvGetCode -> clickGetCode()
            R.id.btnLogin -> clickLogin()
            R.id.tvPwdLogin -> clickPwdLogin()
            R.id.tvRegister -> clickRegister()
        }
    }
}
