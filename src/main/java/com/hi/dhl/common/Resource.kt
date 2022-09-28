package com.hi.dhl.common

import com.hi.dhl.utils.StringUtils.getMessage

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/4
 *     desc  :
 * </pre>
 */

object R {
    object String {
        val projectTitle = "SyncKit"
        val projectTaskDone = "\nsync done\n"
        val projectTaskStart = "\n sync start ...... \n\n"
        val projectVersion = "\n SyncKit v1.0.0 \n"
        object ui{
            @JvmField
            val labelRemoteUser = getMessage("sync.ui.lable.remote.user")
            @JvmField
            val labelRemotePort = getMessage("sync.ui.lable.remote.port")
            @JvmField
            val labelRemoteAddress = getMessage("sync.ui.lable.remote.address")
            @JvmField
            val labelRemoteCommand = getMessage("sync.ui.lable.remote.command")
            @JvmField
            val labelLaunchActivity = getMessage("sync.ui.android.lable.launch.activity")
            @JvmField
            val tfLaunchActivity = getMessage("sync.ui.android.tf.launch.activity")

            @JvmField
            val labelSDK = getMessage("sync.ui.android.lable.sdk")
            @JvmField
            val tfSDK = getMessage("sync.ui.android.textfield.sdk")

            val actionPlugin = getMessage("sync.action.plugin")
            val actionBuildProject = getMessage("sync.action.build.project")
            val actionStopBuildProject = getMessage("sync.action.stop.build.project")
            val actionCleanProject = getMessage("sync.action.clean.project")
            val actionInstallApk = getMessage("sync.action.install.apk")
            val actionSyncToService = getMessage("sync.action.syncToService")
            val actionInitialization = getMessage("sync.action.init")
            val actionInstallJDK11 = getMessage("sync.action.install.jdk11")
            val actionAndroidSDK = getMessage("sync.action.install.sdk")
        }
    }

    object ShellScript {
        val installJDK11 = "install_jdk_11.sh"
        val installAndroidSdk = "install_android_sdk.sh"
        val installApk = "install-apk.sh"
    }
}

object Common {
    val isDebug = true
    val projectTitle = "SyncKit"
    val resourceConfigDir = "config"

    val syncConfigRootDir = ".sync"
    val syncConfigServiceDir = "service"
    val syncConfigScriptDir = "script"
    val syncConfigLocalIgnoreFile = "local_ignore"
    val syncConfigRemoteIncludeFile = "remote_include"
    val syncDefaultConfigJson = "config.json"

    val remoteRootDir: String = "~/SyncKit"
}
