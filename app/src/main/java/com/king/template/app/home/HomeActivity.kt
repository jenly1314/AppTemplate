package com.king.template.app.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.king.template.R
import com.king.template.app.Constants
import com.king.template.app.base.BaseActivity
import com.king.template.app.me.MeFragment
import com.king.template.app.base.TabFragment
import com.king.template.databinding.HomeActivityBinding

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeActivity : BaseActivity<HomeViewModel, HomeActivityBinding>(){

    var fragment1 : Fragment? = null
    var fragment2 : Fragment? = null
    var fragment3 : Fragment? = null
    var fragment4 : Fragment? = null

    var lastTime : Long = 0

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        showFragment { getFragment1(it) }
    }

    override fun getLayoutId(): Int {
        return R.layout.home_activity
    }

    override fun onBackPressed() {
        if(lastTime < System.currentTimeMillis() - Constants.DOUBLE_CLICK_EXIT_TIME){
            lastTime = System.currentTimeMillis()
            showToast(R.string.tips_double_click_exit)
            return
        }
        super.onBackPressed()
    }


    private fun hideAllFragment(fragmentTransaction: FragmentTransaction) {
        hideFragment(fragmentTransaction, fragment1)
        hideFragment(fragmentTransaction, fragment2)
        hideFragment(fragmentTransaction, fragment3)
        hideFragment(fragmentTransaction, fragment4)
    }

    private fun hideFragment(fragmentTransaction: FragmentTransaction, fragment: Fragment?) {
        fragment?.let {
            fragmentTransaction.hide(it)
        }
    }

    private fun showFragment(block:(FragmentTransaction) -> Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        hideAllFragment(fragmentTransaction)
        fragmentTransaction.show(block(fragmentTransaction))
        fragmentTransaction.commit()
    }

    private fun getFragment1(fragmentTransaction: FragmentTransaction): Fragment{
        if(fragment1 == null){
            //TODO 替换成菜单对应的Fragment
            fragment1 = HomeFragment.newInstance()
            fragment1?.let{
                fragmentTransaction.add(R.id.fragmentContent,it)
            }
        }
        return fragment1!!
    }

    private fun getFragment2(fragmentTransaction: FragmentTransaction): Fragment{
        if(fragment2 == null){
            //TODO 替换成菜单对应的Fragment
            fragment2 = TabFragment.newInstance( {
                when(it){
                    0 -> MenuFragment.newInstance("Tab1",false)
                    else -> MenuFragment.newInstance("Tab2",false)
                }
            },arrayOf("Tab1","Tab2"),true)
            fragment2?.let{
                fragmentTransaction.add(R.id.fragmentContent,it)
            }
        }
        return fragment2!!
    }

    private fun getFragment3(fragmentTransaction: FragmentTransaction): Fragment{
        if(fragment3 == null){
            //TODO 替换成菜单对应的Fragment

            fragment3 = MenuFragment.newInstance(getString(R.string.home_menu3))
            fragment3?.let{
                fragmentTransaction.add(R.id.fragmentContent,it)
            }
        }
        return fragment3!!
    }

    private fun getFragment4(fragmentTransaction: FragmentTransaction): Fragment{
        if(fragment4 == null){
            //TODO 替换成菜单对应的Fragment
            fragment4 = MeFragment.newInstance()
            fragment4?.let{
                fragmentTransaction.add(R.id.fragmentContent,it)
            }
        }
        return fragment4!!
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.rbMenu1 -> showFragment {getFragment1(it)}
            R.id.rbMenu2 -> showFragment {getFragment2(it)}
            R.id.rbMenu3 -> showFragment {getFragment3(it)}
            R.id.rbMenu4 -> showFragment {getFragment4(it)}
        }
    }

}