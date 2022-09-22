package com.hi.dhl.ui

import com.hi.dhl.common.SyncContentProvide
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/24
 *     desc  :
 * </pre>
 */
class ConfigCommandDialog(
    val project: Project
) : DialogWrapper(project, false) {
    val command = ConfigCommandForm()
    val remoteMachineInfo by lazy(LazyThreadSafetyMode.NONE) {
        SyncContentProvide.getInstance(project).readSyncServiceConfig()
    }

    init {
        setTitle("Configure Project");
        init()
    }

    override fun doOKAction() {
        super.doOKAction()
        val remoteBuildCommand = command.inputRemoteCommand.text
        val remoteMachineAddress = command.inputRemoteAddress.text
        val remotePort = command.inputRemotePort.text

        remoteMachineInfo.remoteAddress = remoteMachineAddress
        remoteMachineInfo.remotePort = remotePort
        remoteMachineInfo.remoteBuildCommand = remoteBuildCommand

        SyncContentProvide.getInstance(project).saveSyncServiceConfig(remoteMachineInfo)
    }

    override fun doCancelAction() {
        super.doCancelAction()
    }

    override fun createCenterPanel(): JComponent? {
        command.inputRemoteCommand.text = remoteMachineInfo.remoteBuildCommand
        command.inputRemoteAddress.text = remoteMachineInfo.remoteAddress
        command.inputRemotePort.text = remoteMachineInfo.remotePort
        return command.panel
    }

}