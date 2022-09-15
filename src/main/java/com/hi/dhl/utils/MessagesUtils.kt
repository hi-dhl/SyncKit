package com.hi.dhl.utils

import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.NlsContexts
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/15
 *     desc  :
 * </pre>
 */
object MessagesUtils {
    fun showMessageWarnDialog(
        message: String,
        title: String,
    ) {
        Messages.showMessageDialog(
            "Not initialized, please click the Sync Init",
            "Sync Kit",
            Messages.getWarningIcon()
        );
    }
}