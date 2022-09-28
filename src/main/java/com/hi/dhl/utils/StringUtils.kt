package com.hi.dhl.utils

import com.intellij.AbstractBundle

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/28
 *     desc  :
 * </pre>
 */
val BUNDLE = "message.String_zh_CN"

object StringUtils : AbstractBundle(BUNDLE) {
    override fun getMessage(key: String, vararg params: Any?): String {
        return super.getMessage(key, *params)
    }
}