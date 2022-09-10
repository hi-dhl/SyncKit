package com.hi.dhl.console

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
data class RemoteMachineInfo(
    val remotePort: String,
    val remoteAddress: String,
    val remoteUser: String,
    val remoteRootDir: String = "~/SyncKit"
)