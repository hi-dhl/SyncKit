package com.hi.dhl.action.listener

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/22
 *     desc  :
 * </pre>
 */
interface BuildProcessListener {
    fun onStart()
    fun onStop(ExitCode: Int)
}