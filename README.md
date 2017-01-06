# 72GGames_Demo
仿72g赚吧app---测试机型：小米4

第二个模块点击ListView的item开启服务下载，会有些bug。
此item点击事件，开启服务下载的代码本人已注释，撤掉即可。
不知道是不是跟本人手机最近有点脾气的原因。若友人测试下载的功能，请告知下测试结果！谢谢！
---
关于作者：
RealMoMo
> 关于我，欢迎关注  
   微信：[Real_Mo]()  
   邮箱：momo_weiye@126.com
-------------
####开发目的: 
<br>1.项目框架搭建、抽取基类</br>
<br>2.主要：练习自定义View</br>
<br>3.练习动画</br>
<br>4.练习服务组件</br>


###预览界面

<br> ![image](https://github.com/RealMoMo/72GGames_Demo/blob/master/gif/1.gif)</br>
 <br>动态演示</br>
 



###开发环境
Android Studio2.0


### 下载安装
导入项目，重新配置适合你开发环境build.gradle文件。
导入里面app模块，重新配置适合你开发环境build.gradle文件。 

```java  
  
apply plugin: 'com.android.application'

android {
    compileSdkVersion 23
    buildToolsVersion "23.0.3"
    defaultConfig {
        applicationId "momo.com.week12_project"
        minSdkVersion 15
        targetSdkVersion 23
        versionCode 2
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    compile 'com.android.support:appcompat-v7:23.4.0'
    testCompile 'junit:junit:4.12'
    compile files('libs/tools.jar')
    compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'
}
  
```

###Thanks
Everyone who has contributed code and reported issues and pull requests!



###Version
<br>1.0实现App主要模块的自定义View、动画----2017.01.06</br>

