package com.hi.dhl.console

import com.hi.dhl.utils.LogUtils
import com.intellij.execution.filters.ConsoleFilterProvider
import com.intellij.execution.filters.Filter
import com.intellij.execution.filters.RegexpFilter
import com.intellij.openapi.project.Project

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/4
 *     desc  :
 * </pre>
 */
class SyncConsoleFilterProvider : ConsoleFilterProvider {
    override fun getDefaultFilters(project: Project): Array<Filter> {
        return arrayOf(ConsoleResultFilter())
    }
}

class ConsoleResultFilter: Filter{
    override fun applyFilter(line: String, entireLength: Int): Filter.Result? {
        val result = arrayListOf<Filter.ResultItem>()
        if(line.contains("outputs") && line.contains(".apk")){
            LogUtils.logI(line)
        }

//        val resultItem = Filter.ResultItem()

        return Filter.Result(result)
    }
}