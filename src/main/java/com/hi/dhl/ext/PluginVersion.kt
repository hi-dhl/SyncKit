package com.hi.dhl.ext

import com.hi.dhl.common.R
import com.hi.dhl.utils.FileUtils
import java.io.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/10/28
 *     desc  :
 * </pre>
 */

object PluginVersion {
    val VERSION_2_1 = "2.1"
}

fun upgrad(localProjectBasePath: String) {
    upgradVersionTo_2_1(localProjectBasePath)
}

private fun upgradVersionTo_2_1(localProjectBasePath: String) {
    if (PluginVersion.VERSION_2_1.equals(R.String.pluginVersion)) {
        val config = FileUtils.getSyncConfigDir(localProjectBasePath)
        if (!FileUtils.isExists(config)) {
            return
        }

        var src = FileUtils.getShellScriptPath(localProjectBasePath, R.ShellScript.installSshpass)
        var dest = FileUtils.getShellScriptDirPath(localProjectBasePath)
        if (!FileUtils.isExists(src)) {
            FileUtils.copyScriptFile(R.ShellScript.installSshpass, File(dest))
        }

        src = FileUtils.getFilesPath(localProjectBasePath, R.sshpassFile)
        dest = FileUtils.getFilesDirPath(localProjectBasePath)
        if (!FileUtils.isExists(src)) {
            FileUtils.copyFile(R.sshpassFile, File(dest))
        }
    }
}