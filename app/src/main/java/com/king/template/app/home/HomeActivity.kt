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
import com.king.template.dict.HomeMenu
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
        showFragment(HomeMenu.MENU1)

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

    private fun showFragment(@HomeMenu menu: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        hideAllFragment(fragmentTransaction)
        fragmentTransaction.show(getFragment(fragmentTransaction, menu))
        fragmentTransaction.commit()
    }

    private fun getFragment(fragmentTransaction: FragmentTransaction,@HomeMenu menu: Int): Fragment {
        var fragment: Fragment? = fragments[menu]
        if (fragment == null) {
            fragment = createFragment(menu)
            fragment?.let {
                fragmentTransaction.add(R.id.fragmentContent, it)
                fragments.put(menu, it)
            }
        }
        return fragment!!
    }

    /**
     * 创建 Fragment
     */
    private fun createFragment(@HomeMenu menu: Int): Fragment = when (menu) {
        // TODO 只需修改此处，改为对应的 Fragment
        HomeMenu.MENU1 -> HomeFragment.newInstance()
        HomeMenu.MENU2 -> TabFragment.newInstance(
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
        HomeMenu.MENU3 -> MenuListFragment.newInstance(getString(R.string.home_menu3))
        HomeMenu.MENU4 -> MeFragment.newInstance()
        else -> throw NullPointerException()
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.rbMenu1 -> showFragment(HomeMenu.MENU1)
            R.id.rbMenu2 -> showFragment(HomeMenu.MENU2)
            R.id.rbMenu3 -> showFragment(HomeMenu.MENU3)
            R.id.rbMenu4 -> showFragment(HomeMenu.MENU4)
        }
    }

}