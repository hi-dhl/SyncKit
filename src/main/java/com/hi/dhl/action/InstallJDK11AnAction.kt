package com.hi.dhl.action

import com.hi.dhl.Common
import com.hi.dhl.R
import com.hi.dhl.common.DataManager
import com.hi.dhl.console.CommandManager
import com.hi.dhl.console.SyncRunnerConsole
import com.hi.dhl.utils.FileUtils
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.konan.file.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
class InstallJDK11AnAction : AbstractAnAction() {

    override fun action(project: Project) {
        try {
            val projectBasePath = project.basePath ?: "./"
            LogUtils.logI("click action project path = ${projectBasePath}")

            val projectName = projectBasePath.substring(projectBasePath.lastIndexOf(File.separator) + 1)
            val remoteProjectPath = DataManager.getMachineInfo().remoteRootDir + File.separator + projectName

            val commands = StringBuilder()
            val shellScriptPath = projectBasePath + File.separator + Common.syncConfigRootDir + File.separator + R.ShellScript.installJDK11
            LogUtils.logI("shellScriptPath path = ${shellScriptPath}")
            if (FileUtils.isExists(shellScriptPath)) {
                CommandManager.execRemoteSellScript(commands, shellScriptPath, remoteProjectPath)

                SyncRunnerConsole(
                    project = project,
                    consoleTitle = R.String.projectTitle,
                    workingDir = projectBasePath,
                    command = commands.toString()
                ).initAndRun()
            } else {
                LogUtils.logE("file not exist ${shellScriptPath}")
            }

        } catch (e: Exception) {
            LogUtils.logE("exec action fail ${e}");
        }
    }

}