package com.hi.dhl.action.base

import com.hi.dhl.common.DataManager
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/14
 *     desc  :
 * </pre>
 */
abstract class AbstractAnAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        LogUtils.logI("DataManager init")
        e.project?.let { project ->
            DataManager.init(project)
            action(project)
        }
    }

    abstract fun action(project: Project)
}