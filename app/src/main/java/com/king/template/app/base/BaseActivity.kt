package com.king.template.app.base

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.king.base.util.StringUtils
import com.king.base.util.ToastUtils
import com.king.frame.mvvmframe.base.BaseActivity
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.app.account.CodeLoginActivity
import com.king.template.app.account.LoginActivity
import com.king.template.app.home.HomeActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class BaseActivity<VM : BaseViewModel<out BaseModel>,VDB : ViewDataBinding> : BaseActivity<VM,VDB>(){

    val rxPermission by lazy { RxPermissions(this) }

    fun setToolbarTitle(title: String?){
        title?.let {
            tvTitle.text = it
        }
    }


    override fun initData(savedInstanceState: Bundle?) {
        registerMessageEvent {
            ToastUtils.showToast(context,it)
        }
    }

    //-----------------------------------

    fun showToast(@StringRes resId: Int){
        Toasty.normal(context,resId).show()
    }

    fun showToast(text: CharSequence){
        Toasty.normal(context,text).show()
    }

    //-----------------------------------

    fun checkInput(tv: TextView): Boolean {
        return !TextUtils.isEmpty(tv.text)
    }

    fun checkInput(tv: TextView,msgId: Int): Boolean {
        if (TextUtils.isEmpty(tv.text)) {
            if (msgId != 0) {
                showToast(msgId)
            }
            return false
        }
        return true
    }

    fun checkInput(tv: TextView, msg: CharSequence? = null): Boolean {
        if (TextUtils.isEmpty(tv.text)) {
            if (StringUtils.isNotBlank(msg)) {
                showToast(msg!!)
            }
            return false
        }
        return true
    }

    //-----------------------------------

    fun setClickRightClearListener(tv: TextView) {
        tv.setOnTouchListener { v: View?, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_UP -> return@setOnTouchListener clickRightClear(tv, event)
            }
            false
        }
    }

    private fun clickRightClear(tv: TextView, event: MotionEvent): Boolean {
        val drawableRight = tv.compoundDrawables[2]
        if (drawableRight != null && event.rawX >= tv.right - drawableRight.bounds.width()) {
            tv.text = null
            return true
        }
        return false
    }


    fun setClickRightEyeListener(et: EditText) {
        et.setOnTouchListener { v: View?, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    val drawableRight = et.compoundDrawables[2]
                    if (drawableRight != null && event.rawX >= et.right - drawableRight.bounds.width()) {
                        clickEye(et)
                        return@setOnTouchListener true
                    }
                    return@setOnTouchListener false
                }
            }
            false
        }
    }

    private fun clickEye(etPassword: EditText) {
        if (etPassword.inputType != InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) { //隐藏密码
            etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            val drawableRight = ContextCompat.getDrawable(context, R.drawable.ic_password_hide)
            val compoundDrawable = etPassword.compoundDrawables
            etPassword.setCompoundDrawablesWithIntrinsicBounds(compoundDrawable[0], compoundDrawable[1], drawableRight, compoundDrawable[3])
        } else { //显示密码
            etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            val drawableRight = ContextCompat.getDrawable(context, R.drawable.ic_password_show)
            val compoundDrawable = etPassword.compoundDrawables
            etPassword.setCompoundDrawablesWithIntrinsicBounds(compoundDrawable[0], compoundDrawable[1], drawableRight, compoundDrawable[3])
        }
    }


    //-----------------------------------

    fun startActivity(clazz: Class<*>,username: String? = null){
        var intent = newIntent(clazz)
        intent.putExtra(Constants.KEY_USERNAME,username)
        startActivity(intent)
    }

    fun startLoginActivity(username: String? = null,isCode: Boolean = false,isAlphaAnim: Boolean = false,isClearTask: Boolean = false) {
        val intent = Intent(context, if (isCode) CodeLoginActivity::class.java else LoginActivity::class.java)
        intent.putExtra(Constants.KEY_USERNAME, username)
        intent.putExtra(Constants.KEY_CLEAR_TASK, isClearTask)
        if (isClearTask) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        if(isAlphaAnim){
            val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(context, R.anim.alpha_in_anim, R.anim.app_dialog_out)
            startActivity(intent, optionsCompat.toBundle())
        }else{
            startActivity(intent)
        }
    }

    fun startHomeActivity(){
        val intent = Intent(context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(context, R.anim.alpha_in_anim, R.anim.alpha_out_anim)
        startActivity(intent, optionsCompat.toBundle())
    }

    fun startWebActivity(url: String,title: String? = null){
        var intent = Intent(context,WebActivity::class.java)
        title?.let {
            intent.putExtra(Constants.KEY_TITLE,it)
        }
        intent.putExtra(Constants.KEY_URL,url)
        startActivity(intent)
    }

    //-----------------------------------


    open fun onClick(v : View){
        if(v.id == R.id.ivLeft){
            onBackPressed()
        }
    }

}