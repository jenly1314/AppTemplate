package com.king.template.app.account

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.app.base.BaseActivity
import com.king.template.databinding.LoginActivityBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginViewModel, LoginActivityBinding>(){

    var username : String? = null

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        setToolbarTitle(getString(R.string.login))

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

        username = intent.getStringExtra(Constants.KEY_USERNAME)

        username?.let {
            viewDataBinding.etUsername.setText(it)
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.login_activity
    }

    //-------------------------------

    private fun clickRegister(){
        username = viewDataBinding.etUsername.text.toString()
        startActivity(RegisterActivity::class.java,username)
    }

    private fun clickCodeLogin(){
        username = viewDataBinding.etUsername.text.toString()
        startLoginActivity(username, isCode = true)
    }

    private fun clickForgotPwd(){
        username = viewDataBinding.etUsername.text.toString()
        startActivity(ResetPwdActivity::class.java,username)
    }

    private fun clickLogin(){
        if(!checkInput(viewDataBinding.etUsername,R.string.hint_username)){
            return
        }
        if(!checkInput(viewDataBinding.etPassword,R.string.hint_password)){
            return
        }

        // TODO 点击“登录”逻辑
        showToast(R.string.login)

        val username = viewDataBinding.etUsername.text.toString()
        val password = viewDataBinding.etPassword.text.toString()
        viewModel.login(username,password)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.btnLogin -> clickLogin()
            R.id.tvForgotPwd -> clickForgotPwd()
            R.id.tvCodeLogin -> clickCodeLogin()
            R.id.tvRegister -> clickRegister()
        }
    }
}