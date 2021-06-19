# Api

#### 介绍
提供一些简单的服务
1. 彩票开奖结果
2. 天气信息查询（待实现）

#### 软件架构
1. SpringBoot + mybatis-plus
2. Mysql
3. 聚合数据彩票接口（https://www.juhe.cn/docs/api/id/300）
4. jdk 1.8
5. Maven
6. lyear(http://lyear.itshubao.com/iframe/v4/) -> 前端框架

#### 使用说明

项目克隆到本地后需要修改以下配置信息
1.  数据库相关配置
2.  redis相关配置
3.  聚合数据相关配置（polymerization.lottery.key）
4.  邮件相关配置

#### 部署
1. 这里是列表文本使用Maven命令对项目进行打包，打报成功后，项目下会生成一个target文件夹，文件夹下有一个jar包
2. 使用java命令启动应用： **java -jar .\Api-1.0-SNAPSHOT.jar --spring.profiles.active=dev** （spring.profiles.active=dev，指定启动加载的profiles文件）
![输入图片说明](https://images.gitee.com/uploads/images/2021/0619/002231_65dd7afb_7967034.png "屏幕截图.png")

#### 项目地址
彩票: http://laimengchao.space/api/two_color_ball.html

#### 效果
1.双色球最新开奖结果
![输入图片说明](https://images.gitee.com/uploads/images/2021/0618/235042_2490222b_7967034.png "屏幕截图.png")
2.双色球历史开奖结果
![输入图片说明](https://images.gitee.com/uploads/images/2021/0618/235052_fe3d10d6_7967034.png "屏幕截图.png")
3.双色球趋势图
![输入图片说明](https://images.gitee.com/uploads/images/2021/0618/235102_a421f206_7967034.png "屏幕截图.png")
4.奖金对照表
![输入图片说明](https://images.gitee.com/uploads/images/2021/0618/235107_a3b23b75_7967034.png "屏幕截图.png")
5.随机号
![输入图片说明](https://images.gitee.com/uploads/images/2021/0618/235116_8ebaf768_7967034.png "屏幕截图.png")
6.订阅及激活邮件
![输入图片说明](https://images.gitee.com/uploads/images/2021/0618/235143_9757efab_7967034.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0618/235408_bab9178a_7967034.png "屏幕截图.png")
7.取消订阅及取消订阅邮件
![输入图片说明](https://images.gitee.com/uploads/images/2021/0618/235527_fe3f9b24_7967034.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0618/235546_894d5735_7967034.png "屏幕截图.png")
8.开奖结果邮件
![输入图片说明](https://images.gitee.com/uploads/images/2021/0618/235628_fe637b90_7967034.png "屏幕截图.png")

#### 感谢

特别感谢以下作者提供的开源组件

1.[itshubao](http://lyear.itshubao.com/iframe/v4/)提供的前端ui框架
