package com.hi.dhl.common

import com.hi.dhl.Common
import com.hi.dhl.console.RemoteMachineInfo
import com.hi.dhl.utils.FileUtils
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
object DataManager {
    private var mInit = false
    private val configName: String = Common.syncDefaultConfigJson
    private lateinit var machineInfo: RemoteMachineInfo
    private lateinit var projectBasePath: String

    fun init(project: Project) {
        if (mInit) {
            return
        }
        mInit = true
        projectBasePath = project.basePath ?: "./"
        FileUtils.copyToTarget(projectBasePath)
        machineInfo = getMachineInfo()
        LogUtils.logI(machineInfo.toString())
    }

    fun getMachineInfo(): RemoteMachineInfo {
        if (!::machineInfo.isInitialized) {
            machineInfo = FileUtils.readServiceConfig(
                FileUtils.getSyncServicePath(configName)
            )
        }
        return machineInfo
    }

    fun projectBasePath() = projectBasePath
}