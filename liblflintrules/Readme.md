##Error类型错误 
1.  ChineseStringDetector
    检查java代码中的中文硬编码
2.  LoggerUsageDetector
    由于系统默认Log在release下依然会打印日志,造成泄露信息的风险
3.  FastJsonDetector
    检查fastJson格式化类对象是否实现了Serializable或者Parcelable
4.  BaseActivityDetector
    检查工程所有Activity是否继承自LFBaseActivity或者LFBaseAppCompatActivity
5.  ViewIdNameDetector
    检查layout文件中的id是否符合《Android-Code-Style》中"布局文件中的id命名"这个部分的规定

##Warning类型错误
