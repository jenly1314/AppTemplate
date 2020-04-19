package com.king.template.app.account

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.app.base.BaseActivity
import com.king.template.databinding.CodeLoginActivityBinding
import kotlinx.android.synthetic.main.code_login_activity.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class CodeLoginActivity : BaseActivity<LoginViewModel, CodeLoginActivityBinding>(){

    var username : String? = null

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        setToolbarTitle(getString(R.string.login))

        etUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etUsername.isSelected = !TextUtils.isEmpty(s)
            }

        })
        setClickRightClearListener(etUsername)

        username = intent.getStringExtra(Constants.KEY_USERNAME)

        username?.let {
            etUsername.setText(it)
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.code_login_activity
    }

    //-------------------------------

    private fun clickRegister(){
        username = etUsername.text.toString()
        startActivity(RegisterActivity::class.java,username)
    }

    private fun clickPwdLogin(){
        onBackPressed()
    }


    private fun clickLogin(){
        //TODO 点击“登录”逻辑

        if(!checkInput(etUsername,R.string.hint_username)){
            return
        }
        if(!checkInput(etCode,R.string.hint_verify_code)){
            return
        }

        //TODO 点击“登录”逻辑
        showToast(R.string.login)

        val username = etUsername.text.toString()
        val verifyCode = etCode.text.toString()
        viewModel.verifyCodeLogin(username,verifyCode)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.btnLogin -> clickLogin()
            R.id.tvPwdLogin -> clickPwdLogin()
            R.id.tvRegister -> clickRegister()
        }
    }
}