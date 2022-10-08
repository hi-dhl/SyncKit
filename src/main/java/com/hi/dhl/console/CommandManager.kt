package com.hi.dhl.console

import com.hi.dhl.common.Common
import com.hi.dhl.common.R
import com.hi.dhl.utils.FileUtils
import java.io.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/9
 *     desc  :
 * </pre>
 */
object CommandManager {

    fun compileAndroid(build: StringBuilder,
                       extraCommand: String,
                       projectBasePath: String,
                       remoteMachineInfo: RemoteMachineInfo): StringBuilder {
        val projectName = projectBasePath.substring(projectBasePath.lastIndexOf(File.separator) + 1)
        val remoteProjectPath = remoteMachineInfo.remoteRootDir + File.separator + projectName + File.separator
        // 同步文件到远程
        syncLocalToRemote(build, remoteProjectPath, projectBasePath, remoteMachineInfo)
        build.append(" && ")
        // 执行编译命令
        execRemoteCommand(build, remoteProjectPath, extraCommand, remoteMachineInfo)
        build.append(" && ")
        // 将结果拉回到本地
        syncRemoteToLocal(build, remoteProjectPath, projectBasePath, remoteMachineInfo)
        // 执行本地安装命令
        val shellPath = FileUtils.getShellScriptPath(projectBasePath, R.ShellScript.installApk)
        if (FileUtils.isExists(shellPath)) {
            build.append(" && ")

            var execShellScript = ""
            if (!remoteMachineInfo.launchActivity.isNullOrEmpty()
                && !remoteMachineInfo.launchActivity.equals(R.String.ui.tfLaunchActivity)) {
                execShellScript = "chmod 777 ${shellPath} && bash ${shellPath} ${remoteMachineInfo.launchActivity} "
            } else {
                execShellScript = "chmod 777 ${shellPath} && bash ${shellPath} "
            }

            execLocalCommand(build, execShellScript)
        }
        return build
    }

    fun syncLocalToRemote(build: StringBuilder,
                          remoteMachineWorkPath: String,
                          localProjectBasePath: String,
                          remoteMachineInfo: RemoteMachineInfo) {
//        /bin/bash -c "rsync -e 'ssh -p 22' --archive --delete  --progress --rsync-path='mkdir -p ~/rsync/demo && rsync'  ./ root@ip:~/rsync/demo"
        build.append("rsync -e 'ssh -p ${remoteMachineInfo.remotePort}' --archive --delete ")
//        build.append("--partial ") // 保留因故没有传完的文件，下次在续传
        build.append("--progress ")
        var sdkdir: String = ""
        if(!remoteMachineInfo.sdkDir.isNullOrEmpty()
            && !remoteMachineInfo.sdkDir.equals(R.String.ui.tfSDK)){
            sdkdir = ";echo sdk.dir=${remoteMachineInfo.sdkDir} >> ${remoteMachineWorkPath + File.separator + Common.localProperties}"
        }
        var ndkDir: String = ""
        if(!remoteMachineInfo.ndkDir.isNullOrEmpty()
            && !remoteMachineInfo.ndkDir.equals(R.String.ui.tfNDK)){
            ndkDir = ";echo ndk.dir=${remoteMachineInfo.ndkDir} >> ${remoteMachineWorkPath + File.separator + Common.localProperties}"
        }
        build.append("--rsync-path='mkdir -p ${remoteMachineWorkPath};rm -rf ${remoteMachineWorkPath + File.separator + Common.localProperties}${sdkdir}${ndkDir} && rsync' ")
        val localIgnoreFile =
            File(localProjectBasePath + File.separator + Common.syncConfigRootDir + File.separator + Common.syncConfigLocalIgnoreFile)
        if (localIgnoreFile.exists()) {
            build.append("--exclude-from=${localIgnoreFile}  ")
        }
        build.append("${localProjectBasePath + File.separator} ")
        build.append(" ${remoteMachineInfo.remoteUser}@${remoteMachineInfo.remoteHost}:${remoteMachineWorkPath} ")
    }

    fun syncLocalFileToRemote(build: StringBuilder,
                              filePath: String,
                              remoteMachineWorkPath: String,
                              remoteMachineInfo: RemoteMachineInfo) {
        build.append("rsync -e 'ssh -p ${remoteMachineInfo.remotePort}' --archive --delete ")
        build.append("--progress ")
        build.append("--rsync-path='mkdir -p ${remoteMachineWorkPath} && rsync' ")
        build.append("${filePath} ")
        build.append(" ${remoteMachineInfo.remoteUser}@${remoteMachineInfo.remoteHost}:${remoteMachineWorkPath} ")
    }

    fun execRemoteCommand(build: StringBuilder,
                          remoteMachineWorkPath: String,
                          extraCommand: String,
                          remoteMachineInfo: RemoteMachineInfo) {
//        ssh -p22 root@ip  "cd ~/SyncKit  && "
        build.append("ssh -p ${remoteMachineInfo.remotePort} ${remoteMachineInfo.remoteUser}@${remoteMachineInfo.remoteHost}  ' set +e source  ~/.bashrc > /dev/null 2>&1; source ~/.bash_profile > /dev/null 2>&1; source ~/.zshrc > /dev/null 2>&1;cd ${remoteMachineWorkPath}  && ${extraCommand}' ")
    }

    fun execRemoteSellScript(build: StringBuilder,
                             filePath: String,
                             remoteMachineWorkPath: String,
                             remoteMachineInfo: RemoteMachineInfo) {
        val remoteScriptPath = remoteMachineWorkPath + File.separator + Common.syncConfigScriptDir
        syncLocalFileToRemote(build, filePath, remoteScriptPath, remoteMachineInfo)
        build.append(" && ")
        val fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1)
        var execShellScript: String
        if (fileName.contains("apk")
            && !remoteMachineInfo.launchActivity.isNullOrEmpty()
            && !remoteMachineInfo.launchActivity.equals(R.String.ui.tfLaunchActivity)) {
            execShellScript = "chmod 777 ${fileName} && bash ${fileName} ${remoteMachineInfo.launchActivity} "
        } else if(filePath.contains(R.ShellScript.installSSHPub) && !remoteMachineInfo.sshPublicKey.isNullOrEmpty()){
            execShellScript = "chmod 777 ${fileName} && bash ${fileName} ${remoteMachineInfo.sshPublicKey} "
        }else {
            execShellScript = "chmod 777 ${fileName} && bash ${fileName} "
        }
        execRemoteCommand(build, remoteScriptPath, execShellScript, remoteMachineInfo)
    }

    private fun syncRemoteToLocal(build: StringBuilder,
                                  remoteMachineWorkPath: String,
                                  localProjectBasePath: String,
                                  remoteMachineInfo: RemoteMachineInfo) {
        build.append("rsync -e 'ssh -p ${remoteMachineInfo.remotePort}' --archive ")
//        build.append("--partial ") // 保留因故没有传完的文件，下次在续传
        build.append("--progress ")
        val remoteIncludeFile =
            File(localProjectBasePath + File.separator + Common.syncConfigRootDir + File.separator + Common.syncConfigRemoteIncludeFile)
        if (remoteIncludeFile.exists()) {
            build.append("--include-from=${remoteIncludeFile}  ")
        }

        val remoteIgnoreFile =
            File(localProjectBasePath + File.separator + Common.syncConfigRootDir + File.separator + Common.syncConfigRemoteIgnoreFile)
        if (remoteIgnoreFile.exists()) {
            build.append("--exclude-from=${remoteIgnoreFile}  ")
        }
        
        build.append(" ${remoteMachineInfo.remoteUser}@${remoteMachineInfo.remoteHost}:${remoteMachineWorkPath} ")
        build.append("${localProjectBasePath} ")
    }

    fun execLocalCommand(build: StringBuilder, extraCommand: String) {
        build.append(extraCommand)
    }

}