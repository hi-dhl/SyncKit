package test

import com.hi.dhl.console.RemoteMachineInfo
import com.hi.dhl.ktkit.common.toJson
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/4
 *     desc  :
 * </pre>
 */

//class TestSyncKit : LightIdeaTestCase() {
//    fun testCopyFile() {
//        try {
//            val files = File("configure").listFiles()
//            println(files.size)
//            for (sourceFile in files) {
//                val destFile = File(".sync", sourceFile.name)
//                sourceFile to destFile
//            }
//        } catch (e: Exception) {
//
//        }
//    }
//}
fun main() {
//    val remoteMachineProjectDir = "~/SyncKit"
//    val workingDir = "./"
//    val stringBuilder = StringBuilder()
//    stringBuilder.append("rsync --archive --delete")
//    stringBuilder.append(" ")
//    stringBuilder.append("--partial --progress")
//    stringBuilder.append(" ")
//    stringBuilder.append("--rsync-path='mkdir -p ${remoteMachineProjectDir} && rsync'")
//    stringBuilder.append(" ")
//    stringBuilder.append("--times")
//    stringBuilder.append(" ")
//    stringBuilder.append(workingDir)
//    stringBuilder.append(" ")
//    stringBuilder.append("nas_build:${remoteMachineProjectDir}")
//    println(stringBuilder.toString())

//    try {
//        val files = File("/Users/denghuilong/Documents/project/github-owner/SyncKit/src/main/resources/configure").listFiles()
//        println(files.size)
//        val destDir = File("/Users/denghuilong/Documents/project/github-owner/SyncKit/.sync")
//        if(!destDir.exists()) destDir.mkdirs()
//        for (sourceFile in files) {
//            val destFile = File(destDir,sourceFile.name)
//            println("destFile = ${destFile.absolutePath}")
//            copyFile(sourceFile,destFile)
//        }
//    } catch (e: Exception) {
//
//    }

    val linx1 = RemoteMachineInfo(
        remotePort = "22",
        remoteAddress = "192.168.0.100",
        remoteUser = "root"
    )
    println(linx1.toJson())
}

private fun copyFile(src: File, dest: File) {
    Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING)
}
