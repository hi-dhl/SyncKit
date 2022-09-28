package com.hi.dhl.action.base

import com.hi.dhl.R
import com.hi.dhl.action.BuildProcessListener
import com.hi.dhl.common.DataManager
import com.hi.dhl.common.SyncContentProvide
import com.hi.dhl.console.RemoteMachineInfo
import com.hi.dhl.console.SyncRunnerConsole
import com.hi.dhl.utils.LogUtils
import com.hi.dhl.utils.MessagesUtils
import com.hi.dhl.utils.StringUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/14
 *     desc  :
 * </pre>
 */
abstract class AbstractAnAction : AnAction() {
    lateinit var remoteMachineInfo: RemoteMachineInfo
    lateinit var projectBasePath: String
    override fun actionPerformed(e: AnActionEvent) {
        try {
            e.project?.let { project ->
                remoteMachineInfo = SyncContentProvide.getInstance(project).readSyncServiceConfig()
                if (remoteMachineInfo.checkOrNull()) {
                    return
                }
                projectBasePath = project.basePath ?: "./"
                if (!DataManager.isInit(projectBasePath)) {
                    MessagesUtils.showMessageWarnDialog(
                        StringUtils.getMessage("sync.init.warring.title"),
                        StringUtils.getMessage("sync.init.warring.content")
                    )
                    return
                }
                DataManager.init(project)
                action(project)
            }
        } catch (e: Exception) {
            LogUtils.logE("exec action fail ${e}");
        }
    }

    abstract fun action(project: Project)

    fun execSyncRunnerConsole(
        project: Project,
        projectBasePath: String,
        commands: String,
        buildProcessListener: BuildProcessListener? = null
    ) {
        SyncRunnerConsole(
            project = project,
            consoleTitle = R.String.projectTitle,
            workingDir = projectBasePath,
            command = commands
        ).initAndRun()
    }

}