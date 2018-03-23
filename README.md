# WeatherSender

通过（很渣的）爬虫技术，将天气数据分析后通过Javax Mail发送至指定邮箱中，并随机发送不一样的摘句/脑筋急转弯等。

## 请在res/url.txt中更改获取天气的网址
仅支持中国城市，请在[中国天气网](http://www.weather.com.cn/profile/city.shtml)选取城市
例如：[郑州天气](http://www.weather.com.cn/weather1d/101180101.shtml)


### 请更改Main中的变量更改参数：
```
	// 邮箱服务器地址
	static final String HOST = "host.xxx.com";
	
	//发送邮件账户与密码 *请确保使用邮箱提供的SMTP密码
	static final String EMAIL = "example@xxx.com";
	static final String PASSWORD = "password";
	
	//接收账户
	static final String RECEIVE_EMAIL = "example@xxx.com";
	
	//定义自动发送消息的时间，此处为6:00 AM
	private static int hour = 6;
	private static int min = 0;
```

### 在控制台输入指令：
支持的命令如下：
```
/send: 发送至所有接收者

/info: 显示天气信息

/update: 更新天气信息到内存中

/help: 显示帮助列表

/remain: 剩余消息数

/remainDetail: 显示所有剩余消息

/used：显示已使用的句子

/updateUrl [网址]: 更新网址

/reset 更新天气与句子

/setTime 时:分 设置自动发送的时间

/stop 停止程序
```
