package com.hi.dhl.action.config

import com.hi.dhl.ui.ConfigCommandDialog
import com.hi.dhl.ui.ConfigCommandForm
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.popup.JBPopupFactory
import java.awt.Dimension
import javax.swing.JLabel

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/22
 *     desc  :
 * </pre>
 */
class ConfigCommandAnAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { project ->
            ConfigCommandDialog(project).show()
        }
    }

}