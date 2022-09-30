package com.hi.dhl.action

import com.hi.dhl.common.DataManager
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.hi.dhl.common.R
import com.hi.dhl.common.SyncContentProvide

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/15
 *     desc  :
 * </pre>
 */
class InitializationAnAction : AnAction(R.String.ui.actionInitialization) {

    override fun actionPerformed(e: AnActionEvent) {
        try {
            e.project?.let { project ->
                val syncContentProvide = SyncContentProvide(project)
                syncContentProvide.deleteSyncRootDir()
                syncContentProvide.initData()
            }
        }catch (e: Exception){
            LogUtils.logE("exec action fail ${e}");
        }

    }

}