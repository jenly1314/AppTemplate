package com.king.template.template

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.king.template.R
import com.king.template.app.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class TemplateDialogFragment : BaseDialogFragment<TemplateViewModel, ViewDataBinding>() {

    companion object {
        fun newInstance(): TemplateDialogFragment {
            return TemplateDialogFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.temp_dialog_fragment
    }
}
