package com.hi.dhl.console

import com.hi.dhl.common.Common
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
    var remoteHost: String,
    var remoteUser: String,
    var remoteBuildCommand: String? = null,
    var remoteRootDir: String = Common.remoteRootDir,
    var launchActivity: String? = null,
    var sdkDir: String? = null,
    var ndkDir: String? = null,
    var sshPublicKey: String? = null
) {
    var useGradlew = 0

    fun checkOrNull(): Boolean {
        val warringTitle = StringUtils.getMessage("sync.service.empry.title")
        return when {
            remotePort.isNullOrEmpty() -> {
                MessagesUtils.showMessageWarnDialog(
                    warringTitle, StringUtils.getMessage("sync.service.empry.port")
                )
                true
            }
            remoteHost.isNullOrEmpty() -> {
                MessagesUtils.showMessageWarnDialog(
                    warringTitle, StringUtils.getMessage("sync.service.empry.host")
                )
                true
            }
            remoteUser.isNullOrEmpty() -> {
                MessagesUtils.showMessageWarnDialog(
                    warringTitle, StringUtils.getMessage("sync.service.empry.user")
                )
                true
            }
            else -> false
        }
    }

    fun isSelectGradlew(): Boolean {
        return when (useGradlew) {
            SELECT_GRADLEW -> true
            NOT_SELECT_GRADLEW -> false
            else -> true
        }
    }

    fun setGradlew(select: Boolean) {
        if (select) {
            useGradlew = SELECT_GRADLEW;
        } else {
            useGradlew = NOT_SELECT_GRADLEW;
        }
    }

    companion object{
        const val SELECT_GRADLEW = 1
        const val NOT_SELECT_GRADLEW = 2

        fun createEmptyRemoteMachineInfo(): RemoteMachineInfo {
            return RemoteMachineInfo(
                remotePort = "", remoteHost = "", remoteUser = ""
            )
        }
    }
}