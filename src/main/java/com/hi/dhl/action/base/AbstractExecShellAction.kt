package com.hi.dhl.action.base
import com.hi.dhl.common.Common
import com.hi.dhl.console.CommandManager
import com.hi.dhl.utils.FileUtils
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.project.Project
import java.io.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/14
 *     desc  :
 * </pre>
 */
abstract class AbstractExecShellAction : AbstractAnAction {

    constructor() : super()

    constructor(text: String) : super(text)

    fun execShelLScript(project: Project, shellScript: String) {
        try {

            val remoteMachineInfo = syncContentProvide.readSyncServiceConfig()
            val projectName = projectBasePath.substring(projectBasePath.lastIndexOf(File.separator) + 1)
            val remoteProjectPath = remoteMachineInfo.remoteRootDir + File.separator + projectName

            val commands = StringBuilder()
            val localShellScriptPath = FileUtils.getShellScriptPath(projectBasePath, shellScript)
            LogUtils.logI("localShellScriptPath = ${localShellScriptPath}")
            if (FileUtils.isExists(localShellScriptPath)) {
                CommandManager.execRemoteSellScript(commands, localShellScriptPath, remoteProjectPath, remoteMachineInfo, projectBasePath)
                execSyncRunnerConsole(project, projectBasePath, commands.toString())
            } else {
                LogUtils.logE("file not exist ${localShellScriptPath}")
            }

        } catch (e: Exception) {
            LogUtils.logE("exec action fail ${e}");
        }
    }
}