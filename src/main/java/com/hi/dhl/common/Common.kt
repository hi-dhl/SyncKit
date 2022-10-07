package com.hi.dhl.common

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/10/2
 *     desc  :
 * </pre>
 */
object Common {
    val isDebug = false
    val projectTitle = "SyncKit"
    val resourceConfigDir = "config"

    val syncConfigRootDir = ".sync"
    val syncConfigServiceDir = "service"
    val syncConfigScriptDir = "script"
    val syncConfigLocalIgnoreFile = "local_ignore"
    val syncConfigRemoteIncludeFile = "remote_include"
    val syncConfigRemoteIgnoreFile = "remote_ignore"
    val syncDefaultConfigJson = "config.json"
    val localProperties = "local.properties"

    val flagRmoetUserWrok = "~/"
    val remoteRootDir: String = "~/code"
    val osLine = System.getProperty("line.separator")
    val flagCustomerRule = "#customerRule"
}
