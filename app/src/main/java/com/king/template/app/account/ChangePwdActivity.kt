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
import kotlinx.android.synthetic.main.change_pwd_activity.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ChangePwdActivity : BaseActivity<PasswordViewModel, ChangePwdActivityBinding>(){

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        setToolbarTitle(getString(R.string.change_password))

        etOldPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etOldPassword.isSelected = !TextUtils.isEmpty(s)
            }

        })
        etNewPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etNewPassword.isSelected = !TextUtils.isEmpty(s)
            }

        })
        etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etConfirmPassword.isSelected = !TextUtils.isEmpty(s)
            }

        })
        setClickRightClearListener(etOldPassword)
        setClickRightClearListener(etNewPassword)
        setClickRightClearListener(etConfirmPassword)

    }

    override fun getLayoutId(): Int {
        return R.layout.change_pwd_activity
    }

    //-------------------------------


    private fun clickChangePassword(){
        if(!checkInput(etOldPassword,R.string.hint_old_password)){
            return
        }
        if(!checkInput(etNewPassword,R.string.hint_new_password)){
            return
        }
        if(!CheckUtils.checkPassword(etNewPassword.text.toString())){
            showToast(R.string.tips_password_matcher)
            return
        }
        if(!checkInput(etConfirmPassword,R.string.hint_confirm_new_password)){
            return
        }
        var newPwd = etNewPassword.text.toString()
        if(newPwd != etConfirmPassword.text.toString()){
            showToast(R.string.hint_new_password_not_match)
            return
        }

        //TODO 点击“修改密码”逻辑
        showToast(R.string.change_password)

        var oldPwd = etOldPassword.text.toString()

        viewModel.changePwd(oldPwd,newPwd)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.btnChangePassword -> clickChangePassword()
        }
    }
}