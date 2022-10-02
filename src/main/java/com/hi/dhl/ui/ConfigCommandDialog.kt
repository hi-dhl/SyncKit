package com.hi.dhl.ui

import com.hi.dhl.common.R
import com.hi.dhl.common.SyncContentProvide
import com.hi.dhl.console.RemoteMachineInfo
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import java.awt.Color
import javax.swing.JComponent

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/24
 *     desc  :
 * </pre>
 */
class ConfigCommandDialog(
    val project: Project,
    val remoteMachineInfo: RemoteMachineInfo
) : DialogWrapper(project, false) {

    val pluginConfigForm = PluginConfigForm()

    init {
        setTitle(R.String.ui.actionPlugin)
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
        if (!pluginConfigForm.tfLaunchActivity.text.equals(R.String.ui.tfLaunchActivity)) {
            remoteMachineInfo.launchActivity = pluginConfigForm.tfLaunchActivity.text
        }

        if (!pluginConfigForm.tfSDK.text.equals(R.String.ui.tfSDK)) {
            remoteMachineInfo.sdkDir = pluginConfigForm.tfSDK.text
        }

        if (!pluginConfigForm.tfNdk.text.equals(R.String.ui.tfNDK)) {
            remoteMachineInfo.ndkDir = pluginConfigForm.tfNdk.text
        }

        SyncContentProvide.getInstance(project).saveSyncServiceConfig(remoteMachineInfo)
    }

    override fun createCenterPanel(): JComponent? {
        resetData()
        return pluginConfigForm.rootPanel
    }

    private fun resetData() {
        if (!remoteMachineInfo.remoteBuildCommand.isNullOrEmpty()) {
            pluginConfigForm.tfRemoteCommand.text = remoteMachineInfo.remoteBuildCommand
        }

        if (!remoteMachineInfo.remoteAddress.isNullOrEmpty()) {
            pluginConfigForm.tfRemoteAddress.text = remoteMachineInfo.remoteAddress
        }

        if (!remoteMachineInfo.remotePort.isNullOrEmpty()) {
            pluginConfigForm.tfRemotePort.text = remoteMachineInfo.remotePort
        }

        if (!remoteMachineInfo.remoteUser.isNullOrEmpty()) {
            pluginConfigForm.tfRemoteUser.text = remoteMachineInfo.remoteUser
        }
        if (!remoteMachineInfo.launchActivity.isNullOrEmpty()
            && !remoteMachineInfo.launchActivity.equals(R.String.ui.tfLaunchActivity)) {
            pluginConfigForm.tfLaunchActivity.text = remoteMachineInfo.launchActivity
            pluginConfigForm.tfLaunchActivity.foreground = Color.BLACK
        }

        if (!remoteMachineInfo.sdkDir.isNullOrEmpty()
            && !remoteMachineInfo.sdkDir.equals(R.String.ui.tfSDK)) {
            pluginConfigForm.tfSDK.text = remoteMachineInfo.sdkDir
            pluginConfigForm.tfSDK.foreground = Color.BLACK
        }

        if (!remoteMachineInfo.ndkDir.isNullOrEmpty()
            && !remoteMachineInfo.ndkDir.equals(R.String.ui.tfNDK)) {
            pluginConfigForm.tfNdk.text = remoteMachineInfo.ndkDir
            pluginConfigForm.tfNdk.foreground = Color.BLACK
        }
    }

}