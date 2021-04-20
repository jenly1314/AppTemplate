package com.king.template.app.account

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.king.template.R
import com.king.template.app.base.BaseActivity
import com.king.template.databinding.ChangePwdActivityBinding
import com.king.template.util.CheckUtils
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class ChangePwdActivity : BaseActivity<PasswordViewModel, ChangePwdActivityBinding>(){

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        setToolbarTitle(getString(R.string.change_password))

        viewDataBinding.etOldPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewDataBinding.etOldPassword.isSelected = !TextUtils.isEmpty(s)
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
        setClickRightClearListener(viewDataBinding.etOldPassword)
        setClickRightClearListener(viewDataBinding.etNewPassword)
        setClickRightClearListener(viewDataBinding.etConfirmPassword)

    }

    override fun getLayoutId(): Int {
        return R.layout.change_pwd_activity
    }

    //-------------------------------


    private fun clickChangePassword(){
        if(!checkInput(viewDataBinding.etOldPassword,R.string.hint_old_password)){
            return
        }
        if(!checkInput(viewDataBinding.etNewPassword,R.string.hint_new_password)){
            return
        }
        if(!CheckUtils.checkPassword(viewDataBinding.etNewPassword.text.toString())){
            showToast(R.string.tips_password_matcher)
            return
        }
        if(!checkInput(viewDataBinding.etConfirmPassword,R.string.hint_confirm_new_password)){
            return
        }
        var newPwd = viewDataBinding.etNewPassword.text.toString()
        if(newPwd != viewDataBinding.etConfirmPassword.text.toString()){
            showToast(R.string.hint_new_password_not_match)
            return
        }

        //TODO 点击“修改密码”逻辑
        showToast(R.string.change_password)

        var oldPwd = viewDataBinding.etOldPassword.text.toString()

        viewModel.changePwd(oldPwd,newPwd)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.btnChangePassword -> clickChangePassword()
        }
    }
}