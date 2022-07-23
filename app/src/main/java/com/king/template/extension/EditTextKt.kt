package com.king.template.extension

import android.os.Build
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */

@JvmOverloads
fun EditText.disableCopyAndPaste(disablePaste: Boolean = true) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val callback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                //创建菜单项ActionMode，返回false表示不创建，事件结束
                return false
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                //菜单项更新，返回false表示没有更新，事件结束
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                //选择菜单项的某一项，返回true表示拦截点击事件，事件结束
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                //菜单项ActionMode销毁，什么也不做
            }
        }
        //禁用复制
        this.customSelectionActionModeCallback = callback

        //禁用粘贴
        if (disablePaste)
            this.customInsertionActionModeCallback = callback
    } else {
        //通过反射来禁用复制和粘贴
        disableCopyAndPasteByReflect(disablePaste)
    }
}

private fun EditText.disableCopyAndPasteByReflect(disablePaste: Boolean) {
    try {
        //因为Editor包含两个boolean字段： mInsertionControllerEnabled、mSelectionControllerEnabled，
        // 其中mInsertionControllerEnabled表示粘贴，为false就不起作用
        // 其中mSelectionControllerEnabled表示选中文本，为false就不起作用
        //所以，获取Editor之后，将这两个boolean字段设为false就可以禁用复制和粘贴

        //1、获取EditText的Editor实例
        val editorField = TextView::class.java.getDeclaredField("mEditor")
        editorField.isAccessible = true
        val editorInstance = editorField.get(this)

        val editorClass = Class.forName("android.widget.Editor")
        //2、获取Editor的mSelectionControllerEnabled字段，设为false禁用复制
        val selectField = editorClass.getDeclaredField("mSelectionControllerEnabled")
        selectField.isAccessible = true
        selectField.set(editorInstance, false)
        //3、获取Editor的mInsertionControllerEnabled字段，设为false禁用粘贴
        if (disablePaste) {
            val insertField = editorClass.getDeclaredField("mInsertionControllerEnabled")
            insertField.isAccessible = true
            insertField.set(editorInstance, false)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}