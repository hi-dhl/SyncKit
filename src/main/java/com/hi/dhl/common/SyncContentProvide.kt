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
    val localIgnoreFile = FileUtils.getSyncConfigPath(localProjectPath, Common.syncConfigLocalIgnoreFile)

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

    fun readSyncServiceConfig(): RemoteMachineInfo {
        return FileUtils.readConfigJson(localSyncConfigFile)
    }

    fun saveSyncServiceConfig(remoteMachineInfo: RemoteMachineInfo) {
        FileUtils.saveConfigJson(remoteMachineInfo.toJson(), localSyncConfigFile)
    }

    fun readSyncLocalIgnore(): String {
        return FileUtils.readConfigText(localIgnoreFile)
    }

    fun saveSyncLocalIgnore(text: String) {
        FileUtils.saveConfigText(text, localIgnoreFile)
    }

    fun deleteSyncRootDir() {
        val file = File(localProjectPath + File.separator + Common.syncConfigRootDir)
        FileUtils.deleteDirectory(file.toPath())
    }

//    companion object : SingletonHolder<SyncContentProvide, Project>(::SyncContentProvide)

    companion object {
        val contentProvides = HashMap<Project, SyncContentProvide>()
        fun getInstance(project: Project): SyncContentProvide {
            var syncContentProvide = contentProvides.get(project)
            if (syncContentProvide == null) {
                syncContentProvide = SyncContentProvide(project)
                contentProvides.put(project, syncContentProvide)
            }
            return syncContentProvide
        }
    }
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