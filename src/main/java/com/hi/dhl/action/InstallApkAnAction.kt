package com.hi.dhl.action

import com.hi.dhl.Common
import com.hi.dhl.R
import com.hi.dhl.console.CommandManager
import com.hi.dhl.console.SyncRunnerConsole
import com.hi.dhl.utils.FileUtils
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.project.Project
import java.io.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
class InstallApkAnAction : AbstractAnAction() {

    override fun action(project: Project) {
        try {
            val projectBasePath = project.basePath ?: "./"
            LogUtils.logI("click action path = ${projectBasePath}");

            val commands = StringBuilder()
            val shellInstallApkPath =
                projectBasePath + File.separator + Common.syncConfigRootDir + File.separator + FileUtils.SHELL_INSTALL_APK
            if (FileUtils.isExists(shellInstallApkPath)) {
                val shellInstallApk = FileUtils.getSyncConfigPath(FileUtils.SHELL_INSTALL_APK)
                CommandManager.execLocalCommand(
                    commands,
                    "chmod 777 ${shellInstallApk} && bash ${shellInstallApk}"
                )

                SyncRunnerConsole(
                    project = project,
                    consoleTitle = R.String.projectTitle,
                    workingDir = projectBasePath,
                    command = commands.toString()
                ).initAndRun()

            } else {
                LogUtils.logE("no find ${FileUtils.getSyncConfigPath(FileUtils.SHELL_INSTALL_APK)}")
            }

        } catch (e: Exception) {
            LogUtils.logE("exec action fail ${e}");
        }
    }

}