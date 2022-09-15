package com.hi.dhl.action.base

import com.hi.dhl.common.DataManager
import com.hi.dhl.utils.LogUtils
import com.hi.dhl.utils.MessagesUtils
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
        try {
            e.project?.let { project ->
                val projectBasePath = project.basePath ?: "./"
                if (!DataManager.isInit(projectBasePath)) {
                    MessagesUtils.showMessageWarnDialog(
                        "Not initialized, please click the Sync Init", "Sync Kit"
                    )
                    return
                }
                action(project)
            }
        } catch (e: Exception) {
            LogUtils.logE("exec action fail ${e}");
        }
    }

    abstract fun action(project: Project)
}