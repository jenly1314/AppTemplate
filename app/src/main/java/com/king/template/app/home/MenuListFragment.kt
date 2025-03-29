package com.king.template.app.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseMultiItemAdapter
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.layoutmanager.QuickGridLayoutManager
import com.chad.library.adapter4.viewholder.DataBindingHolder
import com.king.android.ktx.fragment.argument
import com.king.template.BR
import com.king.template.R
import com.king.template.app.base.ListFragment
import com.king.template.data.model.ImgMenu
import com.king.template.databinding.MenuListFragmentBinding
import com.king.template.databinding.RvImgMenuHeadItemBinding
import com.king.template.databinding.RvImgMenuItemBinding
import com.king.template.dict.MenuType
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@AndroidEntryPoint
class MenuListFragment : ListFragment<ImgMenu, MenuListViewModel, MenuListFragmentBinding>() {


    private val menuList by lazy {
        val list: MutableList<ImgMenu> = ArrayList()
        list.add(ImgMenu(MenuType.HEAD, "菜单标题1"))
        list.add(ImgMenu(MenuType.MENU_1, "菜单1", R.drawable.ic_menu_list_item))
        list.add(ImgMenu(MenuType.MENU_2, "菜单2", R.drawable.ic_menu_list_item))
        list.add(ImgMenu(MenuType.MENU_3, "菜单3", R.drawable.ic_menu_list_item))
        list.add(ImgMenu(MenuType.MENU_4, "菜单4", R.drawable.ic_menu_list_item))
        list.add(ImgMenu(MenuType.HEAD, "菜单标题2"))
        list.add(ImgMenu(MenuType.MENU_5, "菜单5", R.drawable.ic_menu_list_item))
        list
    }

    private var text by argument<String>()

    private var showToolbar by argument(defaultValue = true)


    companion object {
        fun newInstance(text: String, showToolbar: Boolean = true): MenuListFragment {
            return MenuListFragment().apply {
                this.text = text
                this.showToolbar = showToolbar
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.menu_list_fragment
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        binding.toolbar.toolbar.isVisible = showToolbar
        binding.toolbar.tvTitle.text = text
    }


    override fun initRecyclerView(rv: RecyclerView) {
        rv.layoutManager = QuickGridLayoutManager(requireContext(), 4)

    }

    override fun isSupportRefresh(): Boolean {
        return false
    }

    override fun isSupportLoadMore(): Boolean {
        return false
    }

    override fun createAdapter(): BaseQuickAdapter<ImgMenu, out RecyclerView.ViewHolder> {
        return object : BaseMultiItemAdapter<ImgMenu>(menuList) {
            init {
                addItemType(
                    itemViewType = ImgMenu.ITEM_HEAD,
                    listener = object :
                        OnMultiItemAdapterListener<ImgMenu, DataBindingHolder<RvImgMenuHeadItemBinding>> {
                        override fun onBind(
                            holder: DataBindingHolder<RvImgMenuHeadItemBinding>,
                            position: Int,
                            item: ImgMenu?
                        ) {
                            holder.binding.apply {
                                setVariable(BR.item, item)
                                executePendingBindings()
                            }
                        }

                        override fun onCreate(
                            context: Context,
                            parent: ViewGroup,
                            viewType: Int
                        ): DataBindingHolder<RvImgMenuHeadItemBinding> {
                            val viewBinding = RvImgMenuHeadItemBinding.inflate(
                                LayoutInflater.from(context),
                                parent,
                                false
                            )
                            return DataBindingHolder(viewBinding)
                        }

                        override fun isFullSpanItem(itemType: Int): Boolean {
                            return true
                        }

                    }).addItemType(
                    itemViewType = ImgMenu.ITEM_MENU,
                    listener = object :
                        OnMultiItemAdapterListener<ImgMenu, DataBindingHolder<RvImgMenuItemBinding>> {
                        override fun onBind(
                            holder: DataBindingHolder<RvImgMenuItemBinding>,
                            position: Int,
                            item: ImgMenu?
                        ) {
                            holder.binding.apply {
                                setVariable(BR.item, item)
                                executePendingBindings()
                            }
                        }

                        override fun onCreate(
                            context: Context,
                            parent: ViewGroup,
                            viewType: Int
                        ): DataBindingHolder<RvImgMenuItemBinding> {
                            val viewBinding = RvImgMenuItemBinding.inflate(
                                LayoutInflater.from(context),
                                parent,
                                false
                            )
                            return DataBindingHolder(viewBinding)
                        }


                    }).onItemViewType { position, list ->
                    list[position].itemType
                }
            }
        }
    }

    override fun smartRefreshLayout(): SmartRefreshLayout {
        return binding.srl
    }

    override fun recyclerView(): RecyclerView {
        return binding.rv
    }


    override fun clickItem(view: View, position: Int) {
        super.clickItem(view, position)
        val data = mAdapter.getItem(position)!!
        when (data.menuType) {
            MenuType.HEAD -> {}
            // TODO 点击菜单示例
            else -> showToast(data.name)
        }
    }


}
