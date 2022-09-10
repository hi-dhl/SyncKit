package com.hi.dhl.utils

import com.hi.dhl.Common
import com.hi.dhl.console.RemoteMachineInfo
import com.hi.dhl.ktkit.common.fromJson
import java.io.File
import java.io.InputStream
import java.net.JarURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/9
 *     desc  :
 * </pre>
 */
object FileUtils {
    public val SHELL_INSTALL_APK = "install-apk.sh"
    private lateinit var basePath: String

    fun copyToTarget(basePath: String) {
        try {
            this.basePath = basePath
            val destDir = File(basePath + File.separator + Common.syncConfigRootDir)
            if (destDir.exists()) {
                return
            }
            destDir.mkdirs()
            val destFile = File(destDir, Common.syncConfigLocalIgnoreFile)
            LogUtils.logI("destFile = ${destFile.absolutePath}");
            copyDestDir(destDir)
        } catch (e: Exception) {
            LogUtils.logE("init fail ${e}");
        }
    }

    private fun copyFile(src: File, dest: File) {
        Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING)
    }

    private fun copyFile(src: InputStream, dest: File) {
        Files.copy(src, dest.toPath(), StandardCopyOption.REPLACE_EXISTING)
    }

    private fun copyDestDir(destDir: File) {
        val classLoader = javaClass.getClassLoader()
        val url: URL = classLoader.getResource(Common.resourceConfigDir)
        val jarPath: String = url.toString().substring(0, url.toString().indexOf("!/") + 2)

        val jarURL = URL(jarPath)
        val jarCon: JarURLConnection = jarURL.openConnection() as JarURLConnection
        val jarFile: JarFile = jarCon.getJarFile()
        val jarEntrys: Enumeration<JarEntry> = jarFile.entries()

        while (jarEntrys.hasMoreElements()) {
            val entry: JarEntry = jarEntrys.nextElement()
            val name: String = entry.getName()
            if (name.contains(Common.resourceConfigDir)) {

                if (name.contains(Common.syncConfigService)) {
                    if (entry.isDirectory) {
                        File(destDir, Common.syncConfigService).mkdirs()
                    } else {
                        LogUtils.logI("copyDestDir name = ${name}")
                        val endName = name.substring(name.lastIndexOf("/") + 1)
                        copyFile(
                            classLoader.getResourceAsStream(name),
                            File(File(destDir, Common.syncConfigService), endName)
                        )
                    }
                } else if (!entry.isDirectory()) {
                    LogUtils.logI("copyDestDir name = ${name}")
                    val endName = name.substring(name.lastIndexOf("/") + 1)
                    copyFile(
                        classLoader.getResourceAsStream(name),
                        File(destDir, endName)
                    )
                }

            }
        }
    }

    fun isExists(fileName: String): Boolean {
        val file = File(basePath + File.separator + Common.syncConfigRootDir, fileName)
        return file.exists()
    }

    fun readServiceConfig(configName: String): RemoteMachineInfo {
        val file = File(configName)
        val content = file.readText()
        return content.fromJson()
    }

    fun saveServiceConfig(json: String, configName: String) {
        val file = File(configName)
        file.writeText(json)
    }

    fun getSyncConfigPath(fileName: String) = File(basePath + File.separator +
            Common.syncConfigRootDir, fileName).absolutePath

    fun getSyncServicePath(fileName: String) = File(basePath + File.separator +
            Common.syncConfigRootDir + File.separator +
            Common.syncConfigService, fileName).absolutePath
}