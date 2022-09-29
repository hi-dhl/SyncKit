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

    val pluginConfigForm = PluginConfigForm()

    val remoteMachineInfo by lazy(LazyThreadSafetyMode.NONE) {
        SyncContentProvide.getInstance(project).readSyncServiceConfig()
    }

    init {
        setTitle("Configure Project");
        init()
    }

    override fun doOKAction() {
        super.doOKAction()
        val remoteBuildCommand = pluginConfigForm.tfRemoteCommand.text
        val remoteMachineAddress = pluginConfigForm.tfRemoteAddress.text
        val remotePort = pluginConfigForm.tfRemotePort.text
        val remoteUser = pluginConfigForm.tfRemoteUser.text

        remoteMachineInfo.remoteAddress = remoteMachineAddress
        remoteMachineInfo.remotePort = remotePort
        remoteMachineInfo.remoteBuildCommand = remoteBuildCommand
        remoteMachineInfo.remoteUser = remoteUser
        remoteMachineInfo.launchActivity = pluginConfigForm.tfLaunchActivity.text
        remoteMachineInfo.sdkDir = pluginConfigForm.tfSDK.text
        SyncContentProvide.getInstance(project).saveSyncServiceConfig(remoteMachineInfo)
    }

    override fun doCancelAction() {
        super.doCancelAction()
    }

    override fun createCenterPanel(): JComponent? {
        pluginConfigForm.tfRemoteCommand.text = remoteMachineInfo.remoteBuildCommand
        pluginConfigForm.tfRemoteAddress.text = remoteMachineInfo.remoteAddress
        pluginConfigForm.tfRemotePort.text = remoteMachineInfo.remotePort
        pluginConfigForm.tfRemoteUser.text = remoteMachineInfo.remoteUser
        pluginConfigForm.tfLaunchActivity.text = remoteMachineInfo.launchActivity
        pluginConfigForm.tfSDK.text = remoteMachineInfo.sdkDir
        return pluginConfigForm.rootPanel
    }

}