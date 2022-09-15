package com.hi.dhl.action

import com.hi.dhl.R
import com.hi.dhl.action.base.AbstractAnAction
import com.hi.dhl.console.CommandManager
import com.hi.dhl.console.SyncRunnerConsole
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
class InstallApkAnAction : AbstractAnAction() {

    override fun action(project: Project) {
        try {
            val projectBasePath = project.basePath ?: "./"
            LogUtils.logI("click action path = ${projectBasePath}");

            val commands = StringBuilder()
            val shellInstallApkPath = FileUtils.getShellScriptPath(projectBasePath, R.ShellScript.installApk)
            if (FileUtils.isExists(shellInstallApkPath)) {
                CommandManager.execLocalCommand(
                    commands,
                    "chmod 777 ${shellInstallApkPath} && bash ${shellInstallApkPath}"
                )

                SyncRunnerConsole(
                    project = project,
                    consoleTitle = R.String.projectTitle,
                    workingDir = projectBasePath,
                    command = commands.toString()
                ).initAndRun()

            } else {
                LogUtils.logE("file is not exists ${FileUtils.getShellScriptPath(projectBasePath, R.ShellScript.installApk)}")
            }

        } catch (e: Exception) {
            LogUtils.logE("exec action fail ${e}");
        }
    }

}