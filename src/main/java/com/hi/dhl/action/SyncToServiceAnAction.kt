package com.hi.dhl.action

import com.hi.dhl.R
import com.hi.dhl.common.DataManager
import com.hi.dhl.console.CommandManager
import com.hi.dhl.console.SyncRunnerConsole
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import org.jetbrains.kotlin.konan.file.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
class SyncToServiceAnAction : AnAction() {

//    private var machineInfo: RemoteMachineInfo = DataManager.getMachineInfo()

    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { project ->
            try {
                val projectBasePath = project.basePath ?: "./"
                LogUtils.logI("click action path = ${projectBasePath}");
                val stringBuilder = StringBuilder()
                val projectName = projectBasePath.substring(projectBasePath.lastIndexOf(File.separator) + 1)
                val remoteProjectPath = DataManager.getMachineInfo().remoteRootDir + File.separator + projectName
                CommandManager.syncLocalToRemote(stringBuilder, remoteProjectPath)

                SyncRunnerConsole(
                    project = project,
                    consoleTitle = R.String.projectTitle,
                    workingDir = projectBasePath,
                    command = stringBuilder.toString()
                ).initAndRun()
            } catch (e: Exception) {
                LogUtils.logE("exec command fail ${e}");
            }
        }

    }

//    companion object {
//        val text = "Sync/Compile"
//        val description = text
//        val icon: Icon = IconLoader.getIcon("/icons/kit.png", javaClass)
//        fun createAnAction(): AnAction {
//            return SyncToServiceAnAction(
//                text = text,
//                description = description,
//                icon = icon
//            )
//        }
//    }
}