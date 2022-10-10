package com.hi.dhl.utils

import com.hi.dhl.common.Common
import com.hi.dhl.console.RemoteMachineInfo
import com.hi.dhl.ktkit.common.fromJson
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.JarURLConnection
import java.net.URL
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
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

    fun copyToTarget(basePath: String) {
        try {
            val destDir = File(basePath + File.separator + Common.syncConfigRootDir)
            if (destDir.exists()) {
                return
            }
            destDir.mkdirs()
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
        val classLoader : ClassLoader? = FileUtils::class.java.getClassLoader()
        if (classLoader != null) {
            useKotlinCopyJarResource(classLoader, destDir)
        } else {
            LogUtils.logE("use kotlin getClassLoader is null")
            JavaJarUtils.copy(destDir)
        }
    }

    @JvmStatic
    fun useKotlinCopyJarResource(classLoader: ClassLoader, destDir: File) {
        val url: URL? = classLoader.getResource(Common.resourceConfigDir)
        if (url == null) {
            LogUtils.logE("use kotlin classLoader.getResource is null")
            return
        }
        val jarPath: String = url.toString().substring(0, url.toString().indexOf("!/") + 2)

        val jarURL = URL(jarPath)
        val jarCon: JarURLConnection = jarURL.openConnection() as JarURLConnection
        val jarFile: JarFile = jarCon.getJarFile()
        val jarEntrys: Enumeration<JarEntry> = jarFile.entries()

        while (jarEntrys.hasMoreElements()) {
            val entry: JarEntry = jarEntrys.nextElement()
            val name: String = entry.getName()
            if (name.contains(Common.resourceConfigDir)) {

                if (name.contains("icons") || name.contains("com")) {
                    continue
                }

                when {
                    name.contains(Common.syncConfigServiceDir) -> {
                        if (entry.isDirectory) {
                            File(destDir, Common.syncConfigServiceDir).mkdirs()
                        } else {
                            LogUtils.logI("copyDestDir name = ${name}")
                            val endName = name.substring(name.lastIndexOf("/") + 1)
                            copyFile(
                                classLoader.getResourceAsStream(name),
                                File(File(destDir, Common.syncConfigServiceDir), endName)
                            )
                        }
                    }
                    name.contains(Common.syncConfigScriptDir) -> {
                        if (entry.isDirectory) {
                            File(destDir, Common.syncConfigScriptDir).mkdirs()
                        } else {
                            LogUtils.logI("copyDestDir name = ${name}")
                            val endName = name.substring(name.lastIndexOf("/") + 1)
                            copyFile(
                                classLoader.getResourceAsStream(name),
                                File(File(destDir, Common.syncConfigScriptDir), endName)
                            )
                        }
                    }
                    !entry.isDirectory() -> {
                        val endName = name.substring(name.lastIndexOf("/") + 1)
                        LogUtils.logI("copyDestDir name = ${name}")
                        copyFile(
                            classLoader.getResourceAsStream(name), File(destDir, endName)
                        )
                    }
                }

            }
        }
    }

    fun isExists(filePath: String): Boolean {
        val file = File(filePath)
        return file.exists()
    }

    fun readConfigJson(configFilePath: String): RemoteMachineInfo {
        val file = File(configFilePath)
        if (!file.exists()) {
            LogUtils.logW("file not exits ${file.absolutePath}")
            return RemoteMachineInfo.createEmptyRemoteMachineInfo()
        }
        val content = file.readText()
        return content.fromJson()
    }

    fun saveConfigJson(json: String, configFilePath: String) {
        val file = File(configFilePath)
        file.writeText(json)
    }

    fun readConfigText(configFilePath: String): String {
        val file = File(configFilePath)
        if (!file.exists()) {
            LogUtils.logW("file not exits ${file.absolutePath}")
            return ""
        }
        return file.readText()
    }

    fun saveConfigText(text: String, configFilePath: String) {
        val file = File(configFilePath)
        file.writeText(text)
    }

    fun getSyncConfigPath(basePath: String, fileName: String): String {
        return basePath + File.separator + Common.syncConfigRootDir + File.separator + fileName
    }

    fun getSyncServicePath(basePath: String, fileName: String): String {
        return basePath + File.separator + Common.syncConfigRootDir + File.separator + Common.syncConfigServiceDir + File.separator + fileName
    }

    fun getShellScriptPath(basePath: String, shellName: String): String {
        return basePath + File.separator + Common.syncConfigRootDir + File.separator + Common.syncConfigScriptDir + File.separator + shellName
    }

    fun deleteDirectory(directory: Path) {
        if (Files.exists(directory)) {
            Files.walkFileTree(directory, object : SimpleFileVisitor<Path>() {
                override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
                    file?.let { Files.delete(it) }
                    return super.visitFile(file, attrs)
                }

                override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
                    dir?.let { Files.delete(it) }
                    return FileVisitResult.CONTINUE
                }
            })
        }
    }
}