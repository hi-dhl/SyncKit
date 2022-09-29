package com.hi.dhl.action.listener

import java.awt.Color
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JTextField

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/29
 *     desc  :
 * </pre>
 */
class JTextFieldHintListener(
    val jTextField: JTextField,
    val hintText: String
) : FocusListener {

    init {
        jTextField.text = hintText
        jTextField.foreground = Color.GRAY
    }

    override fun focusGained(e: FocusEvent?) {
        val text = jTextField.text
        if (text.equals(hintText)) {
            jTextField.text = ""
            jTextField.foreground = Color.BLACK
        }
    }

    override fun focusLost(e: FocusEvent?) {
        val text = jTextField.text
        if (text.equals("")) {
            jTextField.text = hintText
            jTextField.foreground = Color.GRAY
        }
    }
}