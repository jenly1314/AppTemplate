package com.king.template.app.home

import android.os.Bundle
import android.util.SparseArray
import android.view.View
import androidx.core.util.valueIterator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.app.base.BaseActivity
import com.king.template.app.base.TabFragment
import com.king.template.app.me.MeFragment
import com.king.template.databinding.HomeActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.NullPointerException

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class HomeActivity : BaseActivity<HomeViewModel, HomeActivityBinding>() {

    private val fragments by lazy {
        SparseArray<Fragment>()
    }

    var lastTime: Long = 0

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        showFragment(0)

    }

    override fun getLayoutId(): Int {
        return R.layout.home_activity
    }

    override fun onBackPressed() {
        if (lastTime < System.currentTimeMillis() - Constants.DOUBLE_CLICK_EXIT_TIME) {
            lastTime = System.currentTimeMillis()
            showToast(R.string.tips_double_click_exit)
            return
        }
        super.onBackPressed()
    }


    private fun hideAllFragment(fragmentTransaction: FragmentTransaction) {
        fragments.valueIterator().forEach {
            fragmentTransaction.hide(it)
        }
    }

    private fun showFragment(position: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        hideAllFragment(fragmentTransaction)
        fragmentTransaction.show(getFragment(fragmentTransaction, position))
        fragmentTransaction.commit()
    }

    private fun getFragment(fragmentTransaction: FragmentTransaction, position: Int): Fragment {
        var fragment: Fragment? = fragments[position]
        if (fragment == null) {
            fragment = createFragment(position)
            fragment?.let {
                fragmentTransaction.add(R.id.fragmentContent, it)
                fragments.put(position, it)
            }
        }
        return fragment!!
    }

    private fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> HomeFragment.newInstance()
            1 -> TabFragment.newInstance(
                getString(R.string.app_name),
                arrayOf("Tab1", "Tab2"),
                showToolbar = true,
                showBack = false
            ) {
                when (it) {
                    0 -> MenuFragment.newInstance("Tab1", false)
                    else -> MenuFragment.newInstance("Tab2", false)
                }
            }
            2 -> MenuFragment.newInstance(getString(R.string.home_menu3))
            3 -> MeFragment.newInstance()
            else -> throw NullPointerException()
        }
        return fragment
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.rbMenu1 -> showFragment(0)
            R.id.rbMenu2 -> showFragment(1)
            R.id.rbMenu3 -> showFragment(2)
            R.id.rbMenu4 -> showFragment(3)
        }
    }

}