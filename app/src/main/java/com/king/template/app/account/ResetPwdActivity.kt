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
import com.king.template.databinding.ResetPwdActivityBinding
import com.king.template.dict.VerifyCodeScene
import com.king.template.extension.disableCopyAndPaste
import com.king.template.util.CheckUtils
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class ResetPwdActivity : BaseActivity<PasswordViewModel, ResetPwdActivityBinding>() {

    var username: String? = null

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        setToolbarTitle(getString(R.string.reset_password))

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
        setClickRightEyeListener(binding.etNewPassword)
        setClickRightEyeListener(binding.etConfirmPassword)

        binding.etNewPassword.disableCopyAndPaste(false)
        binding.etConfirmPassword.disableCopyAndPaste(false)

        viewModel.liveDataResetPassword.observe(this) {
            if (it) {
                // TODO 密码重置成功后的逻辑处理
                showToast(R.string.successfully_reset)
                finish()
            }
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
        return R.layout.reset_pwd_activity
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
        viewModel.getVerifyCode(username!!, VerifyCodeScene.PASSWORD)
    }

    /**
     * 重置
     */
    private fun clickReset() {
        if (!checkInput(binding.etUsername, R.string.hint_username)) {
            return
        }
        if (!CheckUtils.checkUsername(binding.etUsername.text.toString())) {
            showToast(R.string.tips_username_matcher)
            return
        }
        if (!checkInput(binding.etCode, R.string.hint_verify_code)) {
            return
        }
        if (!checkInput(binding.etNewPassword, R.string.hint_password)) {
            return
        }
        if (!CheckUtils.checkPassword(binding.etNewPassword.text.toString())) {
            showToast(R.string.tips_password_matcher)
            return
        }

        // TODO 点击“重置密码”逻辑
        Timber.d(getString(R.string.reset_password))

        val username = binding.etUsername.text.toString()
        val verifyCode = binding.etCode.text.toString()
        val password = binding.etNewPassword.text.toString()
        viewModel.resetPassword(username, verifyCode, password)

    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.tvGetCode -> clickGetCode()
            R.id.btnReset -> clickReset()
        }
    }
}
