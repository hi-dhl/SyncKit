package com.hi.dhl.utils

import com.intellij.openapi.util.SystemInfoRt
import java.io.File

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/10/15
 *     desc  :
 * </pre>
 */
object ExecutableUtils {
    var executablePath: String = ""

    fun findExecutable(): String {
        LogUtils.logI(executablePath)
        if (!executablePath.isNullOrEmpty()) {
            return executablePath
        }
        executablePath = findExecutableOnPath("bash")
        return if (!executablePath.isNullOrEmpty()) executablePath else "/bin/bash"
    }

    fun findExecutableOnPath(name: String): String {
        val endName = if (SystemInfoRt.isWindows) name + ".exe" else name
        val paths = System.getenv("PATH").split(File.pathSeparator)
        for (dirname in paths) {
            File(dirname, endName).apply {
                if (isFile() && canExecute()) {
                    return absolutePath
                }
            }
        }
        LogUtils.logE("should haven't found the executable")
        return ""
    }

}