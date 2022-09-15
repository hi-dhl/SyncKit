package com.hi.dhl.common

import com.hi.dhl.Common
import com.hi.dhl.console.RemoteMachineInfo
import com.hi.dhl.utils.FileUtils
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.project.Project
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
object DataManager {
    private val configName: String = Common.syncDefaultConfigJson
    private lateinit var projectBasePath: String

    fun isInit(projectBasePath: String): Boolean {
        val destDir = File(projectBasePath + File.separator + Common.syncConfigRootDir)
        if (destDir.exists()) {
            return true
        } else {
            return false
        }
    }

    fun deleteSyncRootDir(project: Project) {
        projectBasePath = project.basePath ?: "./"
        val file = File(projectBasePath + File.separator + Common.syncConfigRootDir)
        FileUtils.deleteDirectory(file.toPath())
    }

    fun init(project: Project) {
        projectBasePath = project.basePath ?: "./"
        val machineInfo: RemoteMachineInfo
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
        return FileUtils.readServiceConfig(FileUtils.getSyncServicePath(projectBasePath, configName))
    }

    fun projectBasePath(): String = projectBasePath
}