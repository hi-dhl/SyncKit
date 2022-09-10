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
 * author: dhl
 * date  : 2022/9/3
 * desc  :
</pre> *
 */
class SyncKitMainAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { project ->
            try {
                val baseWorkingDir = project.basePath ?: "./"
                LogUtils.logI("click action path = ${baseWorkingDir}");
                init(baseWorkingDir)
                CommandManager.init(project)
                val command = CommandManager.compileAndroid("./gradlew --stop && ./gradlew assembleDebug")
                SyncRunnerConsole(
                    project = project,
                    consoleTitle = R.String.projectTitle,
                    workingDir = baseWorkingDir,
                    command = command
                ).initAndRun()
            } catch (e: Exception) {
                LogUtils.logE("exec command fail ${e}");
            }
        }
    }

    private fun init(basePath: String) {
        FileUtils.copyToTarget(basePath)
    }

}