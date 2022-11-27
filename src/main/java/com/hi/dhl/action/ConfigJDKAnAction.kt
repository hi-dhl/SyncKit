package com.hi.dhl.action

import com.hi.dhl.common.R
import com.hi.dhl.action.base.AbstractExecShellAction
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/11/27
 *     desc  :
 * </pre>
 */
class ConfigJDKAnAction : AbstractExecShellAction(R.String.ui.actionConfigJDK) {

    override fun afterActionPerformed(project: Project) {
        execShelLScript(project, R.ShellScript.configJDK)
    }

}