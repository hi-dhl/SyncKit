package com.hi.dhl.console

import com.hi.dhl.Common

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
    val remoteRootDir: String = Common.remoteRootDir
) {
    companion object {
        fun createEmptyRemoteMachineInfo(): RemoteMachineInfo {
            return RemoteMachineInfo(
                remotePort = "",
                remoteAddress = "",
                remoteUser = ""
            )
        }
    }
}