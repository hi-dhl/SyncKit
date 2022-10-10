package com.hi.dhl.utils;

import com.hi.dhl.common.Common;
import com.hi.dhl.common.R;
import com.intellij.notification.NotificationDisplayType;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <pre>
 *     author: dhl
 *     date  : 2021/7/24
 *     desc  :
 * </pre>
 */
public class JavaJarUtils {

    private static final String scriptDirPath = Common.resourceConfigDir + File.separator + Common.syncConfigScriptDir + File.separator;

    private static String[] scriptPaths = new String[]{
            scriptDirPath + R.ShellScript.installApk,
            scriptDirPath + R.ShellScript.installAndroidNDK,
            scriptDirPath + R.ShellScript.installAndroidSDK,
            scriptDirPath + R.ShellScript.installJDK11,
            scriptDirPath + R.ShellScript.installSSHPub
    };

    private static final String serviceDirPath = Common.resourceConfigDir + File.separator + Common.syncConfigServiceDir + File.separator;

    private static String[] servicePaths = new String[]{
            serviceDirPath + Common.syncDefaultConfigJson,
    };

    private static String[] configsPaths = new String[]{
            Common.resourceConfigDir + File.separator + Common.syncConfigLocalIgnoreFile,
            Common.resourceConfigDir + File.separator + Common.syncConfigRemoteIgnoreFile,
            Common.resourceConfigDir + File.separator + Common.syncConfigRemoteIncludeFile
    };

    public static void copy(File dest) {
        ClassLoader classLoader = JavaJarUtils.class.getClassLoader();
        URL url = classLoader.getResource(Common.resourceConfigDir);
        if (url != null) {
            FileUtils.useKotlinCopyJarResource(classLoader, dest);
        } else {
            LogUtils.logE("use Java classLoader.getResource is null", NotificationDisplayType.NONE);
            copyFiles(dest);
        }
    }

    public static void copyFiles(File dest) {

        for (String file : scriptPaths) {
            write(read(file), new File(dest, Common.syncConfigScriptDir), file.substring(file.lastIndexOf("/")));
        }

        for (String file : servicePaths) {
            write(read(file), new File(dest, Common.syncConfigServiceDir), file.substring(file.lastIndexOf("/")));
        }

        for (String file : configsPaths) {
            write(read(file), dest, file.substring(file.lastIndexOf("/")));
        }
    }

    public static File write(String source, File dest, String fileName) {
        if (!dest.exists()) {
            dest.mkdirs();
        }
        FileOutputStream f1 = null;
        FileChannel fc1 = null;
        File outFile = null;
        try {
            outFile = new File(dest, fileName);
            f1 = new FileOutputStream(outFile);
            fc1 = f1.getChannel();
            ByteBuffer buffer = ByteBuffer.wrap(source.getBytes());
            fc1.write(buffer);
        } catch (Exception e) {
            closeQuietly(f1);
            closeQuietly(fc1);
        }
        return outFile;
    }

    private static String read(String path) {
        String content = "";
        InputStream inputStream = null;
        try {
            inputStream = JavaJarUtils.class.getClassLoader().getResourceAsStream(path);
            content = read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(inputStream);
        }
        return content;
    }

    public static String read(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        StringBuilder sb = new StringBuilder();
        int byteRead = 0;
        while ((byteRead = inputStream.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, byteRead));
        }
        return sb.toString();
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
