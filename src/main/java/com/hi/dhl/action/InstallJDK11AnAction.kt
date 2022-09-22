package com.hi.dhl.action

import com.hi.dhl.R
import com.hi.dhl.action.base.AbstractExecShellAction
import com.hi.dhl.common.SyncContentProvide
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
class InstallJDK11AnAction : AbstractExecShellAction() {

    override fun action(project: Project) {
        execShelLScript(project, R.ShellScript.installJDK11)
    }

}