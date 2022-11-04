package com.hi.dhl.common

import com.hi.dhl.ext.PluginVersion
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
        val pluginVersion = PluginVersion.CURRENT_VERSION
        val projectTitle = "SyncKit"
        val projectTaskDone = "\nsync done\n"
        val projectTaskStartTip = """
            
             .----------------.  .----------------.  .----------------.  .----------------.
            | .--------------. || .--------------. || .--------------. || .--------------. |
            | |                                                                          | |  
            | |                           SyncKit v${pluginVersion}                                   | |
            | |                                                                          | |
            | |      如何使用：https://github.com/hi-dhl/SyncKit/blob/main/doc/use.md      | |
            | |                                                                          | |
            | |      常见问题：https://github.com/hi-dhl/SyncKit/blob/main/doc/qa.md       | | 
            | |                                                                          | |
            | '--------------' || '--------------' || '--------------' || '--------------' |
             '----------------'  '----------------'  '----------------'  '----------------' 
             
             
         """.trimIndent()
        object ui{
            @JvmField
            val labelRemoteUser = getMessage("sync.ui.lable.remote.user")
            @JvmField
            val labelRemotePort = getMessage("sync.ui.lable.remote.port")
            @JvmField
            val labelRemoteHost = getMessage("sync.ui.lable.remote.host")
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
            @JvmField
            val labelNDK = getMessage("sync.ui.android.lable.ndk")
            @JvmField
            val tfNDK = getMessage("sync.ui.android.textfield.ndk")

            @JvmField
            val lableRemoteWorkDir = getMessage("sync.ui.lable.remote.workDir")

            val actionPlugin = getMessage("sync.action.plugin")
            val actionBuildProject = getMessage("sync.action.build.project")
            val actionStopBuildProject = getMessage("sync.action.stop.build.project")
            val actionCleanProject = getMessage("sync.action.clean.project")
            val actionRemoteCommand = getMessage("sync.action.remote.command")
            val actionInstallApk = getMessage("sync.action.install.apk")
            val actionSyncToService = getMessage("sync.action.syncToService")
            val actionInitialization = getMessage("sync.action.init")
            val actionInstallJDK11 = getMessage("sync.action.install.jdk11")
            val actionAndroidSDK = getMessage("sync.action.install.sdk")
            val actionAndroidNDK = getMessage("sync.action.install.ndk")
            val actionInstallSSHPub = getMessage("sync.action.install.ssh.pub")

            val confirmDialogTitle = getMessage("sync.ui.config.dialog.title")
            val confirmDialogTip = getMessage("sync.ui.config.dialog.tip")

            @JvmField
            val labelRemotePassword= getMessage("sync.ui.lable.remote.password")
            @JvmField
            val tfRemotePassword = getMessage("sync.ui.android.textfield.password")
        }
    }

    object ShellScript {
        @JvmField
        val installJDK11 = "install_jdk_11.sh"
        @JvmField
        val installAndroidSDK = "install_android_sdk.sh"
        @JvmField
        val installApk = "install-apk.sh"
        @JvmField
        val installSSHPub = "install-ssh-publickey.sh"
        @JvmField
        val installAndroidNDK = "install_android_ndk.sh"
        @JvmField
        val cleanProject = "clean-project.sh"
        @JvmField
        val installSshpass = "install-sshpass.sh"
    }

    val sshpassFile = "sshpass-1.08.tar.gz"
}

