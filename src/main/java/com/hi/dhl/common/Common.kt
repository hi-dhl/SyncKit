package com.hi.dhl.common

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/10/2
 *     desc  :
 * </pre>
 */
object Common {
    val isDebug = true
    val projectTitle = "SyncKit"
    val resourceConfigDir = "config"

    val syncConfigRootDir = ".sync"
    val syncConfigServiceDir = "service"
    val syncConfigScriptDir = "script"
    val syncConfigLocalIgnoreFile = "local_ignore"
    val syncConfigRemoteIncludeFile = "remote_include"
    val syncDefaultConfigJson = "config.json"
    val localProperties = "local.properties"

    val remoteRootDir: String = "~/SyncKit"
    val osLine = System.getProperty("line.separator")
    val flagCustomerRule = "#customerRule"
}
