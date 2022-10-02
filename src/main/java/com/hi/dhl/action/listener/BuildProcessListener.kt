package com.hi.dhl.action.listener

import com.intellij.execution.process.ProcessEvent

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/22
 *     desc  :
 * </pre>
 */
interface BuildProcessListener {
    fun onStart(startTime: Long)
    fun onStop(processEvent: ProcessEvent, endTime: Long)
}