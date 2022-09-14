package com.hi.dhl.common

import com.hi.dhl.Common
import com.hi.dhl.console.RemoteMachineInfo
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
object DataManager {
    private val configName: String = Common.syncDefaultConfigJson
    private lateinit var machineInfo: RemoteMachineInfo
    private lateinit var projectBasePath: String

    fun isInit(projectBasePath: String): Boolean {
        val destDir = File(projectBasePath + File.separator + Common.syncConfigRootDir)
        if (destDir.exists()) {
            return true
        } else {
            return false
        }
    }

    fun init(project: Project) {
        projectBasePath = project.basePath ?: "./"
        if (isInit(projectBasePath)) {
            machineInfo = getMachineInfo()
        } else {
            FileUtils.copyToTarget(projectBasePath)
            machineInfo = getMachineInfo()
        }
        LogUtils.logI(machineInfo.toString())
    }

    fun getMachineInfo(): RemoteMachineInfo {
        if (!isInit(projectBasePath)) {
            LogUtils.logW("DataManager is not initialized")
        }
        if (!::machineInfo.isInitialized) {
            machineInfo = FileUtils.readServiceConfig(FileUtils.getSyncServicePath(configName))
        }
        return machineInfo
    }

    fun projectBasePath(): String = projectBasePath
}