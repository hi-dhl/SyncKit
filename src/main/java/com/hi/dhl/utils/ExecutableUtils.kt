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
    var executableBashPath: String = ""

    fun findExecutable(): String {
        LogUtils.logI(executablePath)
        if (!executablePath.isEmpty()) {
            return executablePath
        }
        if (compareVersion(SystemInfoRt.OS_VERSION, "10.15.0") >= 0) {
            executablePath = findExecutableOnPath("zsh")
            if (executablePath.isEmpty()) {
                executablePath = findExecutableOnPath("bash")
            }
        } else {
            executablePath = findExecutableOnPath("bash")
        }
        return if (!executablePath.isEmpty()) executablePath
        else if (compareVersion(SystemInfoRt.OS_VERSION, "10.15.0") >= 0) "/bin/zsh"
        else "/bin/bash"
    }

    fun findExecutableInServiceOnPath(): String {
        if (!executableBashPath.isEmpty()) {
            return executableBashPath
        }
        executableBashPath = findExecutableOnPath("bash")
        return if (!executableBashPath.isEmpty()) executableBashPath
        else "/bin/bash"
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

    fun compareVersion(version1: String?, version2: String?): Int {
        if (version1 == null || version2 == null) {
            return -1
        }
        val versions1 = version1.split(".")
        val versions2 = version2.split(".")
        var index = 0
        val minLength = Math.min(versions1.size, versions2.size)
        var diff = 0

        while (index < minLength
            && (versions1[index].length - versions2[index].length) == 0
            && versions1[index].compareTo(versions2[index]) == 0
        ) {
            ++index
        }
        if(index < minLength){
            diff = versions1[index].compareTo(versions2[index])
        }
        return diff
    }
}

fun main() {
    val result = ExecutableUtils.compareVersion("10.15.7" ,"10.14.7");
    println(result)
}