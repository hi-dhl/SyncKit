package com.hi.dhl.action

import com.hi.dhl.R
import com.hi.dhl.console.CommandManager
import com.hi.dhl.console.SyncRunnerConsole
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
class CompileAnAction : AnAction() {
    val command = "./gradlew --stop && ./gradlew assembleDebug"

    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { project ->
            try {
                val projectBasePath = project.basePath ?: "./"
                LogUtils.logI("click action path = ${projectBasePath}");
                val build = StringBuilder()
                CommandManager.compileAndroid(build, command, projectBasePath)
                SyncRunnerConsole(
                    project = project,
                    consoleTitle = R.String.projectTitle,
                    workingDir = projectBasePath,
                    command = build.toString()
                ).initAndRun()
            } catch (e: Exception) {
                LogUtils.logE("exec command fail ${e}");
            }
        }

    }


}