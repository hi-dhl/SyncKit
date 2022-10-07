# <p align="center"> 云同步编译工具（SyncKit） </p>


<p align="center">
<a href="https://github.com/hi-dhl"><img src="https://img.shields.io/badge/GitHub-HiDhl-4BC51D.svg?style=flat"></a>  

<img src="https://img.shields.io/badge/language-kotlin-orange.svg"/> 

<img src="https://img.shields.io/badge/version-1.0.1-lightgrey.svg"/>
</p>

`SyncKit` 是基于 `Intellij idea` 开发的插件，适用于 `AndroidStudio` 以及 `jetbrains` 旗下的所有 `idea` 软件，主要将本地的项目同步到远程设备，在**远程设备**上进行编译，然后将编译的结果拉回本地。

> 如果有帮助欢迎在仓库 hi-dhl/SyncKit 右上角点个 star，如果你在使用过程中有任何问题，或者有其它的需求，欢迎给我提 issue。

**远程设备**可以是 Nas、另外一台备用电脑、云端（阿里云、腾讯云、华为云等等）、 Docker 虚拟出来的容器等等。

同步编译工具（`SyncKit`），配上内网穿透，就可以享受在任意地点进行数据同步和远程编译。


这个工具让你电脑运行的更加流畅，避免出现下面两个问题：


![](https://img.hi-dhl.com/1663344133598822.jpg)

![](https://img.hi-dhl.com/1663344313991362.jpg)

在开发这个工具之前，我尝试在 github 上寻找类似的项目来解决现有的问题，我只找到了项目 `mainframer` ，但是这个项目作者已经好几年没有在维护这个项目了，使用起来比较麻烦，`idea` 配置也比较麻烦。并不能完全解决我遇到的问题，因此云同步编译工具 `SyncKit` 就诞生了。


`SyncKit` 支持以下功能：

* 支持数据同步到远程设备
* 支持增量同步，本地文件有修改或者新增才会同步到远程设备
* 远程编译功能
* 对于 Android 应用，一键完成远程编译、自动安装、打开目标 Activity
* 一键安装常用工具，部署开发环境
    * 一键安装 **JDK11**
    * 一键安装 **Android SDK**
    * 一键安装 **Android NDK**
* 支持 Mac 、ubuntu

TODO:

* [ ] 支持命令行
* [ ] 支持 Win
* [ ] 支持数据双向同步
* [ ] 支持多台远程设备间切换




### 如何安装工具

最新版本 V1.0.1

* 方法一：`idea 开发工具 -> Preferences -> Plugins` 搜索 SyncKit，安装重启即可
* 方法二：点击链接，下载 `SyncKit.zip`，不需要解压，拖拽到 idea 开发工具中，将会自动安装，点击重启即可

安装成功之后，将会在工具栏上出现下面的图标。

![](https://img.hi-dhl.com/16650664173995.jpg)



### 工具如何使用

* 按照图示，点击 「插件配置」，或者按快捷键
    * Win：`alt shift 5`   
    * Mac：`option shift 5`

![](https://img.hi-dhl.com/16650664707273.jpg)


* 上一步操作完之后，将会弹出一个对话框，输入对应的 Host（IP 或者域名）、端口号、用户名即可，其它都是可选的

![](https://img.hi-dhl.com/16649635496189.jpg)

* 配置 ssh 无密码访问远程设备（可选）

如果你已经配置了，这一步可忽略，如果你没有配置，按照下面的步骤执行，否则你每次执行的时候，都需要输入密码。

* 执行下面命令，获取本地电脑的 `SSH public key`

```
cat ~/.ssh/id_rsa.pub
``` 

如果你的电脑之前没有安装过 SSH，执行下面命令安装 SSH，一路回车，即可。


```
ssh-keygen -t rsa -C "test@qq.com"
```

* 进入远程设备，执行下面命令，将上一步获取到的 `SSH public key`，追加到 `authorized_keys` 文件中

```
echo ${SSH_PUBLIC_KEY} >> ~/.ssh/authorized_keys
```


上面都设置完之后，就可以开始使用云同步编译工具，进行远程编译，或者将本地文件同步到远程设备，如下图示。

**点击「远程编译」将会出现如下界面**

![](https://img.hi-dhl.com/16649650280751.gif)


### 远程设备工具安装（可选）


为了简化服务器的部署，我也提供了一键部署服务器环境，按需在远程设备上安装 **JDK11** 、**Andriod SDK** 、**Andriod NDK**。

同样也可以在本地执行脚本，安装对应的工具，点击「初始化」会在当前目录下生成 `.sync` 文件夹，在 `.sync/script` 文件夹下执行对应的脚本即可，例如：

```
bash install_jdk_11.sh
bash install_android_sdk.sh
bash install_android_ndk.sh
```

![](https://img.hi-dhl.com/16650665616246.jpg)

### 可选功能

**自动打开 Activity**

点击 「插件配置」将会弹出配置对话框，在 **Launch Activity** 中，按照提示，输入要打开的 Activity，将会在安装完 App 之后，自动打开 Activity。

![](https://img.hi-dhl.com/16650666168955.jpg)


**文件过滤功能**

工具支持文件过滤功能，当我们同步文件到远程设备，可以过滤掉不需要同步的文件，点击 「插件配置」将会弹出配置对话框，在文件过滤文本框中，输入你不需要同步的文件。

![](https://img.hi-dhl.com/16650666510988.jpg)


工具默认会自动生成一些同步规则，应该满足 80% 的场景，如果有其他不需要同步的文件，在文件过滤文本框中，按照下面的格式，输入你不需要同步的文件，按行分割，一行一个，同步的时候，将会忽略这些文件。

* 某个文件不需要同步，输入文件名即可，例如 `local.properties`
* 如果某个类型的文件不需要同步，输入文件扩展名即可，例如 `*.log`
* 如果某个文件夹不需要同步，输入文件夹加上 `/` 即可，例如 `build/`

**填入远程设备 SDK 或者 NDK 路径**

在编译 Android 项目中，会自动识别 SDK 或者 NDK 路径，如果失败了，编译将会出错，这时需要手动输入 远程设备 SDK 或者 NDK 路径。

![](https://img.hi-dhl.com/16650666987696.jpg)


### 常见问题


**问题一：**

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

* 如果已经添加了，尝试执行下面命令即可解决，方案来自[stackoverflow.com](https://stackoverflow.com/questions/52885928/vs-code-gives-me-an-ssh-askpass-error-when-i-try-to-push-the-changes-to-my-githu)

```
ssh-keyscan -t rsa bitbucket.org >> ~/.ssh/known_hosts
```


**问题二：**

```
Execution failed for task ':app:parseDebugLocalResources'.
> Could not resolve all files for configuration ':app:androidApis'.
   > Failed to transform android.jar to match attributes {artifactType=android-platform-attr, org.gradle.libraryelements=jar, org.gradle.usage=java-runtime}.
      > Execution failed for PlatformAttrTransform: /root/build/android-sdk/platforms/android-32/android.jar.
         > /root/build/android-sdk/platforms/android-32/android.jar
```

这可能是因为，第一次下载 `android-32` 可能因为网络问题被中断，导致文件 `/root/build/android-sdk/platforms/android-32/android.jar` 不存在，我们可以手动删除 `android-32` 文件夹，然后重新执行远程编译，会重新下载 `android-32`，如果下载速度比较慢，可以在当前项目中，添加 `alyun maven` 仓库， 其他版本的 SDK 处理的方案都是一样的。


**问题三：** 

在使用云同步编译工具（SyncKit）时，提示缺少工具而导致失败，执行下面命令安装对应的工具即可。

```
yum install -y rsync unzip wget
```


### License

```
Copyright 2022 hi-dhl (Jack Deng)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


