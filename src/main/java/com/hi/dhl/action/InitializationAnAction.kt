package com.hi.dhl.action

import com.hi.dhl.common.DataManager
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/15
 *     desc  :
 * </pre>
 */
class InitializationAnAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        try {
            e.project?.let { project ->
                DataManager.deleteSyncRootDir(project)
                DataManager.init(project)
            }
        }catch (e: Exception){
            LogUtils.logE("exec action fail ${e}");
        }

    }

}