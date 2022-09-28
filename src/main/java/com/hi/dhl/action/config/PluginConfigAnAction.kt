package com.hi.dhl.action.config

import com.hi.dhl.common.R
import com.hi.dhl.ui.ConfigCommandDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/22
 *     desc  :
 * </pre>
 */
class PluginConfigAnAction : AnAction(R.String.ui.actionPlugin) {

    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { project ->
            ConfigCommandDialog(project).show()
        }
    }

}