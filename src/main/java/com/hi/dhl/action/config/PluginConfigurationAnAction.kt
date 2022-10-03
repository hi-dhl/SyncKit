package com.hi.dhl.action.config

import com.hi.dhl.action.base.AbstractAnAction
import com.hi.dhl.common.R
import com.hi.dhl.common.SyncContentProvide
import com.hi.dhl.ui.ConfigCommandDialog
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/22
 *     desc  :
 * </pre>
 */
class PluginConfigurationAnAction : AbstractAnAction(R.String.ui.actionPlugin) {

    override fun beforeAction(project: Project) {
        super.beforeAction(project)
        val syncContentProvide = SyncContentProvide(project)
        if (!syncContentProvide.isInit()) {
            syncContentProvide.initData()
        }
    }

    override fun action(project: Project) {
        LogUtils.logI(remoteMachineInfo.toString())
        ConfigCommandDialog(project, remoteMachineInfo).show()
    }

}