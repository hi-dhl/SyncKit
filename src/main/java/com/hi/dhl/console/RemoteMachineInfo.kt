package com.hi.dhl.console

import com.hi.dhl.Common
import com.hi.dhl.utils.MessagesUtils
import com.hi.dhl.utils.StringUtils

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/10
 *     desc  :
 * </pre>
 */
data class RemoteMachineInfo(
    var remotePort: String,
    var remoteAddress: String,
    var remoteUser: String,
    var remoteBuildCommand: String? = null,
    var remoteRootDir: String = Common.remoteRootDir
) {
    companion object {
        fun createEmptyRemoteMachineInfo(): RemoteMachineInfo {
            return RemoteMachineInfo(
                remotePort = "", remoteAddress = "", remoteUser = ""
            )
        }
    }

    fun checkOrNull(): Boolean {
        val warringTitle = StringUtils.getMessage("sync.service.empry.title")
        return when {
            remotePort.isNullOrEmpty() -> {
                MessagesUtils.showMessageWarnDialog(
                    warringTitle, StringUtils.getMessage("sync.service.empry.port")
                )
                true
            }
            remoteAddress.isNullOrEmpty() -> {
                MessagesUtils.showMessageWarnDialog(
                    warringTitle, StringUtils.getMessage("sync.service.empry.address")
                )
                true
            }
            remoteUser.isNullOrEmpty() -> {
                MessagesUtils.showMessageWarnDialog(
                    warringTitle, StringUtils.getMessage("sync.service.empry.user")
                )
                true
            }
            remoteBuildCommand.isNullOrEmpty() -> {
                MessagesUtils.showMessageWarnDialog(
                    warringTitle, StringUtils.getMessage("sync.service.empry.command")
                )
                true
            }
            else -> false
        }
    }
}