package com.king.template.app.account

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.app.base.BaseActivity
import com.king.template.databinding.RegisterActivityBinding
import com.king.template.dict.VerifyCodeScene
import com.king.template.extension.disableCopyAndPaste
import com.king.template.util.CheckUtils
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class RegisterActivity : BaseActivity<RegisterViewModel, RegisterActivityBinding>(){

    var username : String? = null

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        setToolbarTitle(getString(R.string.register))

        viewDataBinding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewDataBinding.etUsername.isSelected = !TextUtils.isEmpty(s)
            }

        })
        setClickRightClearListener(viewDataBinding.etUsername)
        setClickRightEyeListener(viewDataBinding.etPassword)

        viewDataBinding.etPassword.disableCopyAndPaste()

        viewModel.liveData.observe(this) {
            if (it) {
                // TODO 注册成功后的逻辑处理
                showToast(R.string.successfully_registration)
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
            viewDataBinding.etUsername.setText(it)
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.register_activity
    }

    private fun startCountDownTime() {
        object : CountDownTimer(Constants.VERIFY_CODE_COUNT_DOWN_DURATION, Constants.VERIFY_CODE_COUNT_DOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                viewDataBinding.tvGetCode.isEnabled = false
                viewDataBinding.tvGetCode.setTextColor(ContextCompat.getColor(context, R.color.text_9))
                viewDataBinding.tvGetCode.text =
                    java.lang.String.format(
                        getString(R.string.verify_code_down_time_),
                        millisUntilFinished / 1000
                    )
            }

            override fun onFinish() {
                viewDataBinding.tvGetCode.isEnabled = true
                viewDataBinding.tvGetCode.setTextColor(ContextCompat.getColor(context, R.color.text_theme))
                viewDataBinding.tvGetCode.setText(R.string.send_verify_code)
            }
        }.start()
    }


    //-------------------------------


    private fun clickTermOfService(){
        // TODO 点击“用户服务协议条款”逻辑
        showToast(R.string.tips_user_terms_of_service)
    }

    private fun clickGetCode(){
        // TODO 点击“发送验证码”逻辑
        if(!checkInput(viewDataBinding.etUsername,R.string.hint_username)){
            return
        }
        username = viewDataBinding.etUsername.text.toString()
        viewModel.getVerifyCode(username!!, VerifyCodeScene.REGISTER)
    }

    private fun clickLoginNow(){
        finish()
    }

    private fun clickRegister(){
        if(!checkInput(viewDataBinding.etUsername,R.string.hint_username)){
            return
        }
        if(!CheckUtils.checkUsername(viewDataBinding.etUsername.text.toString())){
            showToast(R.string.tips_username_matcher)
            return
        }
        if(!checkInput(viewDataBinding.etCode,R.string.hint_verify_code)){
            return
        }
        if(!checkInput(viewDataBinding.etPassword,R.string.hint_password)){
            return
        }

        // TODO 点击“注册”逻辑
        Timber.d(getString(R.string.register))

        val username = viewDataBinding.etUsername.text.toString()
        val verifyCode = viewDataBinding.etCode.text.toString()
        val password = viewDataBinding.etPassword.text.toString()
        viewModel.register(username,verifyCode,password)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.btnRegister -> clickRegister()
            R.id.tvLogin -> clickLoginNow()
            R.id.tvGetCode -> clickGetCode()
            R.id.tvTermOfService -> clickTermOfService()
        }
    }
}