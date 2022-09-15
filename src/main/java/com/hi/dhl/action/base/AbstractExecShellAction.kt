package com.hi.dhl.action.base
import com.hi.dhl.Common
import com.hi.dhl.R
import com.hi.dhl.common.DataManager
import com.hi.dhl.console.CommandManager
import com.hi.dhl.console.SyncRunnerConsole
import com.hi.dhl.utils.FileUtils
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.konan.file.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/14
 *     desc  :
 * </pre>
 */
abstract class AbstractExecShellAction : AbstractAnAction() {

    fun execShelLScript(project: Project, shellScript: String) {
        try {
            val projectBasePath = project.basePath ?: "./"
            LogUtils.logI("click action project path = ${projectBasePath}")

            val projectName = projectBasePath.substring(projectBasePath.lastIndexOf(File.separator) + 1)
            val remoteProjectPath = DataManager.getMachineInfo().remoteRootDir + File.separator + projectName + File.separator + Common.syncConfigRootDir

            val commands = StringBuilder()
            val localShellScriptPath = FileUtils.getShellScriptPath(projectBasePath, shellScript)
            LogUtils.logI("localShellScriptPath = ${localShellScriptPath}")
            if (FileUtils.isExists(localShellScriptPath)) {
                CommandManager.execRemoteSellScript(commands, localShellScriptPath, remoteProjectPath)
                execSyncRunnerConsole(project, projectBasePath, commands.toString())
            } else {
                LogUtils.logE("file not exist ${localShellScriptPath}")
            }

        } catch (e: Exception) {
            LogUtils.logE("exec action fail ${e}");
        }
    }
}