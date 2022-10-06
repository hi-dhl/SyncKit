package com.hi.dhl.utils

import com.intellij.openapi.ui.Messages

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/15
 *     desc  :
 * </pre>
 */
object MessagesUtils {

    fun showMessageWarnDialog(title: String, message: String) {
        Messages.showMessageDialog(
            message,
            title,
            Messages.getWarningIcon()
        );
    }

    fun showOkCancelDialog(title: String, message: String): Int {
        return Messages.showOkCancelDialog(
            message,
            title,
            "OK",
            "Cancel",
            Messages.getQuestionIcon()
        )
    }

}