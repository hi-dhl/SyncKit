package com.hi.dhl.action.base

import com.hi.dhl.common.SyncContentProvide
import com.hi.dhl.ext.upgrad
import com.hi.dhl.utils.LogUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/10/4
 *     desc  :
 * </pre>
 */
abstract class BaseAnAction : AnAction {
    constructor() : super()

    constructor(text: String) : super(text)

    lateinit var projectBasePath: String
    lateinit var project: Project
    lateinit var syncContentProvide: SyncContentProvide

    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { project ->

            syncContentProvide = SyncContentProvide.getInstance(project)
            this.project = project
            projectBasePath = project.basePath ?: "./"
            upgrad(projectBasePath)
            LogUtils.logI("projectBasePath = ${projectBasePath}")
        }
    }


}