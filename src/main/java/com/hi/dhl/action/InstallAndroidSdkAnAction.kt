package com.hi.dhl.action

import com.hi.dhl.R
import com.hi.dhl.action.base.ExecShellAction
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
class InstallAndroidSdkAnAction : ExecShellAction() {

    override fun action(project: Project) {
        execShelLScript(project, R.ShellScript.installAndroidSdk)
    }

}