# asminjecttest
android ASM学习与使用

操作步骤：
1、第一次download下来编译前，需要先注释掉
  project/build.gradle 中修改：
      classpath "com.tyty.asmplugin:asmplugin:1.0.1"  改为 //classpath "com.tyty.asmplugin:asmplugin:1.0.1" 
  
  app/build.gradle 中修改：
    ============文件最顶上的代码块 start=================
      plugins {
            id 'com.android.application'
            id 'kotlin-android'
            id 'kotlin-android-extensions'
            id 'asmplugin' //这行先删除，等完成第4步后记得添加回来
      }
      apply from: 'asm.gradle' //这行先删除，等完成第4步后记得添加回来
    ============文件最顶上的代码块 end=================

    
2、clean下整个project；

3、Gradle窗口中的asmplugin展开，先执行：build/clean
                               执行：publishing/publishToMavenLocal
                               执行：upload/uploadArchives
                               成功后项目结构会新增repo文件夹 project/repo
                               再进行第4步

4、 project/build.gradle 中的  classpath "com.tyty.asmplugin:asmplugin:1.0.1"  这一行，去掉注释符号//；再build下  project

   app/build.gradle 中修改
        ============文件最顶上的代码块 start=================
          plugins {
                id 'com.android.application'
                id 'kotlin-android'
                id 'kotlin-android-extensions'
                id 'asmplugin'
          }
          apply from: 'asm.gradle'
        ============文件最顶上的代码块 end=================

5、Terminal中可以执行 gradlew cbcb

    > Task :app:cbcb
    接手外部的参数：佩奇跟乔治一起在公园滑滑梯

    ##说明我们的插件开发，本地maven发布已经OK了；
    ##可以进入plugin的开发工作

