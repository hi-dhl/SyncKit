package com.hi.dhl.action

import com.hi.dhl.action.base.AbstractAnAction
import com.hi.dhl.common.DataManager
import com.hi.dhl.console.CommandManager
import com.hi.dhl.utils.LogUtils
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
class StopBuildAnAction: AbstractAnAction() {
    val extraCommand = "./gradlew --stop"
    override fun action(project: Project) {
        val projectBasePath = project.basePath ?: "./"
        LogUtils.logI("click action path = ${projectBasePath}");
        val commands = StringBuilder()
        val projectName = projectBasePath.substring(projectBasePath.lastIndexOf(File.separator) + 1)
        val remoteProjectPath = DataManager.getMachineInfo().remoteRootDir + File.separator + projectName
        CommandManager.execRemoteCommand(commands, remoteProjectPath, extraCommand)
        execSyncRunnerConsole(project, projectBasePath, commands.toString())
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.isEnabled = DataManager.isBuildProject()
    }
}