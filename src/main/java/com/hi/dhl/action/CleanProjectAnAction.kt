package com.hi.dhl.action

import com.hi.dhl.action.base.AbstractAnAction
import com.hi.dhl.common.R
import com.hi.dhl.console.CommandManager
import com.intellij.openapi.project.Project
import java.io.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/15
 *     desc  :
 * </pre>
 */
class CleanProjectAnAction : AbstractAnAction(R.String.ui.actionCleanProject) {

    override fun afterActionPerformed(project: Project) {
        val extraCommand = "./gradlew --stop; rm -rf ~/.gradle/caches/build-cache-*; rm -rf ~/.gradle/caches/transforms-*; ./gradlew clean"
        val commands = StringBuilder()
        val projectName = projectBasePath.substring(projectBasePath.lastIndexOf(File.separator) + 1)
        val remoteProjectPath = remoteMachineInfo.remoteRootDir + File.separator + projectName
        CommandManager.execRemoteCommand(commands, remoteProjectPath, extraCommand, remoteMachineInfo)
        val consoleTitle = "${R.String.projectTitle} [ clean ] "
        execSyncRunnerConsole(project, projectBasePath, commands.toString(), consoleTitle)
    }

}