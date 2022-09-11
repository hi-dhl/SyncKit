package com.hi.dhl

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/4
 *     desc  :
 * </pre>
 */

object R {
    object String {
        val projectTitle = "SyncKit"
        val projectTaskDone = "\nsync done\n"
        val projectTaskStart = "\n sync start ...... \n"
        val projectVersion = "\n SyncKit v1.0.0 \n"
    }
}

object Common {
    val isDebug = true;
    val projectTitle = "SyncKit"
    val resourceConfigDir = "config"

    val syncConfigRootDir = ".sync"
    val syncConfigService = "service"
    val syncConfigLocalIgnoreFile = "local_ignore"
    val syncConfigRemoteIncludeFile = "remote_include"
    val syncDefaultConfigJson = "config_linux.json"

    val remoteRootDir: String = "~/SyncKit"
}
