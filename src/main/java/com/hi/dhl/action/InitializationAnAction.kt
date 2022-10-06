package com.hi.dhl.action

import com.hi.dhl.action.base.BaseAnAction
import com.hi.dhl.common.R
import com.hi.dhl.utils.LogUtils
import com.hi.dhl.utils.MessagesUtils
import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/15
 *     desc  :
 * </pre>
 */
class InitializationAnAction : BaseAnAction(R.String.ui.actionInitialization) {

    override fun actionPerformed(e: AnActionEvent) {
        super.actionPerformed(e)
        try {

            if (MessagesUtils.showOkCancelDialog(R.String.ui.confirmDialogTitle, R.String.ui.confirmDialogTip) == 0) {
                syncContentProvide.deleteSyncRootDir()
                syncContentProvide.initData()
                LogUtils.log(
                    "INIT SUCCESSFUL",
                    NotificationDisplayType.BALLOON,
                    NotificationType.INFORMATION,
                    project
                )
            }

        } catch (e: Exception) {
            LogUtils.logE("exec action fail ${e}");
        }
    }

}