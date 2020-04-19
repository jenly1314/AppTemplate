package com.king.template.app.account

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.app.base.BaseActivity
import com.king.template.databinding.RegisterActivityBinding
import com.king.template.util.CheckUtils
import kotlinx.android.synthetic.main.login_activity.*
import kotlinx.android.synthetic.main.register_activity.*
import kotlinx.android.synthetic.main.register_activity.etPassword
import kotlinx.android.synthetic.main.register_activity.etUsername

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class RegisterActivity : BaseActivity<RegisterViewModel, RegisterActivityBinding>(){

    var username : String? = null

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        setToolbarTitle(getString(R.string.register))

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
        setClickRightEyeListener(etPassword)

        username = intent.getStringExtra(Constants.KEY_USERNAME)

        username?.let {
            etUsername.setText(it)
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.register_activity
    }


    //-------------------------------


    private fun clickTermOfService(){
        //TODO 点击“用户服务协议条款”逻辑
        showToast(R.string.tips_user_terms_of_service)
    }

    private fun clickGetCode(){
        //TODO 点击“发送验证码”逻辑
    }

    private fun clickLoginNow(){
        onBackPressed()
//        startLoginActivity(isCode = false)
    }

    private fun clickRegister(){
        if(!checkInput(etUsername,R.string.hint_username)){
            return
        }
        if(!CheckUtils.checkUsername(etUsername.text.toString())){
            showToast(R.string.tips_username_matcher)
            return
        }
        if(!checkInput(etCode,R.string.hint_verify_code)){
            return
        }
        if(!checkInput(etPassword,R.string.hint_password)){
            return
        }


        //TODO 点击“注册”逻辑
        showToast(R.string.register)

        val username = etUsername.text.toString()
        val verifyCode = etCode.text.toString()
        val password = etPassword.text.toString()
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