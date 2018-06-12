# GDX-RPG 
A ~~not working~~ cross-platform RPG game engine ~~(tease you)~~

[中文说明](https://github.com/dingjibang/GDX-RPG/blob/master/README_CN.md)

via [Libgdx](https://github.com/libgdx/libgdx).

#### now refactoring...

![Image](https://raw.githubusercontent.com/dingjibang/GDX-LAZY-FONT/master/foobar.png)


![Image](https://raw.githubusercontent.com/dingjibang/GDX-RPG/master/extension/readme.jpg)

官网 / website:  [http://shadow.rpsg-team.com](http://shadow.rpsg-team.com)

~~Jenkins: [http://dev.rpsg-team.com/](http://dev.rpsg-team.com/)~~

UI Prototype: [https://modao.cc/app/GhibQOQlOKeugbsDhTPi0qnDaztNAcc](https://modao.cc/app/GhibQOQlOKeugbsDhTPi0qnDaztNAcc)

"git clone" this project into your IDE, build, and enjoy it.

# Common build faults!important( •́ .̫ •̀  )
#### 0.Gradle Version
[here are issue of gdx](https://github.com/libgdx/libgdx/issues/4855), you MUST USING gradle 3.3(or 2.4?), android buildToolsVersion 24. You can remove retrolambda if AndroidStudio build failed.


#### We need [RetroLambda](https://github.com/orfjackal/retrolambda) to using java8-lambda on java7 or lower, please set the environment variable JAVA8_HOME and JAVA7_HOME with your jdk7 and jdk8 directory (so you need dual java :p).
#### 1."SDK location not found ..." or other errors while gradle building .
please download "android sdk" at first. you have to download those modules:
- Android SDK Build-tools (23.0.1)
- Android 5.1.1 (API 22)
- Android Support Library (23.0.1)
- Android Support Repository (22)

maybe you have to [set "ANDROID_HOME" variable](https://www.google.com.hk/#newwindow=1&q=how+to+set+ANDROID_HOME) or [create a local.properties file on project root!](http://stackoverflow.com/questions/23983221/importing-gradle-project-android-error)

#### 2.first run this game via eclipse/android studio, and got an error "Exception in thread "LWJGL Application" com.badlogic.gdx.utils.GdxRuntimeException: Couldn't load file: xxxxxxxxx"
easy, it's [eclipse solution](http://stackoverflow.com/questions/22822767/new-libgdx-setup-receive-file-not-found/22833470#22833470), and here is [android studio  solution](http://stackoverflow.com/questions/24879812/libgdx-project-exception-in-thread-lwjgl-application-couldnt-load-file-erro)

#### 3.oh no, there are some errors in this project!
don't worry, the game is working, those errors are my debugging.

#### 4.and more...
you can create an issue, or mail to dingjibang@qq.com.

# Game(￣∀￣)
![Image](https://raw.githubusercontent.com/dingjibang/GDX-RPG/master/android/assets/share/share.png)
![Image](https://raw.githubusercontent.com/dingjibang/GDX-RPG/master/android/assets/share/1.png)
![Image](https://raw.githubusercontent.com/dingjibang/GDX-RPG/master/android/assets/share/2.png)
![Image](https://raw.githubusercontent.com/dingjibang/GDX-RPG/master/android/assets/share/3.png)
![Image](https://raw.githubusercontent.com/dingjibang/GDX-RPG/master/android/assets/share/4.png)
![Image](https://raw.githubusercontent.com/dingjibang/GDX-RPG/master/android/assets/share/5.png)

# What's this╮（￣▽￣）╭
It's a ~~big~~ RPG game use "Touhou Project(東方Project)" background story.<br/>
You can visit [http://shadow.rpsg-team.com](http://shadow.rpsg-team.com) to see more info.<br/>
(because my energy is limited, this game has only Chinese language can be chosen.)


# Others(╭￣3￣)╭♡ 
Sorry,the project inner images is protected by COPYRIGHT, don't use in other places please :) <br/>
Others, it's noncommercial.
