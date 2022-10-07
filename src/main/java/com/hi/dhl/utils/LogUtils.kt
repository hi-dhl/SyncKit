package com.hi.dhl.utils

import com.hi.dhl.common.Common
import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages


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
        if (Common.isDebug) {
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
        type: NotificationType = NotificationType.WARNING,
        project: Project? = null
    ) {
        when (displayType) {
            // 无弹框，不展示, 输入日志到 event log
            NotificationDisplayType.NONE -> {
                // 2020.03 推荐使用新的 API
//                NotificationGroupManager.getInstance()
//                    .getNotificationGroup("SyncKitEventLog")

                // 2021.3 将被移除
                NotificationGroup(
                    "SyncKitEventLog",
                    NotificationDisplayType.NONE,
                    true,
                    null,
                    Messages.getInformationIcon()
                )

            }
            // 弹出通知 10s 后小时
            NotificationDisplayType.BALLOON -> {
//                NotificationGroupManager.getInstance()
//                    .getNotificationGroup("SyncKitBallon")

                NotificationGroup(
                    "SyncKitBallon",
                    NotificationDisplayType.BALLOON,
                    true,
                    null,
                    Messages.getInformationIcon()
                )

            }
            // 弹出通知，一直在屏幕上显示，需要主动关闭
            NotificationDisplayType.TOOL_WINDOW -> {
//                NotificationGroupManager.getInstance()
//                    .getNotificationGroup("SyncKitToolWindow")

                NotificationGroup(
                    "SyncKitToolWindow",
                    NotificationDisplayType.TOOL_WINDOW,
                    true,
                    null,
                    Messages.getInformationIcon()
                )

            }
            else -> {
//                NotificationGroupManager.getInstance()
//                    .getNotificationGroup("SyncKitStickBallon")

                NotificationGroup(
                    "SyncKitStickBallon",
                    NotificationDisplayType.STICKY_BALLOON,
                    true,
                    null,
                    Messages.getInformationIcon()
                )

            }
        }.createNotification(content, type).notify(project)

        Logger.getInstance(Common.projectTitle).warn(content)
    }
}