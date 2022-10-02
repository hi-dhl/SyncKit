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
class SSHAnAction : AbstractExecShellAction(R.String.ui.actionInstallSSHPub) {

    override fun action(project: Project) {
        execShelLScript(project, R.ShellScript.installSSHPub)
    }

}