package com.hi.dhl.ext

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
data class RemoteMachineInfo(
    val port: String,
    val address: String,
    val user: String,
    val rootDir: String = "~/SyncKit"
)