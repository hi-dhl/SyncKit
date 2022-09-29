package com.hi.dhl.console

import com.hi.dhl.common.R
import com.hi.dhl.action.listener.BuildProcessListener
import com.hi.dhl.utils.LogUtils
import com.intellij.execution.Executor
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.PtyCommandLine
import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.process.*
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/4
 *     desc  :
 * </pre>
 */
class SyncRunnerConsole(
    project: Project,
    consoleTitle: String,
    workingDir: String,
    private val command: String,
    val buildProcessListener: BuildProcessListener? = null
) : AbstractConsoleRunner<ConsoleView>(project, consoleTitle, workingDir) {

    private lateinit var generalCommandLine: GeneralCommandLine
    private lateinit var process: Process
    private lateinit var osProcessHandler: OSProcessHandler
    private lateinit var consoleView: ConsoleView

    override fun createConsoleView(): ConsoleView {
        LogUtils.logI("createConsoleView")
        consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).console
        return consoleView
    }

    override fun createProcess(): Process {
        LogUtils.logI("createProcess")
        generalCommandLine = PtyCommandLine()
        return generalCommandLine.withWorkDirectory(workingDir).apply {
            exePath = "/bin/bash"
            addParameter("-c")
            addParameter(command)
        }.createProcess().also { process = it }
    }

    override fun createProcessHandler(process: Process): OSProcessHandler {
        LogUtils.logI("createProcessHandler")
        osProcessHandler = OSProcessHandler(generalCommandLine)
        osProcessHandler.addProcessListener(object : ProcessListener {
            override fun startNotified(processEvent: ProcessEvent) {
                val processHandler: ProcessHandler = processEvent.getProcessHandler()
                when (processEvent.exitCode) {
                    0 -> {
                        if (buildProcessListener != null) {
                            buildProcessListener.onStart()
                        }
                        processHandler.notifyTextAvailable(R.String.projectVersion, ProcessOutputTypes.SYSTEM)
                        processHandler.notifyTextAvailable(R.String.projectTaskStart, ProcessOutputTypes.SYSTEM)
//                        consoleView.print(R.String.projectTaskStart, ConsoleViewContentType.USER_INPUT)
//                        consoleView.print(R.String.projectVersion, ConsoleViewContentType.USER_INPUT)
                    }
                }
                LogUtils.logI("startNotified code = ${processEvent.exitCode} text = ${processEvent.text}")
            }

            override fun processTerminated(processEvent: ProcessEvent) {
                val processHandler: ProcessHandler = processEvent.getProcessHandler()
                processHandler.removeProcessListener(this)
                when (processEvent.exitCode) {
                    0 -> processHandler.notifyTextAvailable(R.String.projectTaskDone, ProcessOutputTypes.SYSTEM)
                }
                if (buildProcessListener != null) {
                    buildProcessListener.onStop()
                }
                LogUtils.logI("processTerminated code = ${processEvent.exitCode} text = ${processEvent.text}")
            }

            override fun onTextAvailable(processEvent: ProcessEvent, p1: Key<*>) {
            }
        })
        return osProcessHandler
    }

    override fun fillToolBarActions(
        toolbarActions: DefaultActionGroup?,
        defaultExecutor: Executor?,
        contentDescriptor: RunContentDescriptor?
    ): MutableList<AnAction> {
        LogUtils.logI("fillToolBarActions")
        val actionList: MutableList<AnAction> = ArrayList()
        return actionList;
    }


}