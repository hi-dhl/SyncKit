package com.hi.dhl.action

import com.hi.dhl.action.base.AbstractAnAction
import com.hi.dhl.action.listener.BuildProcessListener
import com.hi.dhl.common.R
import com.hi.dhl.console.CommandManager
import com.hi.dhl.utils.LogUtils
import com.hi.dhl.utils.MessagesUtils
import com.hi.dhl.utils.StringUtils
import com.hi.dhl.utils.TimeUtils
import com.intellij.execution.process.ProcessEvent
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
        var startTime: Long = 0
        execSyncRunnerConsole(project, projectBasePath, commands.toString(), object : BuildProcessListener {
            override fun onStart(time: Long) {
                startTime = time
            }

            override fun onStop(processEvent: ProcessEvent, endTime: Long) {
                val execTime = TimeUtils.formatTime(
                    startTime = startTime,
                    endTime = endTime
                )
                when(processEvent.exitCode) {
                    0 -> {
                        LogUtils.log(
                            "BUILD SUCCESSFUL in ${execTime}",
                            NotificationDisplayType.BALLOON,
                            NotificationType.INFORMATION,
                            project
                        )
                    }
                    else -> {
                        var errorTip = "BUILD FAILED in ${execTime}, error code ${processEvent.exitCode}"
                        if (!processEvent.text.isNullOrEmpty()) {
                            errorTip += ", ${processEvent.text}"
                        }
                        LogUtils.log(
                            errorTip,
                            NotificationDisplayType.BALLOON,
                            NotificationType.WARNING,
                            project
                        )
                    }
                }
            }
        })
    }


}