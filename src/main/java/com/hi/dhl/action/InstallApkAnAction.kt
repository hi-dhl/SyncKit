package com.hi.dhl.action

import com.hi.dhl.R
import com.hi.dhl.console.CommandManager
import com.hi.dhl.console.SyncRunnerConsole
import com.hi.dhl.utils.FileUtils
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
class InstallApkAnAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { project ->
            try {
                val projectBasePath = project.basePath ?: "./"
                LogUtils.logI("click action path = ${projectBasePath}");

                // 执行安装命令
                val build = StringBuilder()
                if (FileUtils.isExists(FileUtils.SHELL_INSTALL_APK)) {
                    val shellInstallApk = FileUtils.getSyncConfigPath(FileUtils.SHELL_INSTALL_APK)
                    CommandManager.execLocalCommand(
                        build,
                        "chmod 777 ${shellInstallApk} && bash ${shellInstallApk}"
                    )

                    SyncRunnerConsole(
                        project = project,
                        consoleTitle = R.String.projectTitle,
                        workingDir = projectBasePath,
                        command = build.toString()
                    ).initAndRun()

                } else {
                    LogUtils.logE("no find ${FileUtils.getSyncConfigPath(FileUtils.SHELL_INSTALL_APK)}")
                }

            } catch (e: Exception) {
                LogUtils.logE("exec command fail ${e}");
            }
        }

    }

}