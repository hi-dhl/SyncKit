package com.hi.dhl.ext

import com.hi.dhl.common.Common

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
    val rootDir: String = Common.remoteRootDir
)