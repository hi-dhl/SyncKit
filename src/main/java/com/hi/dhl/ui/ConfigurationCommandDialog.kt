package com.hi.dhl.ui

import com.hi.dhl.common.R
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/10/16
 *     desc  :
 * </pre>
 */
class ConfigurationCommandDialog(
    val project: Project,
    val commandCallback: (command: String?) -> Unit
) : DialogWrapper(project, false) {

    val configurationCommandForm = ConfigurationCommandForm()

    init {
        setTitle(R.String.ui.actionRemoteCommand)
        init()
    }

    override fun doOKAction() {
        super.doOKAction()
        commandCallback(configurationCommandForm.tfRemoteCommand.text)
    }

    override fun createCenterPanel(): JComponent? {
        return configurationCommandForm.panel
    }
}