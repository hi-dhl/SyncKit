package com.hi.dhl.action

import com.hi.dhl.common.R
import com.hi.dhl.action.base.AbstractAnAction
import com.hi.dhl.console.CommandManager
import com.hi.dhl.utils.FileUtils
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.project.Project
/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
class InstallApkAnAction : AbstractAnAction(R.String.ui.actionInstallApk) {

    override fun action(project: Project) {
        val commands = StringBuilder()
        val shellInstallApkPath = FileUtils.getShellScriptPath(projectBasePath, R.ShellScript.installApk)
        if (FileUtils.isExists(shellInstallApkPath)) {
            var execShellScript = ""
            if (shellInstallApkPath.contains("apk") && !remoteMachineInfo.launchActivity.isNullOrEmpty()) {
                execShellScript = "chmod 777 ${shellInstallApkPath} && bash ${shellInstallApkPath} ${remoteMachineInfo.launchActivity} "
            } else {
                execShellScript = "chmod 777 ${shellInstallApkPath} && bash ${shellInstallApkPath} "
            }
            CommandManager.execLocalCommand( commands, execShellScript)
            execSyncRunnerConsole(project, projectBasePath, commands.toString())
        } else {
            LogUtils.logE(
                "file is not exists ${
                    FileUtils.getShellScriptPath(
                        projectBasePath,
                        R.ShellScript.installApk
                    )
                }"
            )
        }
    }

}