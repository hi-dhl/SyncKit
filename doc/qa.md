# <p align="center"> 云同步编译工具（SyncKit） </p>


<p align="center">
<a href="https://github.com/hi-dhl"><img src="https://img.shields.io/badge/GitHub-HiDhl-4BC51D.svg?style=flat"></a>  

<img src="https://img.shields.io/badge/version-2.0-orange.svg"/>
<img src="https://img.shields.io/badge/language-kotlin-lightgrey.svg"/> 
</p>

### 常见问题

**问题一：**

```
Kotlin could not find the required JDK tools in the Java installation. Make sure Gradle is running on a JDK, not JRE.
```

在使用工具过程中，如果出现上面的错误，请在远程设备（即服务器），执行下面的命令。

```
bash <(curl -Ls https://git.byte1024.org/https://raw.githubusercontent.com/hi-dhl/SyncKit/main/src/main/resources/config/script/config_jdk.sh)
```


**问题二：**

```
ssh_askpass: exec(/usr/X11R6/bin/ssh-askpass): No such file or directory
```

按照如下方式解决：

* 确认在远程设备文件 `~/.ssh/authorized_keys`，是否正确添加了本机的 `SSH public key`，执行下面命令，如果不需要输入密码，表示正确添加了


```
ssh -p 端口号 user@host

例如:
ssh -p 22 root@192.160.0.100
```

* 如果已经添加了，并且可以通过 SSH 访问，尝试执行下面命令即可解决，方案来自[stackoverflow.com](https://stackoverflow.com/questions/52885928/vs-code-gives-me-an-ssh-askpass-error-when-i-try-to-push-the-changes-to-my-githu)

```
ssh-keyscan -t rsa bitbucket.org >> ~/.ssh/known_hosts
```


**问题三：**

```
Execution failed for task ':app:parseDebugLocalResources'.
> Could not resolve all files for configuration ':app:androidApis'.
   > Failed to transform android.jar to match attributes {artifactType=android-platform-attr, org.gradle.libraryelements=jar, org.gradle.usage=java-runtime}.
      > Execution failed for PlatformAttrTransform: /root/build/android-sdk/platforms/android-32/android.jar.
         > /root/build/android-sdk/platforms/android-32/android.jar
```

这可能是因为，第一次下载 `android-32` 可能因为网络问题被中断，导致文件 `/root/build/android-sdk/platforms/android-32/android.jar` 不存在，我们可以手动删除 `android-32` 文件夹，然后重新执行远程编译，会重新下载 `android-32`，如果下载速度比较慢，可以在当前项目中，添加 `alyun maven` 仓库， 其它版本的 SDK 处理的方案都是一样的。


**问题四：** 

在使用云同步编译工具（SyncKit）时，提示缺少工具而导致失败，执行下面命令安装对应的工具即可。

```
yum install -y rsync unzip wget
```

**问题五**

```
Caused by: java.lang.AssertionError: annotationType(): unrecognized Attribute name MODULE
```

编译的时候出现上述错误，是因为当前项目依赖于 JDK 版本 >= 11, 而命令行编译使用的是 JDK 8，因此出现上面的错误，所以需要安装一个 JDK 版本 >= 11，并且配置好环境，即可解决这个问题。

如何配置多个 JDK ，欢迎前往查看文章，[配置多个 JDK](https://www.hi-dhl.com/2021/05/09/jetpack/13-compose)

**问题六：**

如果你安装多个 NDK 的版本，工具无法识别当前项目依赖那个 NDK 版本，需要在「插件配置」中输入远程 NDK 和 SDK的路径。

![](https://img.hi-dhl.com/16650666987696.jpg)

