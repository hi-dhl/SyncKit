package com.hi.dhl.action

import com.hi.dhl.action.base.AbstractAnAction
import com.hi.dhl.common.R
import com.hi.dhl.console.CommandManager
import com.hi.dhl.ui.ConfigurationCommandDialog
import com.hi.dhl.utils.MessagesUtils
import com.hi.dhl.utils.StringUtils
import com.intellij.openapi.project.Project
import java.io.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/15
 *     desc  :
 * </pre>
 */
class RemoteCommandAnAction : AbstractAnAction(R.String.ui.actionRemoteCommand) {

    override fun afterActionPerformed(project: Project) {
        val configurationCommandDialog = ConfigurationCommandDialog(project) { extraCommand ->
            if (extraCommand.isNullOrEmpty()) {
                MessagesUtils.showMessageWarnDialog(
                    StringUtils.getMessage("sync.init.warring.title"),
                    StringUtils.getMessage("sync.ui.command.empty")
                )
                return@ConfigurationCommandDialog
            }
            val commands = StringBuilder()
            val projectName = projectBasePath.substring(projectBasePath.lastIndexOf(File.separator) + 1)
            val remoteProjectPath = remoteMachineInfo.remoteRootDir + File.separator + projectName
            CommandManager.execRemoteCommand(commands, remoteProjectPath, extraCommand, remoteMachineInfo)
            val consoleTitle = "${R.String.projectTitle} [ ${extraCommand} ] "
            execSyncRunnerConsole(project, projectBasePath, commands.toString(), consoleTitle)
        }
        configurationCommandDialog.show()
    }
}