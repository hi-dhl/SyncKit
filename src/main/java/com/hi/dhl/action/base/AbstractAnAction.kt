package com.hi.dhl.action.base

import com.hi.dhl.action.listener.BuildProcessListener
import com.hi.dhl.common.R
import com.hi.dhl.console.RemoteMachineInfo
import com.hi.dhl.console.SyncRunnerConsole
import com.hi.dhl.utils.LogUtils
import com.hi.dhl.utils.MessagesUtils
import com.hi.dhl.utils.StringUtils
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/14
 *     desc  :
 * </pre>
 */
abstract class AbstractAnAction : BaseAnAction {

    constructor() : super()

    constructor(text: String) : super(text)

    lateinit var remoteMachineInfo: RemoteMachineInfo

    override fun actionPerformed(e: AnActionEvent) {
        super.actionPerformed(e)
        try {
            beforeActionPerformed(project)

            if (!syncContentProvide.isInit()) {
                MessagesUtils.showMessageWarnDialog(
                    StringUtils.getMessage("sync.init.warring.title"),
                    StringUtils.getMessage("sync.init.warring.content")
                )
                return
            }

            remoteMachineInfo = syncContentProvide.readSyncServiceConfig()
            if (remoteMachineInfo.checkOrNull()) {
                return
            }
            syncContentProvide.initData()
            afterActionPerformed(project)
        } catch (e: Exception) {
            LogUtils.logE("exec action fail ${e}");
        }
    }

    abstract fun afterActionPerformed(project: Project)

    open fun beforeActionPerformed(project: Project) {
    }

    fun execSyncRunnerConsole(
        project: Project,
        projectBasePath: String,
        commands: String,
        buildProcessListener: BuildProcessListener? = null
    ) {
        val syncRunnerConsole = SyncRunnerConsole(
            project = project,
            consoleTitle = R.String.projectTitle,
            workingDir = projectBasePath,
            command = commands,
            buildProcessListener = buildProcessListener
        )
        syncRunnerConsole.initAndRun()
    }

}