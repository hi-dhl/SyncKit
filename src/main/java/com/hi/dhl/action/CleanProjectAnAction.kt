package com.hi.dhl.action

import com.hi.dhl.action.base.AbstractExecShellAction
import com.hi.dhl.common.R
import com.hi.dhl.console.CommandManager
import com.hi.dhl.utils.FileUtils
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.project.Project
import java.io.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/15
 *     desc  :
 * </pre>
 */
class CleanProjectAnAction : AbstractExecShellAction(R.String.ui.actionCleanProject) {

    override fun afterActionPerformed(project: Project) {
        val src = FileUtils.getShellScriptPath(projectBasePath, R.ShellScript.cleanProject)
        val dest = FileUtils.getShellScriptDirPath(projectBasePath)
        if (!FileUtils.isExists(src)) {
            FileUtils.copyScriptFile(R.ShellScript.cleanProject, File(dest))
        }

        if (FileUtils.isExists(src)) {
            execShelLScript(project, R.ShellScript.cleanProject)
        }else{
            val extraCommand = "./gradlew --stop; rm -rf ~/.gradle/caches/build-cache-*; ./gradlew clean"
            val commands = StringBuilder()
            val projectName = projectBasePath.substring(projectBasePath.lastIndexOf(File.separator) + 1)
            val remoteProjectPath = remoteMachineInfo.remoteRootDir + File.separator + projectName
            CommandManager.execRemoteCommand(commands, remoteProjectPath, extraCommand, remoteMachineInfo)
            val consoleTitle = "${R.String.projectTitle} [ clean ] "
            execSyncRunnerConsole(project, projectBasePath, commands.toString(), consoleTitle)
        }

    }

}