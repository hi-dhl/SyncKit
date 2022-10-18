# <p align="center"> 云同步编译工具（SyncKit） </p>


<p align="center">
<a href="https://github.com/hi-dhl"><img src="https://img.shields.io/badge/GitHub-HiDhl-4BC51D.svg?style=flat"></a>  

<img src="https://img.shields.io/badge/version-2.0-orange.svg"/>
<img src="https://img.shields.io/badge/language-kotlin-lightgrey.svg"/> 
</p>

<p align="center">
    <a href="./doc/use_synckit.md">工具如何使用</a> &nbsp;
    ·
    &nbsp;<a href="./doc/QA.md">常见问题</a>
</p>


`SyncKit` 是基于 `Intellij idea` 开发的插件，适用于 `AndroidStudio` 以及 `jetbrains` 旗下的所有 `idea` 软件，主要将本地的项目同步到远程设备，在**远程设备**上进行编译，然后将编译的结果拉回本地。

> 如果有帮助欢迎在仓库 [hi-dhl/SyncKit](https://github.com/hi-dhl/SyncKit) 右上角点个 star，如果你在使用过程中有任何问题，或者有其它的需求，欢迎给我提 issue。

**远程设备**可以是 Nas、另外一台备用电脑、云端（阿里云、腾讯云、华为云等等）、 Docker 虚拟出来的容器等等。

同步编译工具（`SyncKit`），配上内网穿透，就可以享受在任意地点进行数据同步和远程编译。


这个工具让你电脑运行的更加流畅，避免出现下面两个问题：


![](https://img.hi-dhl.com/1663344133598822.jpg)

![](https://img.hi-dhl.com/1663344313991362.jpg)

在开发这个工具之前，我尝试在 github 上寻找类似的项目来解决现有的问题，我只找到了项目 `mainframer` ，但是这个项目作者已经好几年没有在维护这个项目了，使用起来比较麻烦，`idea` 配置也比较麻烦。并不能完全解决我遇到的问题，因此云同步编译工具 [SyncKit](https://github.com/hi-dhl/SyncKit) 就诞生了。


`SyncKit` 支持以下功能：

* 支持数据同步到远程设备
* 支持增量同步，本地文件有修改或者新增才会同步到远程设备
* 文件过滤功能，过滤掉不需要同步的文件
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
* [ ] 支持 AAB 打包



### 联系我

* 个人微信：hi-dhl
* 公众号：ByteCode，专注分享有趣硬核原创内容，Kotlin、Jetpack、性能优化、系统源码、算法及数据结构、动画、大厂面经

<img src='http://cdn.51git.cn/2020-10-20-151047.png' width = 350px/>

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


