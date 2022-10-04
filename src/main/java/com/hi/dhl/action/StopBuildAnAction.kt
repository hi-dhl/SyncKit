package com.hi.dhl.action

import com.hi.dhl.action.base.AbstractAnAction
import com.hi.dhl.console.CommandManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.konan.file.File
import com.hi.dhl.common.R
/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/15
 *     desc  :
 * </pre>
 */
class StopBuildAnAction : AbstractAnAction(R.String.ui.actionStopBuildProject) {

    override fun afterActionPerformed(project: Project) {
        val extraCommand = "./gradlew -stop"
        val commands = StringBuilder()
        val projectName = projectBasePath.substring(projectBasePath.lastIndexOf(File.separator) + 1)
        val remoteProjectPath = remoteMachineInfo.remoteRootDir + File.separator + projectName
        CommandManager.execRemoteCommand(commands, remoteProjectPath, extraCommand, remoteMachineInfo)
        execSyncRunnerConsole(project, projectBasePath, commands.toString())
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
//        e.presentation.isEnabled = DataManager.isBuildProject()
    }
}