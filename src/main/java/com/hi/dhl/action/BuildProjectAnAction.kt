package com.hi.dhl.action

import com.hi.dhl.action.base.AbstractAnAction
import com.hi.dhl.action.listener.BuildProcessListener
import com.hi.dhl.common.R
import com.hi.dhl.console.CommandManager
import com.hi.dhl.utils.LogUtils
import com.hi.dhl.utils.MessagesUtils
import com.hi.dhl.utils.StringUtils
import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
class BuildProjectAnAction : AbstractAnAction(R.String.ui.actionBuildProject) {


    override fun action(project: Project) {
        var extraCommand = "./gradlew "
        if (remoteMachineInfo.remoteBuildCommand.isNullOrEmpty()) {
            val warringTitle = StringUtils.getMessage("sync.init.warring.title")
            MessagesUtils.showMessageWarnDialog(
                warringTitle, StringUtils.getMessage("sync.service.empry.command")
            )
            return
        }
        extraCommand += remoteMachineInfo.remoteBuildCommand.toString()
        val commands = StringBuilder()
        CommandManager.compileAndroid(commands, extraCommand, projectBasePath, remoteMachineInfo)
        execSyncRunnerConsole(project, projectBasePath, commands.toString(), object : BuildProcessListener {
            override fun onStart() {
            }

            override fun onStop(exitCode: Int) {
                when {
                    exitCode == 0 -> {
                        LogUtils.log(
                            "Build succeeded",
                            NotificationDisplayType.BALLOON,
                            NotificationType.IDE_UPDATE,
                            project
                        )
                    }
                }
            }
        })
    }

}