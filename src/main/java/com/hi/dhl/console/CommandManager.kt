package com.hi.dhl.console

import com.hi.dhl.Common
import com.hi.dhl.common.DataManager
import com.hi.dhl.utils.FileUtils
import org.jetbrains.kotlin.konan.file.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/9
 *     desc  :
 * </pre>
 */
object CommandManager {

    private var machineInfo: RemoteMachineInfo = DataManager.getMachineInfo()

    fun compileAndroid(build: StringBuilder, extraCommand: String, projectBasePath: String): StringBuilder {
        val projectName = projectBasePath.substring(projectBasePath.lastIndexOf(File.separator) + 1)
        val remoteProjectPath = machineInfo.remoteRootDir + File.separator + projectName
        // 同步文件到远程
        syncLocalToRemote(build, remoteProjectPath)
        build.append(" && ")
        // 执行编译命令
        execRemoteCommand(build, remoteProjectPath, extraCommand)
        build.append(" && ")
        // 将结果拉回到本地
        syncRemoteToLocal(build, remoteProjectPath)
        // 执行安装命令
        if (FileUtils.isExists(FileUtils.SHELL_INSTALL_APK)) {
            build.append(" && ")
            val shellInstallApk = FileUtils.getSyncConfigPath(FileUtils.SHELL_INSTALL_APK)
            execLocalCommand(
                build,
                "chmod 777 ${shellInstallApk} && bash ${shellInstallApk}"
            )
        }
        return build
    }

    fun syncLocalToRemote(build: StringBuilder, remoteMachineWorkPath: String) {
//        /bin/bash -c "rsync -e 'ssh -p 22' --archive --delete  --progress --rsync-path='mkdir -p ~/rsync/demo && rsync'  ./ root@ip:~/rsync/demo"
        build.append("rsync -e 'ssh -p ${machineInfo.remotePort}' --archive --delete ")
//        build.append("--partial ") // 保留因故没有传完的文件，下次在续传
        build.append("--progress ")
        build.append("--rsync-path='mkdir -p ${remoteMachineWorkPath} && rsync' ")
        val ignoreFile =
            File(DataManager.projectBasePath() + File.separator + Common.syncConfigRootDir + File.separator + Common.syncConfigLocalIgnoreFile)
        if (ignoreFile.exists) {
            build.append("--exclude-from=${ignoreFile}  ")
        }
        build.append("./ ")
        build.append(" ${machineInfo.remoteUser}@${machineInfo.remoteAddress}:${remoteMachineWorkPath} ")
    }

    fun syncLocalFileToRemote(build: StringBuilder, filePath: String, remoteMachineWorkPath: String) {
        build.append("rsync -e 'ssh -p ${machineInfo.remotePort}' --archive --delete ")
        build.append("--progress ")
        build.append("--rsync-path='mkdir -p ${remoteMachineWorkPath} && rsync' ")
        build.append("${filePath} ")
        build.append(" ${machineInfo.remoteUser}@${machineInfo.remoteAddress}:${remoteMachineWorkPath} ")
    }

    fun execRemoteCommand(build: StringBuilder, remoteMachineWorkPath: String, extraCommand: String) {
//        ssh -p22 root@ip  "cd ~/SyncKit  && "
        build.append("ssh -p ${machineInfo.remotePort} ${machineInfo.remoteUser}@${machineInfo.remoteAddress}  ' source /etc/profile;cd ${remoteMachineWorkPath}  && ${extraCommand}' ")
    }

    fun execRemoteSellScript(build: StringBuilder, filePath: String, remoteMachineWorkPath: String) {
        syncLocalFileToRemote(build, filePath, remoteMachineWorkPath)
        build.append(" && ")
        val fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1)
        execRemoteCommand(build, remoteMachineWorkPath, "chmod 777 ${fileName} && bash ${fileName}")
    }

    private fun syncRemoteToLocal(build: StringBuilder, remoteMachineWorkPath: String) {
        build.append("rsync -e 'ssh -p ${machineInfo.remotePort}' --archive ")
//        build.append("--partial ") // 保留因故没有传完的文件，下次在续传
        build.append("--progress ")
        val remoteIncludeFile =
            File(DataManager.projectBasePath() + File.separator + Common.syncConfigRootDir + File.separator + Common.syncConfigRemoteIncludeFile)
        if (remoteIncludeFile.exists) {
            build.append("--include-from=${remoteIncludeFile}  ")
        }
        build.append(" ${machineInfo.remoteUser}@${machineInfo.remoteAddress}:${remoteMachineWorkPath} ")
        build.append("./ ")
    }

    fun execLocalCommand(build: StringBuilder, extraCommand: String) {
        build.append(extraCommand)
    }

}