# GDX-RPG 
一个~~还未完成的~~跨平台RPG游戏引擎，由[Libgdx](https://github.com/libgdx/libgdx)制作

![Image](https://raw.githubusercontent.com/dingjibang/GDX-LAZY-FONT/master/foobar.png)


![Image](https://raw.githubusercontent.com/dingjibang/GDX-RPG/master/extension/readme.jpg)

官网:  [http://shadow.rpsg-team.com](http://shadow.rpsg-team.com)

~~Jenkins : [http://dev.rpsg-team.com/](http://dev.rpsg-team.com/)~~

使用git克隆这个项目即可运行，目前还没做完，所以没什么说明和文档，随便跑跑看看就好了_(:3
                                             
# 经常出现的“为啥我编译不成功”!important( •́ .̫ •̀  )
#### 首先，请看下你的环境变量（JAVA8_HOME, JAVA7_HOME）有吗！以下是一些常见问题
####1.当编译gradle时候，显示"SDK location not found ..."
首先这个项目需要安卓sdk才可以运行，因为跨平台，也有安卓的那部分你懂的，使用adt并且下载至少以下的模块，才可以继续编译成功：
- Android SDK Build-tools (23.0.1)
- Android 5.1.1 (API 22)
- Android Support Library (23.0.1)
- Android Support Repository (22)
                                             
然后是最重要也是经常出现的问题，就是你需要设置[ANDROID_HOME](https://www.google.com.hk/#newwindow=1&q=how+to+set+ANDROID_HOME)这个环境变量，才能编译，否则肯定会显示找不到sdk路径的问题。<br>
当然有时候设置了还是不好使，你可以尝试[建一个叫local.properties的文件在项目的根目录并且写入相关路径](http://stackoverflow.com/questions/23983221/importing-gradle-project-android-error)


####2.第一次跑这个程序时候，显示 "Exception in thread "LWJGL Application" com.badlogic.gdx.utils.GdxRuntimeException: Couldn't load file: xxxxxxxxx"
这个也很简单，[这是eclipse的解决办法](http://stackoverflow.com/questions/22822767/new-libgdx-setup-receive-file-not-found/22833470#22833470), [这是as的解决办法](http://stackoverflow.com/questions/24879812/libgdx-project-exception-in-thread-lwjgl-application-couldnt-load-file-erro)

####3.项目还是有些红叉叉？
没关系，可能是我没写完就着急上传了QAQ

####4.其他问题...
你可以建一个issue来提问，或者发邮箱至dingjibang@qq.com来提交问题

# 游戏部分截图(￣∀￣)
![Image](https://raw.githubusercontent.com/dingjibang/GDX-RPG/master/android/assets/share/share.png)
![Image](https://raw.githubusercontent.com/dingjibang/GDX-RPG/master/android/assets/share/1.png)
![Image](https://raw.githubusercontent.com/dingjibang/GDX-RPG/master/android/assets/share/2.png)
![Image](https://raw.githubusercontent.com/dingjibang/GDX-RPG/master/android/assets/share/3.png)
![Image](https://raw.githubusercontent.com/dingjibang/GDX-RPG/master/android/assets/share/4.png)
![Image](https://raw.githubusercontent.com/dingjibang/GDX-RPG/master/android/assets/share/5.png)

# 这项目是啥╮（￣▽￣）╭
这个是 ~~巨坑~~ RPG游戏，他的故事背景是根据“东方Project”改编的。<br/>
更多信息你可以点击我们的官网来查看：[http://shadow.rpsg-team.com](http://shadow.rpsg-team.com) <br/>

# 这项目开发到哪了？
瞎〇巴开发，想到哪写哪hhh

#其他问题(╭￣3￣)╭♡ 
本项目中所有图片都是拥有相关版权的，请千万一定不要拿到别的地方去用，其他的诸如代码都是可以随便搞的
