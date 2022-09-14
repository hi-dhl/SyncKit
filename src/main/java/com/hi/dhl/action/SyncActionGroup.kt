package com.hi.dhl.action

import com.hi.dhl.common.DataManager
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
public class SyncActionGroup : DefaultActionGroup() {
//    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
//        LogUtils.logI("SyncActionGroup init")
//        e?.project?.let { project ->
//            DataManager.init(project)
//        }
//        return super.getChildren(e)
//    }
}