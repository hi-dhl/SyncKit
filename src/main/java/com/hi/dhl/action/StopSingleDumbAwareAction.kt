package com.hi.dhl.action

import com.intellij.execution.process.ProcessHandler
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import javax.swing.Icon

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/10/4
 *     desc  :
 * </pre>
 */
class StopSingleDumbAwareAction(
    val processHandler: ProcessHandler,
    text: String,
    icon: Icon
) : DumbAwareAction(text, text, icon) {
    override fun actionPerformed(e: AnActionEvent) {
        processHandler.destroyProcess()
    }
}