# <p align="center"> 云同步编译工具（SyncKit） </p>


<p align="center">
<a href="https://github.com/hi-dhl"><img src="https://img.shields.io/badge/GitHub-HiDhl-4BC51D.svg?style=flat"></a>  

<img src="https://img.shields.io/badge/version-2.0-orange.svg"/>
<img src="https://img.shields.io/badge/language-kotlin-lightgrey.svg"/> 
</p>

## 如何安装工具

* 方法一：点击下发[链接](https://github.com/hi-dhl/SyncKit/releases)，下载最新版本`SyncKit-version.zip`，文件 `SyncKit-version.zip` 不需要解压，拖拽到 idea 开发工具中，将会自动安装，点击重启即可。
  * [下载地址一：https://github.com/hi-dhl/SyncKit/releases](https://github.com/hi-dhl/SyncKit/releases)（实时更新）
  * [下载地址二：https://plugins.jetbrains.com/plugin/19948-synckit/versions](https://plugins.jetbrains.com/plugin/19948-synckit/versions)（需要官方审核，更新比较慢）
* 方法二：`idea 开发工具 -> Preferences -> Plugins` 搜索 SyncKit，安装重启即可

安装成功之后，将会在工具栏上出现下面的图标。

![](https://img.hi-dhl.com/16650664173995.jpg)


## 工具如何使用

在弹出的对话框中，输入远程设备的信息，即可使用。

* 按照图示，点击 「插件配置」，或者按快捷键
    * Win：`alt shift 5`   
    * Mac：`option shift 5`

![](https://img.hi-dhl.com/16670333932446.jpg)

* 在弹出的对话框中，输入对应的 Host（IP 或者域名）、端口号、用户名、登陆密码即可，其它都是可选的

![](https://img.hi-dhl.com/166703183957341.jpg)

上面都设置完之后，就可以开始使用云同步编译工具，本地写代码，远程编译，或者将本地文件同步到远程设备，如下图示。

**点击「远程编译」将会出现如下界面**

![](https://img.hi-dhl.com/16649650280751.gif)


### 可选功能

### 支持 SSH 无密码访问远程设备

插件支持 **密码访问** 和 **无密码访问**，如果想使用 **密码访问** 在上面弹出的对话框中输入登陆密码即可，如果你想使用 **无密码访问**，按照下面的方法手动配置。

如果之前已经配置了 SSH 无密码访问，这一步可忽略，如果你没有配置，按照下面的步骤执行。

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
mkdir -p ~/.ssh && echo ${SSH_PUBLIC_KEY} >> ~/.ssh/authorized_keys
```



#### 自动打开 Activity

点击 「插件配置」将会弹出配置对话框，在 **Launch Activity** 中，按照提示，输入要打开的 Activity，将会在安装完 App 之后，自动打开 Activity。

![](https://img.hi-dhl.com/16650666168955.jpg)

#### 文件过滤功能

工具支持文件过滤功能，当我们同步文件到远程设备，可以过滤掉不需要同步的文件，点击 「插件配置」将会弹出配置对话框，在文件过滤文本框中，输入你不需要同步的文件。

![](https://img.hi-dhl.com/16650666510988.jpg)


工具默认会自动生成一些同步规则，应该满足 80% 的场景，如果有其他不需要同步的文件，在文件过滤文本框中，按照下面的格式，输入你不需要同步的文件，按行分割，一行一个，同步的时候，将会忽略这些文件。

* 某个文件不需要同步，输入文件名即可，例如 `local.properties`
* 如果某个类型的文件不需要同步，输入文件扩展名即可，例如 `*.log`
* 如果某个文件夹不需要同步，输入文件夹加上 `/` 即可，例如 `build/`

#### 填入远程设备 SDK 或者 NDK 路径

在编译 Android 项目中，会自动识别 SDK 或者 NDK 路径，如果失败了，编译将会出错，这时需要手动输入 远程设备 SDK 或者 NDK 路径。

![](https://img.hi-dhl.com/16650666987696.jpg)


如果你的安装多个 NDK 的版本，工具无法识别当前项目依赖的那个 NDK 版本，需要在「插件配置」中输入远程 NDK 和 SDK的路径。


#### 支持自定义命令

**执行远程命令**

![](https://img.hi-dhl.com/16659296169419.jpg)

点击 **「执行远程命令」** 将会弹出一个输入命令的弹窗，如下所示。

![](https://img.hi-dhl.com/16659297498812.jpg)

输入任意命令，例如 `ls -l`, 展示结果如下所示。

![](https://img.hi-dhl.com/16659310248785.jpg)

**执行远程编译命令**

![](https://img.hi-dhl.com/166593076828241.jpg)

如果勾选 **「使用 gradlew」** 那么后面的命令可以直接输入 `assembleDebug`，如果不勾选，则可以输入任意的编译命令即可，例如 `./gradlew assembleDebug`。

#### 远程设备工具安装（可选）

为了简化服务器的部署，我也提供了一键部署服务器环境，按需在远程设备上安装 **JDK11** 、**Andriod SDK** 、**Andriod NDK**。

同样也可以在本地执行脚本，安装对应的工具，点击「初始化」会在当前目录下生成 `.sync` 文件夹，在 `.sync/script` 文件夹下执行对应的脚本即可，例如：

```
bash install_jdk_11.sh
bash install_android_sdk.sh
bash install_android_ndk.sh
```

![](https://img.hi-dhl.com/16650665616246.jpg)


