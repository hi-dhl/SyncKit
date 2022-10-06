package com.hi.dhl.action

import com.hi.dhl.action.base.AbstractAnAction
import com.hi.dhl.action.listener.BuildProcessListener
import com.hi.dhl.common.R
import com.hi.dhl.common.SyncContentProvide
import com.hi.dhl.console.CommandManager
import com.intellij.execution.process.ProcessEvent
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.konan.file.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/15
 *     desc  :
 * </pre>
 */
class StopBuildAnAction : AbstractAnAction(R.String.ui.actionStopBuildProject) {

    override fun afterActionPerformed(project: Project) {
        val extraCommand = "./gradlew --stop"
        val commands = StringBuilder()
        val projectName = projectBasePath.substring(projectBasePath.lastIndexOf(File.separator) + 1)
        val remoteProjectPath = remoteMachineInfo.remoteRootDir + File.separator + projectName
        CommandManager.execRemoteCommand(commands, remoteProjectPath, extraCommand, remoteMachineInfo)
        val consoleTitle = "${R.String.projectTitle} [ stop ]"
        execSyncRunnerConsole(project, projectBasePath, commands.toString(), consoleTitle,object : BuildProcessListener {
            override fun onStart(startTime: Long) {

            }

            override fun onStop(processEvent: ProcessEvent, endTime: Long) {
                SyncContentProvide.getInstance(project).getRunnerConsole().forEach {
                    it.processHandler.destroyProcess()
                }
            }

        })
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
    }
}