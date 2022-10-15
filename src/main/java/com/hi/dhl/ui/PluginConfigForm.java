package com.hi.dhl.ui;

import com.hi.dhl.action.listener.JTextFieldHintListener;
import com.hi.dhl.common.R;
import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;

/**
 * <pre>
 *     author: dhl
 *     date  : 2022/9/29
 *     desc  :
 * </pre>
 */
public class PluginConfigForm {
    JPanel servicePanel;
    JPanel androidPanel;
    JPanel rootPanel;
    JTextField tfLaunchActivity;
    JTextField tfRemoteHost;
    JTextField tfRemotePort;
    JTextField tfRemoteUser;
    JTextField tfRemoteCommand;
    JLabel labelRemoteHost;
    JLabel labelRemotePort;
    JLabel labelRemoteUser;
    JLabel labelRemoteCommand;
    JLabel labelLaunchActivity;
    JTextField tfSDK;
    JLabel lableSDK;
    JTextField tfNdk;
    JLabel lableNDK;
    JTextArea fileFilters;
    JLabel fileFilterTip;
    JTextField tfRemoteWorkDir;
    JLabel lableRemoteWorkDir;
    JLabel lableRemoteWorkDirTip;
    JCheckBox cbGradlew;

    PluginConfigForm() {
        rootPanel.setPreferredSize(new Dimension(460, -1));
        labelRemoteUser.setText(R.String.ui.labelRemoteUser);
        labelRemotePort.setText(R.String.ui.labelRemotePort);
        labelRemoteHost.setText(R.String.ui.labelRemoteHost);
        labelRemoteCommand.setText(R.String.ui.labelRemoteCommand);

        labelLaunchActivity.setText(R.String.ui.labelLaunchActivity);
        tfLaunchActivity.addFocusListener(new JTextFieldHintListener(tfLaunchActivity, R.String.ui.tfLaunchActivity));

        lableSDK.setText(R.String.ui.labelSDK);
        tfSDK.addFocusListener(new JTextFieldHintListener(tfSDK, R.String.ui.tfSDK));

        lableNDK.setText(R.String.ui.labelNDK);
        tfNdk.addFocusListener(new JTextFieldHintListener(tfNdk, R.String.ui.tfNDK));

        fileFilterTip.setForeground(JBColor.GRAY);
        lableRemoteWorkDirTip.setForeground(Color.GRAY);

        lableRemoteWorkDir.setText(R.String.ui.lableRemoteWorkDir);

        cbGradlew.setSelected(true);
        cbGradlew.setText("使用 gradlew");

    }

}
