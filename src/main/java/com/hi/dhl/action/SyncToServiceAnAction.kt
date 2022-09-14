package com.hi.dhl.action

import com.hi.dhl.R
import com.hi.dhl.common.DataManager
import com.hi.dhl.console.CommandManager
import com.hi.dhl.console.SyncRunnerConsole
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.konan.file.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
class SyncToServiceAnAction : AbstractAnAction() {

    override fun action(project: Project) {
        try {
            val projectBasePath = project.basePath ?: "./"
            LogUtils.logI("click action path = ${projectBasePath}");
            val commands = StringBuilder()
            val projectName = projectBasePath.substring(projectBasePath.lastIndexOf(File.separator) + 1)
            val remoteProjectPath = DataManager.getMachineInfo().remoteRootDir + File.separator + projectName
            CommandManager.syncLocalToRemote(commands, remoteProjectPath)

            SyncRunnerConsole(
                project = project,
                consoleTitle = R.String.projectTitle,
                workingDir = projectBasePath,
                command = commands.toString()
            ).initAndRun()
        } catch (e: Exception) {
            LogUtils.logE("exec action fail ${e}");
        }
    }

}