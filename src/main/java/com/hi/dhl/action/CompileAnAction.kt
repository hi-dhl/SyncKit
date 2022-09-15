package com.hi.dhl.action

import com.hi.dhl.R
import com.hi.dhl.action.base.AbstractAnAction
import com.hi.dhl.console.CommandManager
import com.hi.dhl.console.SyncRunnerConsole
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
class CompileAnAction : AbstractAnAction() {
    val extraCommand = "./gradlew --stop && ./gradlew assembleDebug"

    override fun action(project: Project) {
        try {
            val projectBasePath = project.basePath ?: "./"
            LogUtils.logI("click action path = ${projectBasePath}");
            val commands = StringBuilder()
            CommandManager.compileAndroid(commands, extraCommand, projectBasePath)
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