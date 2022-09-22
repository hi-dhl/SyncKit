package com.hi.dhl.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.awt.SwingPanel
import com.intellij.openapi.ui.popup.JBPopupFactory
import java.awt.Dimension
import javax.swing.JLabel

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/22
 *     desc  :
 * </pre>
 */
object ConfigCommand {
    fun createComponentCommand() {
        val factory: JBPopupFactory = JBPopupFactory.getInstance()
//        val panel = SwingPanel(
//            factory = {
//                JPanel().apply {
//                    setLayout(BoxLayout(this, BoxLayout.Y_AXIS))
//                    add(actionButton("1. Swing Button: decrement", dec))
//                }
//            }
//        )

        val composePanel = ComposePanel().apply {
            setContent {
                createContent()
            }
        }

        factory.createComponentPopupBuilder(composePanel, JLabel())
            .setMinSize(Dimension(600, 600))
            .createPopup()
            .showInFocusCenter()
    }

    @Composable
    fun createContent() {
        Column {
            Button(onClick = {

            }) {
                Text("测试")
            }
        }

        TextField(
            value = "",
            onValueChange = {

            }
        )
    }
}