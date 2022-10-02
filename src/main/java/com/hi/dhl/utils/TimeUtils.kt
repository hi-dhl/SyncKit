package com.hi.dhl.utils

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/10/2
 *     desc  :
 * </pre>
 */
object TimeUtils {
    fun formatTime(startTime: Long, endTime: Long): String {
        val duration = endTime - startTime
        val seconds = duration / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val build = StringBuilder()
        if (hours > 0) {
            build.append("${hours}h ")
        }
        if (minutes > 0) {
            build.append("${minutes}m ")
        }
        if (seconds > 0) {
            build.append("${seconds}s")
        }
        return build.toString()
    }
}