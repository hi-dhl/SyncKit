package com.hi.dhl.utils;

import com.hi.dhl.common.Common;
import com.hi.dhl.common.R;
import com.intellij.ide.plugins.cl.PluginClassLoader;
import com.intellij.notification.NotificationDisplayType;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
        try {
            ClassLoader classLoader = JavaJarUtils.class.getClassLoader();
            URL url = classLoader.getResource(Common.resourceConfigDir);
            if (url == null) {
                URL pathUrl = JavaJarUtils.class.getResource("./");
                if (pathUrl != null) {
                    String pathStr = pathUrl.toString();
                    pathStr = pathStr.substring(0, pathStr.indexOf("com/hi")) + Common.resourceConfigDir;
                    url = URI.create(pathStr).toURL();
                }
            }

            if (url == null) {
                LogUtils.logE("get jar URL is null", NotificationDisplayType.NONE);
            }
            copyJarResource(url, dest);
        } catch (Exception e) {
            LogUtils.logE("read fail " + e, NotificationDisplayType.NONE);
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

    private static void copyJarResource(URL url, File dest) throws IOException {
        String jarPath = url.toString().substring(0, url.toString().indexOf("!/") + 2);
        URL jarURL = new URL(jarPath);
        JarURLConnection jarCon = (JarURLConnection) jarURL.openConnection();
        JarFile jarFile = jarCon.getJarFile();
        Enumeration<JarEntry> jarEntrys = jarFile.entries();
        LogUtils.logE("hasMoreElements = " + jarEntrys.hasMoreElements(), NotificationDisplayType.NONE);
        while (jarEntrys.hasMoreElements()) {
            JarEntry entry = jarEntrys.nextElement();
            String name = entry.getName();
            LogUtils.logE("name = " + name, NotificationDisplayType.NONE);
            if (name.contains(Common.resourceConfigDir) && !name.contains("icons") && !name.contains("com")) {
                if (name.contains(Common.syncConfigServiceDir)) {
                    if (entry.isDirectory()) {
                        new File(dest, Common.syncConfigServiceDir).mkdirs();
                    } else {
                        LogUtils.logI("copyDestDir name = ${name}", NotificationDisplayType.NONE);
                        String endName = name.substring(name.lastIndexOf("/") + 1);
                        write(read(name), new File(dest, Common.syncConfigServiceDir), endName);
                    }
                } else if (name.contains(Common.syncConfigScriptDir)) {
                    if (entry.isDirectory()) {
                        new File(dest, Common.syncConfigScriptDir).mkdirs();
                    } else {
                        LogUtils.logI("copyDestDir name = ${name}", NotificationDisplayType.NONE);
                        String endName2 = name.substring(name.lastIndexOf("/") + 1);
                        write(read(name), new File(dest, Common.syncConfigScriptDir), endName2);
                    }
                } else if (!entry.isDirectory()) {
                    String endName3 = name.substring(name.lastIndexOf("/") + 1);
                    LogUtils.logI("copyDestDir name = ${name}", NotificationDisplayType.NONE);
                    write(read(name), dest, endName3);
                }
            }
        }
    }


    public static void main(String... args){
        try {
            URL url = URI.create("jar:file:/C:/Users/dhl/AppData/Roaming/Google/AndroidStudio2020.3/plugins/SyncKit/lib/SyncKit-1.7.jar!/" + Common.resourceConfigDir).toURL();
            System.out.println(url);

            String pathStr = "jar:file:/C:/Users/dhl/AppData/Roaming/Google/AndroidStudio2020.3/plugins/SyncKit/lib/SyncKit-1.7.jar!/com/hi/dhl/utils/";
            pathStr = pathStr.substring(0,pathStr.indexOf("com/hi")) + Common.resourceConfigDir;
            System.out.println("1 path toURL = " + pathStr);

            URL path = URI.create(pathStr).toURL();
            System.out.println("1 path toURL = " + path);
        }catch (Exception e){

        }

    }
}
