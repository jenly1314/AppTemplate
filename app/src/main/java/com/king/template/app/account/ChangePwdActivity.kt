package com.king.template.app.account

import android.os.Bundle
import android.view.View
import com.king.template.R
import com.king.template.app.base.BaseActivity
import com.king.template.databinding.ChangePwdActivityBinding
import com.king.template.extension.disableCopyAndPaste
import com.king.template.util.CheckUtils
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class ChangePwdActivity : BaseActivity<PasswordViewModel, ChangePwdActivityBinding>(){

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        setToolbarTitle(getString(R.string.change_password))

        setClickRightEyeListener(viewDataBinding.etOldPassword)
        setClickRightEyeListener(viewDataBinding.etNewPassword)
        setClickRightEyeListener(viewDataBinding.etConfirmPassword)

        viewDataBinding.etOldPassword.disableCopyAndPaste()
        viewDataBinding.etNewPassword.disableCopyAndPaste()
        viewDataBinding.etConfirmPassword.disableCopyAndPaste()

        viewModel.liveDataUpdatePassword.observe(this) {
            if (it) {
                // TODO 密码修改成功后的逻辑处理
                showToast(R.string.successfully_modified)
                finish()
            }
        }

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

        // TODO 点击“修改密码”逻辑
        Timber.d(getString(R.string.change_password))

        var oldPwd = viewDataBinding.etOldPassword.text.toString()

        viewModel.updatePassword(oldPwd,newPwd)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.btnChangePassword -> clickChangePassword()
        }
    }
}