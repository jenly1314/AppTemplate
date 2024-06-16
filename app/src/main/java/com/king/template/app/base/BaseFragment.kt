package com.king.template.app.base

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.ViewDataBinding
import com.androidutil.util.StringUtils
import com.king.frame.mvvmframe.base.BaseFragment
import com.king.frame.mvvmframe.base.BaseViewModel
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.app.account.CodeLoginActivity
import com.king.template.app.account.LoginActivity
import com.king.template.app.home.HomeActivity

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class BaseFragment<VM : BaseViewModel, VDB : ViewDataBinding> : BaseFragment<VM, VDB>() {

    override fun initData(savedInstanceState: Bundle?) {

    }

    //-----------------------------------

    fun checkInput(tv: TextView): Boolean {
        return !TextUtils.isEmpty(tv.text)
    }

    fun checkInput(tv: TextView, msgId: Int): Boolean {
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

    fun startActivity(clazz: Class<*>, username: String? = null) {
        val intent = Intent(context, clazz)
        intent.putExtra(Constants.KEY_USERNAME, username)
        startActivity(intent)
    }

    fun startLoginActivity(
        username: String? = null,
        isCode: Boolean = false,
        isAlphaAnim: Boolean = false,
        isClearTask: Boolean = false
    ) {
        val intent = Intent(
            context,
            if (isCode) CodeLoginActivity::class.java else LoginActivity::class.java
        )
        intent.putExtra(Constants.KEY_USERNAME, username)
        intent.putExtra(Constants.KEY_CLEAR_TASK, isClearTask)
        if (isClearTask) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        if (isAlphaAnim) {
            val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                requireContext(),
                R.anim.alpha_in_anim,
                R.anim.app_dialog_scale_out
            )
            startActivity(intent, optionsCompat.toBundle())
        } else {
            startActivity(intent)
        }
    }

    fun startHomeActivity() {
        val intent = Intent(context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(
            requireContext(),
            R.anim.alpha_in_anim,
            R.anim.alpha_out_anim
        )
        startActivity(intent, optionsCompat.toBundle())
    }


    fun startWebActivity(url: String, title: String? = null) {
        val intent = Intent(context, WebActivity::class.java)
        title?.let {
            intent.putExtra(Constants.KEY_TITLE, it)
        }
        intent.putExtra(Constants.KEY_URL, url)
        startActivity(intent)
    }

    //-----------------------------------

}