package com.hi.dhl.action

import com.hi.dhl.action.base.AbstractAnAction
import com.hi.dhl.console.CommandManager
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
class BuildProjectAnAction : AbstractAnAction() {
    val extraCommand = "./gradlew assembleDebug"

    override fun action(project: Project) {
        val projectBasePath = project.basePath ?: "./"
        LogUtils.logI("click action path = ${projectBasePath}");
        val commands = StringBuilder()
        CommandManager.compileAndroid(commands, extraCommand, projectBasePath)
        execSyncRunnerConsole(project, projectBasePath, commands.toString())
    }

}