package com.king.template.app.account

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.app.base.BaseActivity
import com.king.template.databinding.ResetPwdActivityBinding
import com.king.template.util.CheckUtils
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class ResetPwdActivity : BaseActivity<PasswordViewModel, ResetPwdActivityBinding>(){

    var username : String? = null

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        setToolbarTitle(getString(R.string.reset_password))

        viewDataBinding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewDataBinding.etUsername.isSelected = !TextUtils.isEmpty(s)
            }

        })
        viewDataBinding.etNewPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewDataBinding.etNewPassword.isSelected = !TextUtils.isEmpty(s)
            }

        })
        viewDataBinding.etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewDataBinding.etConfirmPassword.isSelected = !TextUtils.isEmpty(s)
            }

        })
        setClickRightClearListener(viewDataBinding.etUsername)
        setClickRightClearListener(viewDataBinding.etNewPassword)
        setClickRightClearListener(viewDataBinding.etConfirmPassword)

        username = intent.getStringExtra(Constants.KEY_USERNAME)

        username?.let {
            viewDataBinding.etUsername.setText(it)
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.reset_pwd_activity
    }

    //-------------------------------

    private fun clickReset(){
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
        if(!checkInput(viewDataBinding.etNewPassword,R.string.hint_password)){
            return
        }
        if(!CheckUtils.checkPassword(viewDataBinding.etNewPassword.text.toString())){
            showToast(R.string.tips_password_matcher)
            return
        }

        //TODO 点击“重置密码”逻辑
        showToast(R.string.reset_password)

        val username = viewDataBinding.etUsername.text.toString()
        val verifyCode = viewDataBinding.etCode.text.toString()
        val password = viewDataBinding.etNewPassword.text.toString()
        viewModel.resetPwd(username,verifyCode,password)

    }

    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.btnReset -> clickReset()
        }
    }
}