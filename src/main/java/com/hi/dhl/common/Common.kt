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
    @JvmField
    val resourceConfigDir = "config"
    @JvmField
    val syncConfigRootDir = ".sync"
    @JvmField
    val syncConfigServiceDir = "service"
    @JvmField
    val syncConfigScriptDir = "script"
    @JvmField
    val syncConfigLocalIgnoreFile = "local_ignore"
    @JvmField
    val syncConfigRemoteIncludeFile = "remote_include"
    @JvmField
    val syncConfigRemoteIgnoreFile = "remote_ignore"
    @JvmField
    val syncDefaultConfigJson = "config.json"
    @JvmField
    val syncFiles = "files"

    val localProperties = "local.properties"
    val flagRmoetUserWrok = "~/"
    val remoteRootDir: String = "~/code"
    val osLine = System.getProperty("line.separator")
    val flagCustomerRule = "#customerRule"
}
