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
    val VERSION_2_0 = "2.0"
    val VERSION_2_1 = "2.1"
    val VERSION_2_2 = "2.2"
    val VERSION_2_3 = "2.3"
    val CURRENT_VERSION = VERSION_2_3
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
        if (!FileUtils.isExists(src)) {
            val dest = FileUtils.getShellScriptDirPath(localProjectBasePath)
            FileUtils.copyScriptFile(R.ShellScript.installSshpass, File(dest))
        }

        src = FileUtils.getFilesPath(localProjectBasePath, R.sshpassFile)
        if (!FileUtils.isExists(src)) {
            val dest = FileUtils.getFilesDirPath(localProjectBasePath)
            FileUtils.copyFile(R.sshpassFile, File(dest))
        }
    }
}