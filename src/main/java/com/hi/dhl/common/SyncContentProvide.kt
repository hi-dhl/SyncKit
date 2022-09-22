package com.hi.dhl.common

import com.hi.dhl.Common
import com.hi.dhl.console.RemoteMachineInfo
import com.hi.dhl.ktkit.common.toJson
import com.hi.dhl.utils.FileUtils
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/26
 *     desc  :
 * </pre>
 */
class SyncContentProvide private constructor(val project: Project) {
    val localProjectPath = project.basePath ?: "./"
    val localSyncConfigFile = FileUtils.getSyncServicePath(localProjectPath, Common.syncDefaultConfigJson)

    fun readSyncServiceConfig(): RemoteMachineInfo {
        return FileUtils.readServiceConfig(localSyncConfigFile)
    }

    fun saveSyncServiceConfig(remoteMachineInfo: RemoteMachineInfo) {
        FileUtils.saveServiceConfig(remoteMachineInfo.toJson(), localSyncConfigFile)
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