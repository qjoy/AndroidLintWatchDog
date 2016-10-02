AndrLintWatchDog
=================
**by QJoy （[email](alexq_andr@163.com) alexq_andr@163.com）**

![platform-android](https://img.shields.io/badge/platform-android-green.svg)&nbsp;
![license-bsd](https://img.shields.io/badge/license-BSD-red.svg)&nbsp;
![author-qjoy](https://img.shields.io/badge/author-QJoy-orange.svg)&nbsp;

**AndrLintWatchDog is a collection of some typical Custom-Lint-Check sample. some of them can be used directly, others you can make a little modification to adapt to your project.**

##Features
- [x] 	ImageFileSizeDetector&nbsp;:&nbsp;检查图片文件尺寸是否超过某个限定的大小;
- [x]	LoggerUsageDetector&nbsp;:&nbsp;使用android.util.Log任何方法;
- [x]	FastJsonDetector&nbsp;:	&nbsp;检查fastJson格式化类对象是否实现了Serializable或者Parcelable;
- [x]	BaseActivityDetector&nbsp;:&nbsp;检查工程所有Activity是否继承自LFBaseActivity或者LFBaseAppCompatActivity;
- [x]	ViewIdNameDetector&nbsp;:&nbsp;检查layout文件中的id是否符合《Android-Code-Style》中"布局文件中的id命名"这个部分的规定;

## Requirements
    - AndroidStudio

## Installation
###Import library into project

	Here is two way to use yourself's custom-lint-check.
	1. Basic Way : import library from local module.
		Like this:
			debugCompile project(':liblflintrules_aarwrap')
			
	2. Advanced Way : push your library to maven/jcenter,import from remote.
		Like this:
			debugCompile 'com.qjoy:liblflintrules_aarwrap:1.0.3'
###Setting for lint
Add lint setting in your module gradle file. 

```
android {
    
    ...

    lintOptions {
        // if true, stop the gradle build if errors are found
        abortOnError false
        // if true, only report errors
        ignoreWarnings true
    }

}
```			
		
## Use AndrLintWatchDog
Do these in AndroidStudio Terminal tab:

	1.	"cd" to the module that you want check your lint-rules;
		例:cd app
	2. "lint" command for it;
		例:../gradlew lint
	3.	BUILD SUCCESSFUL,now here is a html file be maked.open it,read it.
		例:Wrote HTML report to file:///省去了无用的路径/app/build/outputs/lint-results-debug.html

![example-cmd](http://7xox5k.com1.z0.glb.clouddn.com/AndrLintWatchDog_cmd.gif)	
![example-htmlreport](http://7xox5k.com1.z0.glb.clouddn.com/AndrLintWatchDog-htmlreport.png)
			
## More Details about AndrLintWatchDog
**[Detail-andrlintwatchdog](http://alexq.farbox.com/post/andrlintwatchdog-custom-lint-zi-ding-yi-lint-ti-gao-dai-ma-zhi-liang)**			
## Release History

## License
 **AndrLintWatchDog is released under the BSD license. See LICENSE for details.**
