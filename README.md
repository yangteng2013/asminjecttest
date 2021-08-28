# asminjecttest
android ASM学习与使用

操作步骤：
1、第一次download下来编译前，需要先注释掉
  project/build.gradle 中修改：
      classpath "com.tyty.asmplugin:asmplugin:1.0.1"  改为 //classpath "com.tyty.asmplugin:asmplugin:1.0.1" 
  
  app/build.gradle 中修改：
      plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-android-extensions'
    id 'asmplugin'
    }

apply from: 'asm.gradle'
    
    
2、clean下整个project；

3、Gradle窗口中的asmplugin展开，先执行：build/clean
                               执行：publishing/publishToMavenLocal
                               执行：upload/uploadArchives
                               成功后，再进行下一步

4、 project/build.gradle 中的  classpath "com.tyty.asmplugin:asmplugin:1.0.1"  这一行，去掉注释符号//；再build下  project
    
5、

6、
