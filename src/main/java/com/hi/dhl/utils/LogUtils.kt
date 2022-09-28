package com.hi.dhl.utils

import com.hi.dhl.common.Common
import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.diagnostic.Logger


/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/4
 *     desc  :
 * </pre>
 */
object LogUtils {

    @JvmStatic
    fun logI(content: String, type: NotificationDisplayType? = NotificationDisplayType.NONE) {
        if(Common.isDebug){
            log(content, type, NotificationType.INFORMATION)
        }
    }

    @JvmStatic
    fun logW(content: String, type: NotificationDisplayType? = NotificationDisplayType.NONE) {
        log(content, type, NotificationType.WARNING)
    }

    @JvmStatic
    fun logE(content: String, type: NotificationDisplayType? = NotificationDisplayType.NONE) {
        log(content, type, NotificationType.ERROR)
    }

    @JvmStatic
    fun log(
        content: String,
        displayType: NotificationDisplayType? = NotificationDisplayType.NONE,
        type: NotificationType = NotificationType.WARNING
    ) {
        when (displayType) {
            // 无弹框，不展示, 输入日志到 event log
            NotificationDisplayType.NONE -> {
                NotificationGroupManager.getInstance()
                    .getNotificationGroup("SyncKitEventLog")
            }
            // 弹出通知 10s 后小时
            NotificationDisplayType.BALLOON -> {
                NotificationGroupManager.getInstance()
                    .getNotificationGroup("SyncKitBallon")

            }
            // 弹出通知，一直在屏幕上显示，需要主动关闭
            else -> {
                NotificationGroupManager.getInstance()
                    .getNotificationGroup("SyncKitStickBallon")
            }
        }?.createNotification(content, type)?.notify(null);

        Logger.getInstance(Common.projectTitle).warn(content)
    }
}