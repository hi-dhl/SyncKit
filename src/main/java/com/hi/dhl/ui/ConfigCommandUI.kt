package com.hi.dhl.ui

import com.intellij.ui.layout.panel
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/22
 *     desc  :
 * </pre>
 */
object ConfigCommandUI {

    fun createCenterPanel() = panel {
        row {
            label("2222")
            JTextField()
        }
    }

    fun createCenterPanel2(): JPanel {
        val panel = JPanel()
        panel.add(JLabel("remoter addr"))
        panel.add(JTextField())
        return panel
    }
}