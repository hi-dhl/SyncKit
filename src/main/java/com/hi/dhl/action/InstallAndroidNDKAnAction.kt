package com.hi.dhl.action

import com.hi.dhl.common.R
import com.hi.dhl.action.base.AbstractExecShellAction
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
class InstallAndroidNDKAnAction : AbstractExecShellAction(R.String.ui.actionAndroidNDK) {

    override fun afterActionPerformed(project: Project) {
        execShelLScript(project, R.ShellScript.installAndroidNDK)
    }

}