package com.hi.dhl.console

import com.hi.dhl.Common
import com.hi.dhl.R
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

//    private var machineInfo: RemoteMachineInfo = DataManager.getMachineInfo()

    fun compileAndroid(build: StringBuilder,
                       extraCommand: String,
                       projectBasePath: String,
                       remoteMachineInfo: RemoteMachineInfo): StringBuilder {
        val projectName = projectBasePath.substring(projectBasePath.lastIndexOf(File.separator) + 1)
        val remoteProjectPath = remoteMachineInfo.remoteRootDir + File.separator + projectName
        // 同步文件到远程
        syncLocalToRemote(build, remoteProjectPath, remoteMachineInfo)
        build.append(" && ")
        // 执行编译命令
        execRemoteCommand(build, remoteProjectPath, extraCommand, remoteMachineInfo)
        build.append(" && ")
        // 将结果拉回到本地
        syncRemoteToLocal(build, remoteProjectPath, remoteMachineInfo)
        // 执行本地安装命令
        val shellPath = FileUtils.getShellScriptPath(projectBasePath,R.ShellScript.installApk)
        if (FileUtils.isExists(shellPath)) {
            build.append(" && ")
            execLocalCommand(
                build,
                "chmod 777 ${shellPath} && bash ${shellPath}"
            )
        }
        return build
    }

    fun syncLocalToRemote(build: StringBuilder,
                          remoteMachineWorkPath: String,
                          remoteMachineInfo: RemoteMachineInfo) {
//        /bin/bash -c "rsync -e 'ssh -p 22' --archive --delete  --progress --rsync-path='mkdir -p ~/rsync/demo && rsync'  ./ root@ip:~/rsync/demo"
        build.append("rsync -e 'ssh -p ${remoteMachineInfo.remotePort}' --archive --delete ")
//        build.append("--partial ") // 保留因故没有传完的文件，下次在续传
        build.append("--progress ")
        build.append("--rsync-path='mkdir -p ${remoteMachineWorkPath} && rsync' ")
        val ignoreFile =
            File(DataManager.projectBasePath() + File.separator + Common.syncConfigRootDir + File.separator + Common.syncConfigLocalIgnoreFile)
        if (ignoreFile.exists) {
            build.append("--exclude-from=${ignoreFile}  ")
        }
        build.append("./ ")
        build.append(" ${remoteMachineInfo.remoteUser}@${remoteMachineInfo.remoteAddress}:${remoteMachineWorkPath} ")
    }

    fun syncLocalFileToRemote(build: StringBuilder,
                              filePath: String,
                              remoteMachineWorkPath: String,
                              remoteMachineInfo: RemoteMachineInfo) {
        build.append("rsync -e 'ssh -p ${remoteMachineInfo.remotePort}' --archive --delete ")
        build.append("--progress ")
        build.append("--rsync-path='mkdir -p ${remoteMachineWorkPath} && rsync' ")
        build.append("${filePath} ")
        build.append(" ${remoteMachineInfo.remoteUser}@${remoteMachineInfo.remoteAddress}:${remoteMachineWorkPath} ")
    }

    fun execRemoteCommand(build: StringBuilder,
                          remoteMachineWorkPath: String,
                          extraCommand: String,
                          remoteMachineInfo: RemoteMachineInfo) {
//        ssh -p22 root@ip  "cd ~/SyncKit  && "
        build.append("ssh -p ${remoteMachineInfo.remotePort} ${remoteMachineInfo.remoteUser}@${remoteMachineInfo.remoteAddress}  ' source /etc/profile;cd ${remoteMachineWorkPath}  && ${extraCommand}' ")
    }

    fun execRemoteSellScript(build: StringBuilder,
                             filePath: String,
                             remoteMachineWorkPath: String,
                             remoteMachineInfo: RemoteMachineInfo) {
        syncLocalFileToRemote(build, filePath, remoteMachineWorkPath, remoteMachineInfo)
        build.append(" && ")
        val fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1)
        execRemoteCommand(build, remoteMachineWorkPath, "chmod 777 ${fileName} && bash ${fileName}", remoteMachineInfo)
    }

    private fun syncRemoteToLocal(build: StringBuilder,
                                  remoteMachineWorkPath: String,
                                  remoteMachineInfo: RemoteMachineInfo) {
        build.append("rsync -e 'ssh -p ${remoteMachineInfo.remotePort}' --archive ")
//        build.append("--partial ") // 保留因故没有传完的文件，下次在续传
        build.append("--progress ")
        val remoteIncludeFile =
            File(DataManager.projectBasePath() + File.separator + Common.syncConfigRootDir + File.separator + Common.syncConfigRemoteIncludeFile)
        if (remoteIncludeFile.exists) {
            build.append("--include-from=${remoteIncludeFile}  ")
        }
        build.append(" ${remoteMachineInfo.remoteUser}@${remoteMachineInfo.remoteAddress}:${remoteMachineWorkPath} ")
        build.append("./ ")
    }

    fun execLocalCommand(build: StringBuilder, extraCommand: String) {
        build.append(extraCommand)
    }

}