package com.hi.dhl.action

import com.hi.dhl.action.base.AbstractExecShellAction
import com.hi.dhl.common.R
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/15
 *     desc  :
 * </pre>
 */
class CleanProjectAnAction : AbstractExecShellAction(R.String.ui.actionCleanProject) {

    override fun afterActionPerformed(project: Project) {
        execShelLScript(project, R.ShellScript.cleanProject)
    }

}