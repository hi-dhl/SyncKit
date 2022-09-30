package com.hi.dhl.common

import com.hi.dhl.console.RemoteMachineInfo
import com.hi.dhl.ktkit.common.toJson
import com.hi.dhl.utils.FileUtils
import com.intellij.openapi.project.Project
import java.io.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/26
 *     desc  :
 * </pre>
 */
class SyncContentProvide constructor(val project: Project) {
    val localProjectPath = project.basePath ?: "./"
    val localSyncConfigFile = FileUtils.getSyncServicePath(localProjectPath, Common.syncDefaultConfigJson)

    fun readSyncServiceConfig(): RemoteMachineInfo {
        return FileUtils.readServiceConfig(localSyncConfigFile)
    }

    fun initData() {
        if (!isInit()) {
            FileUtils.copyToTarget(localProjectPath)
        }
    }

    fun isInit(): Boolean {
        val destDir = File(localProjectPath + File.separator + Common.syncConfigRootDir)
        if (destDir.exists()) {
            return true
        } else {
            return false
        }
    }

    fun saveSyncServiceConfig(remoteMachineInfo: RemoteMachineInfo) {
        FileUtils.saveServiceConfig(remoteMachineInfo.toJson(), localSyncConfigFile)
    }

    fun deleteSyncRootDir() {
        val file = File(localProjectPath + File.separator + Common.syncConfigRootDir)
        FileUtils.deleteDirectory(file.toPath())
    }

    companion object : SingletonHolder<SyncContentProvide, Project>(::SyncContentProvide)
}


open class SingletonHolder<out T : Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator

    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}