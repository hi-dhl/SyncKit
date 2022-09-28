package com.hi.dhl.ui;

import com.hi.dhl.common.R;

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
    JTextField tfRemoteAddress;
    JTextField tfRemotePort;
    JTextField tfRemoteUser;
    JTextField tfRemoteCommand;
    JLabel labelRemoteAddress;
    JLabel labelRemotePort;
    JLabel labelRemoteUser;
    JLabel labelRemoteCommand;
    JLabel labelLaunchActivity;
    private JTextField tfSDK;
    private JLabel labSDK;

    PluginConfigForm() {
        rootPanel.setPreferredSize(new Dimension(450, -1));
        labelRemoteUser.setText(R.String.ui.labelRemoteUser);
        labelRemotePort.setText(R.String.ui.labelRemotePort);
        labelRemoteAddress.setText(R.String.ui.labelRemoteAddress);
        labelRemoteCommand.setText(R.String.ui.labelRemoteCommand);
        labelLaunchActivity.setText(R.String.ui.labelLaunchActivity);
        tfLaunchActivity.setToolTipText(R.String.ui.tfLaunchActivity);
        labSDK.setText(R.String.ui.labelSDK);
        tfSDK.setToolTipText(R.String.ui.tfSDK);
    }

}
