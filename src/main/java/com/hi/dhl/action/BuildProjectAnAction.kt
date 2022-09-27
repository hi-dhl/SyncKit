package com.hi.dhl.action

import com.hi.dhl.action.base.AbstractAnAction
import com.hi.dhl.common.DataManager
import com.hi.dhl.common.SyncContentProvide
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
    var extraCommand = "./gradlew "

    override fun action(project: Project) {
        val projectBasePath = project.basePath ?: "./"
        LogUtils.logI("click action path = ${projectBasePath}");

        val remoteMachineInfo = SyncContentProvide.getInstance(project).readSyncServiceConfig()
        if (remoteMachineInfo.remoteBuildCommand.isNullOrEmpty()) {

        }

        extraCommand += remoteMachineInfo.remoteBuildCommand.toString()

        val commands = StringBuilder()
        CommandManager.compileAndroid(commands, extraCommand, projectBasePath, remoteMachineInfo)
        execSyncRunnerConsole(project, projectBasePath, commands.toString(), object : BuildProcessListener {
            override fun onStart() {
                DataManager.setBuildProject(true)
            }

            override fun onStop() {
                DataManager.setBuildProject(false)
            }
        })
    }

}