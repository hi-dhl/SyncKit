package com.hi.dhl.console

import com.hi.dhl.Common
import com.hi.dhl.utils.FileUtils
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.konan.file.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/9
 *     desc  :
 * </pre>
 */
object CommandManager {

    lateinit var machineInfo: RemoteMachineInfo

    private lateinit var baseWorkingDir: String
    private val configName: String = Common.syncDefaultConfigJson

    fun init(project: Project) {
        baseWorkingDir = project.basePath ?: "./"
        machineInfo = FileUtils.readServiceConfig(
            FileUtils.getSyncServicePath(configName)
        )
    }

    fun compileAndroid(extraCommand: String = ""): String {
        val projectName = baseWorkingDir.substring(baseWorkingDir.lastIndexOf(File.separator) + 1)
        val remoteMachineProjectDir = machineInfo.remoteRootDir + File.separator + projectName
        val stringBuilder = StringBuilder()
        // 同步文件到远程
        syncLocalToRmote(stringBuilder, remoteMachineProjectDir)
        stringBuilder.append(" && ")
        // 执行编译命令
        execRemoteCommand(stringBuilder, remoteMachineProjectDir, extraCommand)
        stringBuilder.append(" && ")
        // 将结果拉回到本地
        syncRemoteToLocal(stringBuilder, remoteMachineProjectDir)
        // 执行安装命令
        if (FileUtils.isExists(FileUtils.SHELL_INSTALL_APK)) {
            stringBuilder.append(" && ")
            execLocalCommand(
                stringBuilder,
                "chmod 777 ${FileUtils.getSyncConfigPath(FileUtils.SHELL_INSTALL_APK)} && bash ${
                    FileUtils.getSyncConfigPath(
                        FileUtils.SHELL_INSTALL_APK
                    )
                }"
            )
        }
        return stringBuilder.toString()
    }

    private fun syncLocalToRmote(build: StringBuilder, remoteMachineProjectDir: String) {
//        /bin/bash -c "rsync -e 'ssh -p 22' --archive --delete  --progress --rsync-path='mkdir -p ~/rsync/demo && rsync'  ./ root@ip:~/rsync/demo"
        build.append("rsync -e 'ssh -p ${machineInfo.remotePort}' --archive --delete ")
//        build.append("--partial ") // 保留因故没有传完的文件，下次在续传
        build.append("--progress ")
        build.append("--rsync-path='mkdir -p ${remoteMachineProjectDir} && rsync' ")
        val ignoreFile =
            File(baseWorkingDir + File.separator + Common.syncConfigRootDir + File.separator + Common.syncConfigLocalIgnoreFile)
        if (ignoreFile.exists) {
            build.append("--exclude-from=${ignoreFile}  ")
        }
        build.append("./ ")
        build.append(" ${machineInfo.remoteUser}@${machineInfo.remoteAddress}:${remoteMachineProjectDir} ")
    }

    private fun execRemoteCommand(
        build: StringBuilder,
        remoteMachineProjectDir: String,
        extraCommand: String
    ) {
//        ssh -p22 root@ip  "cd ~/SyncKit  && "
        build.append("ssh -p ${machineInfo.remotePort} ${machineInfo.remoteUser}@${machineInfo.remoteAddress}  ' source /etc/profile;cd ${remoteMachineProjectDir}  && ${extraCommand}' ")
    }

    private fun syncRemoteToLocal(build: StringBuilder, remoteMachineProjectDir: String) {
        build.append("rsync -e 'ssh -p ${machineInfo.remotePort}' --archive ")
//        build.append("--partial ") // 保留因故没有传完的文件，下次在续传
        build.append("--progress ")
        val remoteIncludeFile =
            File(baseWorkingDir + File.separator + Common.syncConfigRootDir + File.separator + Common.syncConfigRemoteIncludeFile)
        if (remoteIncludeFile.exists) {
            build.append("--include-from=${remoteIncludeFile}  ")
        }
        build.append(" ${machineInfo.remoteUser}@${machineInfo.remoteAddress}:${remoteMachineProjectDir} ")
        build.append("./ ")
    }

    private fun execLocalCommand(
        build: StringBuilder,
        extraCommand: String
    ) {
        build.append(extraCommand)
    }

}